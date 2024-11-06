package org.cse535.configs;



import org.cse535.database.DatabaseService;
import org.cse535.proto.Transaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {


    public static String toString(Transaction transaction) {
        return "Transaction ( "+ transaction.getTransactionNum() + " : " + transaction.getSender() + " -> " + transaction.getReceiver() + " = " + transaction.getAmount() + " )";
    }

    public static String Digest(Transaction transaction) {
        return "Transaction ( "+ transaction.getTransactionNum() + " : " + transaction.getSender() + " -> " + transaction.getReceiver() + " = " + transaction.getAmount() + " )";
    }

//
//    public static HashMap<Integer, TimeTakenToExecute> toTimeTakenMap(Map<Integer, TimeTakenMask> timeTakenToExecuteMapMap) {
//        HashMap<Integer, TimeTakenToExecute> timeTakenToExecuteMap = new HashMap<>();
//        timeTakenToExecuteMapMap.forEach( (term, time) -> {
//            TimeTakenToExecute timeTakenToExecute = new TimeTakenToExecute();
//            timeTakenToExecute.startTime = time.getStartTime();
//            timeTakenToExecute.endTime = time.getEndTime();
//            timeTakenToExecuteMap.put(term, timeTakenToExecute);
//        });
//        return timeTakenToExecuteMap;
//    }
//
//    public String toString(Transaction[] transactions) {
//        StringBuilder sb = new StringBuilder();
//        for (Transaction transaction : transactions) {
//            sb.append(toString(transaction)).append("\n");
//        }
//        return sb.toString();
//    }
//
//    public static String toString(BlockOfTransactions block){
//        StringBuilder sb = new StringBuilder();
//        sb.append("Block: " + block.getBlockNum() + " Term:" + block.getTermNumber()
//                + " Tnx count: "+ block.getTransactionsCount()+ " Leader: " + block.getLeader()+
//                " Block Commit Time: " + block.getBlockCommitTime() + "\n");
//        for (Transaction transaction : block.getTransactionsList()) {
//            sb.append(toString(transaction)).append("\n");
//        }
//        return sb.toString();
//    }
//
//
//
//
//
//
//
//
//    public static String toString(BlockOfTransactions[] blocks){
//        StringBuilder sb = new StringBuilder();
//        for (BlockOfTransactions block : blocks) {
//            sb.append(toString(block)).append("\n");
//        }
//        return sb.toString();
//    }
//
//    public static String toString(HashMap<Integer, BlockOfTransactions> blocks){
//        StringBuilder sb = new StringBuilder();
//        blocks.forEach( (term,block) -> sb.append("Term : ").append(term).append("\n").append(toString(block)) );
//        return sb.toString();
//    }
//
//    public static HashMap<Integer, TimeTakenMask> toTimeTakenMask(HashMap<Integer, TimeTakenToExecute> timeTaken){
//        HashMap<Integer, TimeTakenMask> timeTakenMask = new HashMap<>();
//        timeTaken.forEach( (term, time) -> timeTakenMask.put( term,
//                TimeTakenMask.newBuilder().setStartTime(time.startTime).setEndTime(time.endTime).build()));
//        return timeTakenMask;
//    }
//
//

    public static String toString(HashMap<Integer, HashSet<String>> map){
        StringBuilder sb = new StringBuilder();

        map.forEach( (term, set) -> {
            sb.append("SeqNum : ").append(term).append(" -> ExexcutionReplies : ");
            set.forEach( s -> sb.append(s).append(", "));
            sb.append("\n");
        });

        return sb.toString();
    }


    public static boolean isViewLeader(String serverName, int view){
        return GlobalConfigs.allServers.indexOf(serverName) == view % GlobalConfigs.allServers.size();
    }


    public static String statusToString(DatabaseService.TransactionStatus status){
        switch (status){
            case REQUESTED:
                return "REQ";
            case PrePREPARED:
                return "PP";
            case PREPARED:
                return "P";
            case COMMITTED:
                return "C";
            case EXECUTED:
                return "E";
            case ABORTED:
                return "F";
            case PENDING:
                return "-";
            default:
                return "X";
        }
    }


    public static HashMap<Integer, String> statusToMap(String statusesString, AtomicInteger minSeq, AtomicInteger maxSeq){

        statusesString = statusesString.replaceAll(" ","");

        String[] statuses = statusesString.split(";");
        HashMap<Integer, String> statusMap = new HashMap<>();

        for (String s : statuses) {

            s = s.trim();
            if (s.isEmpty()) continue;

            String[] status = s.split(",");
            int seqNum = Integer.parseInt(status[0].trim());
            statusMap.put(seqNum, status[1]);

            minSeq.set(Math.min(minSeq.get(), seqNum));
            maxSeq.set(Math.max(maxSeq.get(), seqNum));
        }

        return statusMap;
    }





}
