package org.cse535.node;


import com.google.protobuf.InvalidProtocolBufferException;
import org.cse535.configs.GlobalConfigs;
import org.cse535.configs.Utils;
import org.cse535.database.DatabaseService;
import org.cse535.proto.*;
import org.cse535.threadimpls.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Node extends NodeServer{

    public Thread transactionWorkerThread;
    //public DatabaseBackupThread databaseBackupThread;

    public AtomicBoolean pauseTransactionsUntilViewChange = new AtomicBoolean(false);


    public Node(String serverName, int port){
        super(serverName, port);

        this.transactionWorkerThread = new Thread(this::processTnxsInQueue);
        //this.databaseBackupThread = new DatabaseBackupThread(this);


        try {
            this.server.start();
            this.transactionWorkerThread.start();
            //this.databaseBackupThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processTnxsInQueue() {
        logger.log("Transaction Worker Thread Started");

        TransactionInputConfig tnxConfig = null;

        while (true) {
            try {
                Thread.sleep(50);

                if(this.pauseTransactionsUntilViewChange.get()){
                    Thread.sleep(100);
                    continue;
                }

                if(this.database.incomingTnxQueue.isEmpty()){

                    Thread.sleep(50);

                    continue;
                }


                tnxConfig = this.database.incomingTnxQueue.poll();

                this.logger.log("Processing Transaction: " + tnxConfig.getTransaction().getTransactionNum());

                processTransaction(tnxConfig);

//                if(!processTransaction(tnxConfig)){
//                    this.database.incomingTnxQueue.add(tnxConfig);
//                    this.logger.log("Transaction Failed: -- readding to queue " + tnxConfig.getTransaction().getTransactionNum());
//                }


                this.logger.log("Transaction Processed: " + tnxConfig.getTransaction().getTransactionNum());



                this.database.initiateExecutions();

            } catch (InterruptedException e) {
                this.commandLogger.log("Line 143 ::: " + e.getMessage());
                e.printStackTrace();
                System.out.println("Line 143 ::: " + e.getMessage());
            }
        }
    }



    public boolean processTransaction(TransactionInputConfig tnxConfig) {

        this.currentActiveServers = tnxConfig.getServerNamesList();

//        for(String i : this.currentActiveServers){
//            System.out.println("Active Servers: " + i);
//        }

        int currentSeqNum = this.database.currentSeqNum.incrementAndGet();

        this.database.maxAddedSeqNum.set(currentSeqNum);

        this.database.transactionMap.put(currentSeqNum, tnxConfig.getTransaction());
        this.database.seqNumViewMap.put(currentSeqNum, this.database.currentViewNum.get());
        this.database.transactionStatusMap.put(currentSeqNum, DatabaseService.TransactionStatus.REQUESTED);

        PrePrepareRequest prePrepareRequest = PrePrepareRequest.newBuilder()
                .setTransaction(tnxConfig.getTransaction())
                .setSequenceNumber(currentSeqNum)
                .setView(this.database.currentViewNum.get())
                .setProcessId(this.serverName)
                .setDigest(tnxConfig.getTransaction().getTransactionHash())
                .build();

       // this.logger.log(prePrepareRequest.toString());

        this.logger.log("Initiating Pre Prepare for SeqNum: " + currentSeqNum + " View: " + this.database.currentViewNum.get() + " Transaction ID: "+ tnxConfig.getTransaction().getTransactionNum());

        // Initiate PrePrepare
        if( initiatePrePrepare(prePrepareRequest) ){

            this.logger.log("Pre Prepare Success");

            //Byzantine Feature -> does not prepare at all

//            if(this.isServerByzantine()){
//                return true;
//            }

            PrepareRequest prepareRequest = PrepareRequest.newBuilder()
                    .setSequenceNumber(currentSeqNum)
                    .setView(this.database.currentViewNum.get())
                    .setProcessId(this.serverName)
                    .setDigest(tnxConfig.getTransaction().getTransactionHash())
                    .build();


            this.logger.log("Initiating Prepare for SeqNum: " + currentSeqNum + " View: " + this.database.currentViewNum.get() + " Transaction ID: "+ tnxConfig.getTransaction().getTransactionNum());

            //this.logger.log(prepareRequest.toString());

            // Initiate Prepare
            if( initiatePrepare(prepareRequest) ){
                this.logger.log("Prepare Success");

                CommitRequest commitRequest = CommitRequest.newBuilder()
                        .setSequenceNumber(currentSeqNum)
                        .setView(this.database.currentViewNum.get())
                        .setProcessId(this.serverName)
                        .setDigest(Utils.Digest(tnxConfig.getTransaction()))
                        .build();

                this.logger.log("Initiating Commit for SeqNum: " + currentSeqNum + " View: " + this.database.currentViewNum.get() + " Transaction ID: "+ tnxConfig.getTransaction().getTransactionNum());

                //this.logger.log(commitRequest.toString());

                // Initiate Commit
                initiateCommit(commitRequest);

                //this.database.initiateExecutions();

                return true;

            }
        }
        return false;
    }



    public boolean initiatePrePrepare(PrePrepareRequest preprepareRequest) {
        try {

            this.database.prePrepareResponseMap.put(preprepareRequest.getSequenceNumber(), new ArrayList<>(GlobalConfigs.serversCount));

            this.database.transactionStatusMap.put(preprepareRequest.getSequenceNumber(), DatabaseService.TransactionStatus.PrePREPARED);

            PrePrepareWorkerThread[] prePrepareWorkerThreads = new PrePrepareWorkerThread[this.currentActiveServers.size()];

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                String serverName = this.currentActiveServers.get(i);
                if(serverName.equals(this.serverName) || !GlobalConfigs.serversToPortMap.containsKey(serverName)) continue;

                int port = GlobalConfigs.serversToPortMap.get(serverName);
                prePrepareWorkerThreads[i] = new PrePrepareWorkerThread(this, port, serverName, preprepareRequest);
            }

            //System.out.println("PrePrepare Worker Threads: " + this.currentActiveServers.size());


            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                if(prePrepareWorkerThreads[i] == null) continue;
                prePrepareWorkerThreads[i].start();
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                if(prePrepareWorkerThreads[i] == null) continue;
                    prePrepareWorkerThreads[i].join();
            }

            int prePrepareAcceptedCount = 1 ; // this.database.prePrepareResponseMap.get(preprepareRequest.getSequenceNumber()).size();

            for (PrePrepareResponse resp : this.database.prePrepareResponseMap.get(preprepareRequest.getSequenceNumber()) ) {
                if(resp.getSuccess()){
                    prePrepareAcceptedCount++;
                }
            }

            if(prePrepareAcceptedCount >= GlobalConfigs.minQuoromSize){
                this.logger.log("PrePrepare accepted for Seq num : " + preprepareRequest.getSequenceNumber() + " Accepts: " + prePrepareAcceptedCount);
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public PrePrepareResponse handlePrePrepare(PrePrepareRequest request) {

        PrePrepareResponse.Builder prePrepareResponse = PrePrepareResponse.newBuilder();

        prePrepareResponse.setSuccess(false);
        prePrepareResponse.setProcessId(this.serverName);
        prePrepareResponse.setSequenceNumber(request.getSequenceNumber());
        prePrepareResponse.setView(request.getView());

        this.logger.log("PrePrepare request received: " + request.getSequenceNumber() + " Transaction ID: "+ request.getTransaction().getTransactionNum()
        + " Digest: " + request.getDigest()
        );

        if(this.database.transactionMap.containsKey(request.getSequenceNumber())){

            if( this.database.transactionMap.get(request.getSequenceNumber()).getTransactionNum() == request.getTransaction().getTransactionNum() &&
                this.database.transactionMap.get(request.getSequenceNumber()).getTransactionHash().equals(request.getTransaction().getTransactionHash())){

                this.database.transactionStatusMap.put(request.getSequenceNumber(), DatabaseService.TransactionStatus.PrePREPARED);

                this.logger.log("Accepted ---- PrePrepare request accepted: " + request.getSequenceNumber() +
                        " Transaction ID: "+ request.getTransaction().getTransactionNum()
                        + " Digest: " + request.getDigest()
                );

                prePrepareResponse.setSuccess(true);
            }


            this.logger.log("Rejected ---- PrePrepare request rejected: " + request.getSequenceNumber() +
                    " Transaction ID: "+ request.getTransaction().getTransactionNum() + " Transaction ID from db map : " + this.database.transactionMap.get(request.getSequenceNumber()).getTransactionNum()
                    + " Tnx Hash matching : " + this.database.transactionMap.get(request.getSequenceNumber()).getTransactionHash().equals(request.getTransaction().getTransactionHash())
                    + "Transaction Hash: " + this.database.transactionMap.get(request.getSequenceNumber()).getTransactionHash() + " Request Hash: " + request.getTransaction().getTransactionHash()
                    + " Digest: " + request.getDigest()
            );


        }
        else {
            this.database.transactionMap.put(request.getSequenceNumber(), request.getTransaction());
            this.database.seqNumViewMap.put(request.getSequenceNumber(), request.getView());
            this.database.transactionStatusMap.put(request.getSequenceNumber(), DatabaseService.TransactionStatus.PrePREPARED);

            prePrepareResponse.setSuccess(true);

            this.logger.log("Accepted ---- PrePrepare request accepted: " + request.getSequenceNumber() +
                    " Transaction ID: "+ request.getTransaction().getTransactionNum()
                    + " Digest: " + request.getDigest()
            );
        }

        this.database.setMaxAddedSeqNum(request.getSequenceNumber());

        return prePrepareResponse.build();
    }



    public boolean initiatePrepare(PrepareRequest prepareRequest) {
        try {

            this.database.prepareResponseMap.put(prepareRequest.getSequenceNumber(), new ArrayList<>(GlobalConfigs.serversCount));

            PrepareWorkerThread[] prepareWorkerThreads = new PrepareWorkerThread[this.currentActiveServers.size()];

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                String serverName = this.currentActiveServers.get(i);
                int port = GlobalConfigs.serversToPortMap.get(serverName);
                prepareWorkerThreads[i] = new PrepareWorkerThread(this, port, serverName, prepareRequest);
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                prepareWorkerThreads[i].start();
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                prepareWorkerThreads[i].join();
            }

            int prepareAcceptedCount = 1 ; // this.database.prePrepareResponseMap.get(preprepareRequest.getSequenceNumber()).size();

            for (PrePrepareResponse resp : this.database.prePrepareResponseMap.get(prepareRequest.getSequenceNumber()) ) {
                if(resp.getSuccess()){
                    prepareAcceptedCount++;
                }
            }

            if(prepareAcceptedCount >= GlobalConfigs.minQuoromSize){
                this.database.transactionStatusMap.put(prepareRequest.getSequenceNumber(), DatabaseService.TransactionStatus.PREPARED );
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public PrepareResponse handlePrepare(PrepareRequest request) {
        PrepareResponse.Builder prepareResponse = PrepareResponse.newBuilder();
        prepareResponse.setSuccess(false);
        prepareResponse.setProcessId(this.serverName);

        this.logger.log("Received Prepare request :::: + " + request.getSequenceNumber() + " Digest: " + request.getDigest());

        if( this.database.transactionMap.containsKey(request.getSequenceNumber()) &&
                this.database.transactionStatusMap.containsKey(request.getSequenceNumber()) &&
               // this.database.transactionMap.get(request.getSequenceNumber()).getTransactionHash().equals(request.getDigest()) &&
                (this.database.transactionStatusMap.get(request.getSequenceNumber()) == DatabaseService.TransactionStatus.PrePREPARED ||
                this.database.transactionStatusMap.get(request.getSequenceNumber()) == DatabaseService.TransactionStatus.PREPARED)){

            this.database.transactionStatusMap.put(request.getSequenceNumber(), DatabaseService.TransactionStatus.PREPARED);

            this.logger.log("Accepted ---- Prepare request accepted: " + request.getSequenceNumber() + " Digest: " + request.getDigest());
            prepareResponse.setSuccess(true);
        }

        this.database.setMaxAddedSeqNum(request.getSequenceNumber());

        return prepareResponse.build();
    }



    public boolean initiateCommit(CommitRequest commitRequest) {
        try {

            this.database.transactionStatusMap.put(commitRequest.getSequenceNumber(), DatabaseService.TransactionStatus.COMMITTED );

            CommitWorkerThread[] commitWorkerThreads = new CommitWorkerThread[this.currentActiveServers.size()];

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                String serverName = this.currentActiveServers.get(i);
                int port = GlobalConfigs.serversToPortMap.get(serverName);
                commitWorkerThreads[i] = new CommitWorkerThread(this, port, serverName, commitRequest);
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                commitWorkerThreads[i].start();
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                commitWorkerThreads[i].join();
            }

            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }

    public CommitResponse handleCommit(CommitRequest request) {

        this.database.setMaxAddedSeqNum(request.getSequenceNumber());

        if(this.database.transactionStatusMap.containsKey(request.getSequenceNumber()) &&
                this.database.transactionStatusMap.get(request.getSequenceNumber()) == DatabaseService.TransactionStatus.PREPARED){

            this.database.transactionStatusMap.put(request.getSequenceNumber(), DatabaseService.TransactionStatus.COMMITTED);
            return CommitResponse.newBuilder().setSuccess(true).build();

        }

        return CommitResponse.newBuilder().setSuccess(false).build();
    }




    public TxnRelayResponse handleRelayRequest(TransactionInputConfig request) {

        TxnRelayResponse.Builder builder = TxnRelayResponse.newBuilder();
        builder.setSuccess(false);
        builder.setServerName(this.serverName);
        builder.setOption(0); // wait for some time

        if(this.database.transactionStatusMap.containsKey(request.getTransaction().getTransactionNum())){

            if(this.database.transactionStatusMap.get(request.getTransaction().getTransactionNum()) == DatabaseService.TransactionStatus.EXECUTED){
                builder.setSuccess(true);
                builder.setOption(1); // execute

                ExecutionReplyRequest executionReplyRequest = ExecutionReplyRequest.newBuilder()
                        .setView(this.database.seqNumViewMap.get(
                                this.database.transactionNumSeqNumMap.get( request.getTransaction().getTransactionNum() )
                        ))
                        .setSequenceNumber(this.database.transactionNumSeqNumMap.get( request.getTransaction().getTransactionNum() ))
                        .setProcessId(this.serverName)
                        .build();

                builder.setExecutionReply(executionReplyRequest);
            }
        }
        return builder.build();
    }




    public void initiateViewChange() {

        try {

            int nextView = this.database.currentViewNum.get()+1;

            this.logger.log("Initiating View Change .. old view "+ this.database.currentViewNum.get() + " new view " + nextView );

            this.database.isLeader.set(false);
            this.pauseTransactionsUntilViewChange.set(true);

            if(!this.database.viewChangeMessageMap.containsKey(nextView)){
                this.database.viewChangeMessageMap.put(nextView, new HashSet<>());
            }

            this.database.viewChangeMessageMap.put(nextView, new HashSet<>());

            ViewChangeWorkerThread[] viewChangeWorkerThreads = new ViewChangeWorkerThread[GlobalConfigs.allServers.size()];

            ViewChangeRequest viewChangeRequest = ViewChangeRequest.newBuilder()
                    .setProcessId(this.serverName)
                    .setView(nextView)
                    .build();

            //this.logger.log("View change request: "+ viewChangeRequest.toString());

            for (int i = 0; i < GlobalConfigs.allServers.size(); i++) {
                if (!GlobalConfigs.allServers.get(i).equals(this.serverName)) {
                    int port = GlobalConfigs.serversToPortMap.get(GlobalConfigs.allServers.get(i));

                    ViewChangeWorkerThread viewChangeWorkerThread = new ViewChangeWorkerThread(this, port, GlobalConfigs.allServers.get(i), viewChangeRequest);
                    viewChangeWorkerThreads[i] = viewChangeWorkerThread;
                    viewChangeWorkerThread.start();
                }
            }

            for (int i = 0; i < GlobalConfigs.allServers.size(); i++) {
                if ( viewChangeWorkerThreads[i] == null) continue;
                viewChangeWorkerThreads[i].join();
            }

            Thread.sleep(10);
            startNewViewTriggers(nextView);

        }
        catch(InterruptedException e) {
                e.printStackTrace();
        }


    }

    public ViewChangeResponse handleViewChange(ViewChangeRequest request) {

        ViewChangeResponse.Builder viewChangeResponse = ViewChangeResponse.newBuilder();

        viewChangeResponse.setProcessId(this.serverName);
        viewChangeResponse.setView(request.getView());
        viewChangeResponse.setSuccess(true);

        if( ! this.database.viewChangeMessageMap.containsKey(request.getView())){
            this.database.viewChangeMessageMap.put(request.getView(), new HashSet<>());
        }
        this.database.viewChangeMessageMap.get(request.getView()).add(request);

        return viewChangeResponse.build();
    }






    public boolean checkNewViewConditionReached(int viewNumber){

       // this.logger.log("Checking New View Condition Reached for view: " + viewNumber + " with quorom size: " + GlobalConfigs.viewChangeQuoromSize);

        if(!this.database.viewChangeMessageMap.containsKey(viewNumber)){
            this.database.viewChangeMessageMap.put(viewNumber, new HashSet<>());
        }

       // this.logger.log( " View Number:  "+ viewNumber + " View Change Messages: " + this.database.viewChangeMessageMap.get(viewNumber).size());

        return this.database.viewChangeMessageMap.get(viewNumber).size() >= GlobalConfigs.viewChangeQuoromSize;
    }



    public void startNewViewTriggers(int nextView) {
        this.logger.log("Starting New View Triggers for view: " + nextView + " Checking condition reached: " + checkNewViewConditionReached(nextView));
        if(checkNewViewConditionReached(nextView)){

            this.logger.log("New View Triggered: " + nextView);

            this.database.currentViewNum.set(nextView);
            this.database.isLeader.set( Utils.isViewLeader(this.serverName, nextView) );

            this.logger.log("New View Triggered: " + nextView + " isLeader: " + this.database.isLeader.get());

            if(this.database.isLeader.get()){
                initiateNextView(nextView);
            }
            this.pauseTransactionsUntilViewChange.set(false);
        }

    }

    public void initiateNextView(int viewNumber) {

        try{
            NewViewRequest newViewRequest = NewViewRequest.newBuilder()
                    .setProcessId(this.serverName)
                    .setView(viewNumber)
                    .addAllViewChangeMessages( new ArrayList<>(this.database.viewChangeMessageMap.get(viewNumber)) )
                    .build();

            NewViewWorkerThread[] newViewWorkerThreads = new NewViewWorkerThread[GlobalConfigs.allServers.size()+ 1];

            for (int i = 0; i < GlobalConfigs.allServers.size(); i++) {
                if(GlobalConfigs.allServers.get(i).equals(this.serverName)) continue;

                newViewWorkerThreads[i] = new NewViewWorkerThread(this,
                        GlobalConfigs.serversToPortMap.get(GlobalConfigs.allServers.get(i)), GlobalConfigs.allServers.get(i), newViewRequest);
                newViewWorkerThreads[i].start();
            }


            this.logger.log("New View Request sent to all servers");
//            newViewWorkerThreads[GlobalConfigs.allServers.size()] = new NewViewWorkerThread(this,
//                    GlobalConfigs.viewServerPort, GlobalConfigs.viewServerName, newViewRequest);

            this.clientStub.newView(newViewRequest);


            for (int i = 0; i <= GlobalConfigs.allServers.size(); i++) {
                if(newViewWorkerThreads[i] == null) continue;

//                this.logger.log("Waiting for New View Response from: " + newViewWorkerThreads[i].targetServerName
//                        + " "+ newViewWorkerThreads[i].targetPort);

                newViewWorkerThreads[i].join();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public NewViewResponse handleNewView(NewViewRequest request) {

        NewViewResponse.Builder newViewResponse = NewViewResponse.newBuilder();
        newViewResponse.setProcessId(this.serverName);
        newViewResponse.setView(request.getView());
        newViewResponse.setSuccess(false);

        this.logger.log("New View Request received: " + request.getView());

//        this.logger.log("\nXYZ New View Requests:\n\n");
//
//        this.logger.log(request.toString());
//
//        this.logger.log("\n\n");

        this.logger.log( "View Change Messages: " + request.getViewChangeMessagesList().size());
//
//        this.logger.log( "Current View: " + this.database.currentViewNum.get() + " Requested View: " + request.getView() +
//                "\nCondition 1: "+ (request.getViewChangeMessagesList().size() >= GlobalConfigs.viewChangeQuoromSize) +
//                "\nCondition 2: "+ (request.getView() >= this.database.currentViewNum.get())
//                );
//
//        this.logger.log("New Condition: " + ((request.getViewChangeMessagesList().size() >= GlobalConfigs.viewChangeQuoromSize ||
//                this.database.viewChangeMessageMap.get(request.getView()).size() >= GlobalConfigs.viewChangeQuoromSize
//        ) &&  request.getView() >= this.database.currentViewNum.get()));

        if( (request.getViewChangeMessagesList().size() >= GlobalConfigs.viewChangeQuoromSize ||
                this.database.viewChangeMessageMap.get(request.getView()).size() >= GlobalConfigs.viewChangeQuoromSize
                ) &&  request.getView() >= this.database.currentViewNum.get() ){

            this.logger.log("New View Request accepted: " + request.getView());

            newViewResponse.setSuccess(true);
            this.database.currentViewNum.set(request.getView());
        }

        return newViewResponse.build();
    }



    public boolean isLeader(){
            return this.database.isLeader.get();
    }

    public boolean isServerActive() {
        return this.database.isServerActive.get();
    }

    public boolean isServerByzantine() {
        return this.database.isServerByzantine.get();
    }


}
