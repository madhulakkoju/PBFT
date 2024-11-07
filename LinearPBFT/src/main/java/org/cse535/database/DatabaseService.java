package org.cse535.database;

import com.google.protobuf.Timestamp;
import lombok.Data;
import org.cse535.Main;
import org.cse535.configs.GlobalConfigs;
import org.cse535.proto.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class DatabaseService {

    public enum TransactionStatus{
        None,
        REQUESTED,
        PrePREPARED,
        PREPARED,
        COMMITTED,
        EXECUTED,
        ABORTED,
        PENDING
    }

    //TransactionNum : SeqNum
    public HashMap<Integer, Integer> transactionNumSeqNumMap = new HashMap<>();

    // SeqNum : TransactionStatus
    public HashMap<Integer, TransactionStatus> transactionStatusMap = new HashMap<>();

    // SeqNum : Transaction
    public HashMap<Integer, Transaction> transactionMap = new HashMap<>();

    // SeqNum : ViewNum
    public HashMap<Integer, Integer> seqNumViewMap = new HashMap<>();

    // SeqNum : PrePrepareRequest
    public HashMap<Integer, PrePrepareRequest> prePrepareRequestMap = new HashMap<>();
    // SeqNum : PrePrepareResponse
    public HashMap<Integer, List<PrePrepareResponse>> prePrepareResponseMap = new HashMap<>();

    // SeqNum : PrepareRequest
    public HashMap<Integer, PrepareRequest> prepareRequestMap = new HashMap<>();
    // SeqNum : PrepareResponse
    public HashMap<Integer, List<PrepareResponse>> prepareResponseMap = new HashMap<>();

    //SeqNum : CommitRequest
    public HashMap<Integer, CommitRequest> commitRequestMap = new HashMap<>();
    //SeqNum : CommitResponse
    public HashMap<Integer, List<CommitResponse>> commitResponseMap = new HashMap<>();

    // SeqNum : ExecutionReplyRequest
    public HashMap<Integer, ExecutionReplyRequest> executionReplyMap = new HashMap<>();


    // Client: Balance
    public ConcurrentHashMap<String, Integer> accountsMap = new ConcurrentHashMap<>();

    //View Number: View Change Messages
    public ConcurrentHashMap<Integer, HashSet<ViewChangeRequest>> viewChangeMessageMap = new ConcurrentHashMap<>();

    //Already triggered Views - to restrict multiple triggers for same view
    public HashSet<Integer> viewTriggers = new HashSet<>();
    public ConcurrentHashMap<Integer, Boolean> viewsTriggered = new ConcurrentHashMap<>();




    public AtomicInteger currentSeqNum = new AtomicInteger(0);
    public AtomicInteger currentViewNum = new AtomicInteger(0);


    public PriorityBlockingQueue<TransactionInputConfig> incomingTnxQueue = new PriorityBlockingQueue<>(100, new Comparator<TransactionInputConfig>() {
        @Override
        public int compare(TransactionInputConfig o1, TransactionInputConfig o2) {
            return o1.getTransaction().getTransactionNum() - o2.getTransaction().getTransactionNum();
        }
    });

    public AtomicBoolean isLeader = new AtomicBoolean(false);
    public AtomicBoolean isServerActive = new AtomicBoolean(true);
    public AtomicBoolean isServerByzantine = new AtomicBoolean(false);



    public AtomicInteger lastExecutedSeqNum = new AtomicInteger(0);
    public AtomicInteger maxAddedSeqNum = new AtomicInteger(0);








    public DatabaseService(String serverName){

        for (String client : GlobalConfigs.clients) {
            accountsMap.put(client, GlobalConfigs.INIT_BALANCE);
        }

        isLeader.set(serverName.equals(GlobalConfigs.initLeader));

    }


    public void initiateExecutions(){

        Main.node.logger.log("Initiating executions : "+ lastExecutedSeqNum.get() + " : " + maxAddedSeqNum.get());

        while( lastExecutedSeqNum.get() <= maxAddedSeqNum.get() ){

            int seqNum = lastExecutedSeqNum.get() + 1;

            Main.node.logger.log("Initiating execution for seqNum: " + seqNum);
            Main.node.logger.log("Status : " + (transactionStatusMap.containsKey(seqNum) ? transactionStatusMap.get(seqNum) : "Not Found"));

            if( transactionStatusMap.containsKey(seqNum) && transactionStatusMap.get(seqNum) == TransactionStatus.COMMITTED ){
                // Execute the transaction
                executeTransaction(seqNum);
                lastExecutedSeqNum.set(seqNum);
            } else if (transactionMap.containsKey(seqNum) && transactionStatusMap.get(seqNum) == TransactionStatus.EXECUTED){
                lastExecutedSeqNum.set(seqNum);
            } else{
                break;
            }
        }
    }

    public void executeTransaction( int seqNum ){

        if(this.transactionStatusMap.containsKey(seqNum) && this.transactionStatusMap.get(seqNum) == TransactionStatus.EXECUTED){
            return;
        }

        Main.node.logger.log("Executing transaction: " + seqNum);
        Transaction transaction = transactionMap.get(seqNum);


        int senderAmount = this.accountsMap.get(transaction.getSender()) - transaction.getAmount();

        if(senderAmount < 0){
            Main.node.logger.log("Transaction Execution failed: " + seqNum);
            this.transactionStatusMap.put(seqNum, TransactionStatus.ABORTED);
            Main.node.clientStub.executionReply(
                    ExecutionReplyRequest.newBuilder()
                            .setView(this.seqNumViewMap.get(seqNum))
                            .setTransactionId(transaction.getTransactionNum())
                            .setSequenceNumber(seqNum)
                            .setProcessId(Main.node.serverName)
                            .setSuccess(false)
                            .build()
            );
            Main.node.logger.log("Sent Reply to Client executed: " + seqNum);
            return;
        }

        // Execute the transaction
        this.accountsMap.put(transaction.getSender(), this.accountsMap.get(transaction.getSender()) - transaction.getAmount() );
        this.accountsMap.put(transaction.getReceiver(), this.accountsMap.get(transaction.getReceiver()) + transaction.getAmount() );

        this.transactionStatusMap.put(seqNum, TransactionStatus.EXECUTED);

        Main.node.logger.log("Transaction executed: " + seqNum);

        Main.node.logger.log("Sending Reply to Client executed: " + seqNum + " Transaction: " + transaction.getTransactionNum() + "\n"+ transaction.toString());

        Main.node.clientStub.executionReply(
                ExecutionReplyRequest.newBuilder()
                        .setView(this.seqNumViewMap.get(seqNum))
                        .setTransactionId(transaction.getTransactionNum())
                        .setSequenceNumber(seqNum)
                        .setSuccess(true)
                        .setProcessId(Main.node.serverName)
                        .build()
        );

        Main.node.logger.log("Sent Reply to Client executed: " + seqNum);

    }










    public void setLastExecutedSeqNum(int seqNum){
        lastExecutedSeqNum.set(seqNum);
    }

    public void setMaxAddedSeqNum(int seqNum){
        maxAddedSeqNum.set( Math.max( seqNum , maxAddedSeqNum.get() ) );
    }









}
