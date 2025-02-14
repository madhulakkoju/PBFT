package org.cse535.service;

import io.grpc.stub.StreamObserver;
import org.cse535.Main;
import org.cse535.node.ViewServer;
import org.cse535.proto.*;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

            Main.node.initiateViewChange();

            Main.node.database.viewTriggers.add(request.getView()+1);
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
        Main.node.database.prePrepareRequestMap.put(request.getSequenceNumber(), request);
        Main.node.database.prePrepareResponseMap.put(request.getSequenceNumber(), new ArrayList<>(Collections.singleton(resp)) );
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

        //Byzantine Feature -> does not prepare at all
        if(Main.node.isServerByzantine()){

            Main.node.logger.log("Byzantine Server -> left Prepare Request");
            responseObserver.onNext(null);
            responseObserver.onCompleted();
           return;
        }

        PrepareResponse resp = Main.node.handlePrepare(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();

        Main.node.logger.log("Prepare response sent: " + resp.getSequenceNumber());
        //Main.node.database.prepareRequestMap.put(request.getSequenceNumber(), request);
        Main.node.database.prepareResponseMap.put(request.getSequenceNumber(), new ArrayList<>(Collections.singleton(resp)));
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

        Main.node.database.commitRequestMap.put(request.getSequenceNumber(), request);
        Main.node.database.commitResponseMap.putIfAbsent(request.getSequenceNumber(), new ArrayList<>());
        Main.node.database.commitResponseMap.get(request.getSequenceNumber()).add(resp);
    }

    @Override
    public void executionReply(ExecutionReplyRequest request, StreamObserver<ExecutionReplyResponse> responseObserver) {

        //View Server side
        responseObserver.onNext(ExecutionReplyResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();

        ViewServer.viewServerInstance.logger.log("Execution reply received: " + request.getSequenceNumber() );

        if(!ViewServer.viewServerInstance.transactionExecutionResponseMap.containsKey( request.getTransactionId() )){
            ViewServer.viewServerInstance.transactionExecutionResponseMap.put(request.getTransactionId(), new HashSet<>());
        }
        if(!ViewServer.viewServerInstance.transactionExecutionReplies.containsKey( request.getTransactionId() )){
            ViewServer.viewServerInstance.transactionExecutionReplies.put(request.getTransactionId(), new HashSet<>());
        }

        ViewServer.viewServerInstance.transactionExecutionResponseMap.get(request.getTransactionId()).add(request.getProcessId());
        ViewServer.viewServerInstance.transactionExecutionReplies.get(request.getTransactionId()).add(request);
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

        if(  Main.node.database.currentViewNum.get() < request.getView() &&
                ! Main.node.database.viewTriggers.contains(request.getView()) ){

            Main.node.logger.log("Initiating New View Triggers as current view is behind");
            Main.node.database.viewTriggers.add(request.getView());
            Main.node.startNewViewTriggers( request.getView() );
        }
        Thread.sleep(200);

        if( !Main.node.database.isLeader.get() &&  !Main.node.checkNewViewEncountered( request.getView() )){
            // Backup did not receive new view request from new leader -> initiate view change
            Main.node.initiateViewChange();
        }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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

        Main.node.newViewRequests.putIfAbsent(request.getView(), new HashSet<>());
        Main.node.newViewRequests.get(request.getView()).add(request);

        Main.node.logger.log("New View request received: " + request.getView());

        NewViewResponse resp = Main.node.handleNewView(request);

        responseObserver.onNext(resp);
        responseObserver.onCompleted();

        Main.node.logger.log("New View response sent for " + resp.getView() + " : " + resp.getSuccess());

    }
}
