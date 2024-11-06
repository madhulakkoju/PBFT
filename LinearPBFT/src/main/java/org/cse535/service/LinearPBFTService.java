package org.cse535.service;

import io.grpc.stub.StreamObserver;
import org.cse535.Main;
import org.cse535.node.ViewServer;
import org.cse535.proto.*;

import javax.swing.text.View;
import java.util.HashSet;

public class LinearPBFTService extends LinearPBFTGrpc.LinearPBFTImplBase {

    @Override
    public void request(TransactionInputConfig request, StreamObserver<TxnResponse> responseObserver) {

        Main.node.logger.log("Request received: " + request.getTransaction().getTransactionNum());

        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        if( Main.node.isLeader() ){

            Main.node.logger.log("Server is leader -> processing request -- added to queue");
            Main.node.database.incomingTnxQueue.add(request);

            TxnResponse response = TxnResponse.newBuilder().setSuccess(true).setServerName(Main.node.serverName).build();
            responseObserver.onNext(response);
        }
        else{
            Main.node.logger.log("Server is not leader -> left request");
            TxnResponse response = TxnResponse.newBuilder().setSuccess(false).setServerName(Main.node.serverName).build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void relayRequest(TransactionInputConfig request, StreamObserver<TxnRelayResponse> responseObserver) {
        Main.node.logger.log("Relay request received: " + request.getTransaction().getTransactionNum());

        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        TxnRelayResponse response = Main.node.handleRelayRequest(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        if(response.getOption() == 0 ){
            Main.node.logger.log("Relay request processed: " + request.getTransaction().getTransactionNum());
            Main.node.logger.log("Initiating View Change");

            if( Main.node.database.viewTriggers.contains(request.getView()+1) ){
                return;
            }

            Main.node.database.viewTriggers.add(request.getView()+1);
            Main.node.initiateViewChange();
        }
        else{
            Main.node.logger.log("Relay request processed: " +
                    request.getTransaction().getTransactionNum() + " -> already processed");
        }

    }

    @Override
    public void prePrepare(PrePrepareRequest request, StreamObserver<PrePrepareResponse> responseObserver) {

        Main.node.logger.log("PrePrepare request received: " + request.getSequenceNumber());
        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        PrePrepareResponse resp = Main.node.handlePrePrepare(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
        Main.node.logger.log("PrePrepare response sent: " + resp.getSequenceNumber() + " Transaction ID: "+ request.getTransaction().getTransactionNum() + " : " + resp.getSuccess() );
    }


    @Override
    public void prepare(PrepareRequest request, StreamObserver<PrepareResponse> responseObserver) {

        Main.node.logger.log("Prepare request received: " + request.getSequenceNumber());
        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

//        //Byzantine Feature -> does not prepare at all
//        if(Main.node.isServerByzantine()){
//           return;
//        }

        PrepareResponse resp = Main.node.handlePrepare(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();

        Main.node.logger.log("Prepare response sent: " + resp.getSequenceNumber());
    }


    @Override
    public void commit(CommitRequest request, StreamObserver<CommitResponse> responseObserver) {

        Main.node.logger.log("Commit request received: " + request.getSequenceNumber());
        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        CommitResponse resp = Main.node.handleCommit(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();

        Main.node.logger.log("Commit response sent: " + resp.getSequenceNumber());
        Main.node.logger.log("Executions initiated");
        Main.node.database.initiateExecutions();
        Main.node.logger.log("Executions Complete");
    }

    @Override
    public void executionReply(ExecutionReplyRequest request, StreamObserver<ExecutionReplyResponse> responseObserver) {

        //View Server side
        responseObserver.onNext(ExecutionReplyResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();

        ViewServer.viewServerInstance.logger.log("Execution reply received: " + request.getSequenceNumber() );

        if(!ViewServer.viewServerInstance.transactionExecutionResponseMap.containsKey( request.getSequenceNumber() )){
            ViewServer.viewServerInstance.transactionExecutionResponseMap.put(request.getSequenceNumber(), new HashSet<>());
        }

        ViewServer.viewServerInstance.transactionExecutionResponseMap.get(request.getSequenceNumber()).add(request.getProcessId());
//        System.out.println("Execution reply received: " + request.getSequenceNumber() + " : "
//                + request.getProcessId() + " \nView : " + request.getView () + " TnxId: " + request.getTransactionId() + " \n");

    }

    @Override
    public void viewChange(ViewChangeRequest request, StreamObserver<ViewChangeResponse> responseObserver) {

        Main.node.logger.log("View Change request received: " + request.getView());

        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        ViewChangeResponse resp = Main.node.handleViewChange(request);

        responseObserver.onNext(resp);
        responseObserver.onCompleted();

        Main.node.logger.log("View Change response sent for " + resp.getView() + " : " + resp.getSuccess());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(  Main.node.database.currentViewNum.get() < request.getView() &&
                ! Main.node.database.viewTriggers.contains(request.getView()) ){

            Main.node.logger.log("Initiating New View Triggers as current view is behind");
            Main.node.database.viewTriggers.add(request.getView());
            Main.node.startNewViewTriggers( request.getView() );
        }
    }

    @Override
    public void newView(NewViewRequest request, StreamObserver<NewViewResponse> responseObserver) {

        System.out.println("New View Notification received at client : " + request.getView());

        //System.out.println(ViewServer.viewServerInstance);

        if(ViewServer.viewServerInstance != null){

            ViewServer.viewServerInstance.logger.log("New View Notification received at client : " + request.getView());

            ViewServer.viewServerInstance.viewNumber.set(request.getView());
            ViewServer.viewServerInstance.primaryServerName = request.getProcessId();

            responseObserver.onNext( NewViewResponse.newBuilder().setSuccess(true).setView(request.getView()).build() );
            responseObserver.onCompleted();

            if(!ViewServer.viewServerInstance.newViewRequests.containsKey(request.getView())){
                ViewServer.viewServerInstance.newViewRequests.put(request.getView(), new HashSet<>());
            }
            ViewServer.viewServerInstance.newViewRequests.get(request.getView()).add(request);

            ViewServer.viewServerInstance.maxViewNum.set( Math.max(ViewServer.viewServerInstance.maxViewNum.get(), request.getView()) );

            return;
        }



        Main.node.logger.log("New View request received: " + request.getView());

        if(!Main.node.isServerActive()){
            Main.node.logger.log("Server is not active -> left request");

            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        Main.node.logger.log("New View request received: " + request.getView());

        NewViewResponse resp = Main.node.handleNewView(request);

        responseObserver.onNext(resp);
        responseObserver.onCompleted();

        Main.node.logger.log("New View response sent for " + resp.getView() + " : " + resp.getSuccess());

    }
}
