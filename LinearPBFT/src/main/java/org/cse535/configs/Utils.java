package org.cse535.configs;



import org.cse535.database.DatabaseService;
import org.cse535.proto.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {

    public static String toString(Transaction transaction) {
        if(transaction == null) return "";
        return "Transaction ( " + transaction.getSender() + " -> " + transaction.getReceiver() + " = " + transaction.getAmount() + " ) ; ";
    }

    public static String toString(NewViewRequest newViewRequest) {
        if(newViewRequest == null) return "";
        StringBuilder sb = new StringBuilder( "NewViewRequest ( New View: " + newViewRequest.getView() + " Sent by Leader: " + newViewRequest.getProcessId() + " ) ; \n"
                + "Related View Change Requests: ");

        newViewRequest.getViewChangeMessagesList().forEach( viewChangeRequest -> sb.append(toString(viewChangeRequest)).append("  "));
                return sb.toString();
    }

    public static String toString(ViewChangeRequest viewChangeRequest) {
        if(viewChangeRequest == null) return "";
        return "ViewChangeRequest ( New View: " + viewChangeRequest.getView() + " & sent by: " + viewChangeRequest.getProcessId() + " ) ; ";

    }

    public static String toString(ExecutionReplyRequest reply){
        if(reply == null) return "";
        return "Execution Reply at Client ( SeqNum:" + reply.getSequenceNumber()+ " IsSuccess? : "+ reply.getSuccess() +
                " Sent by : " + reply.getProcessId() + " in View "+ reply.getView() + " ) ; ";
    }

    public static String toString( CommitRequest commitRequest){
        if(commitRequest == null) return "";
        return "CommitRequest ( SeqNum: " + commitRequest.getSequenceNumber() + " Sent By: " + commitRequest.getProcessId() + " in View "+ commitRequest.getView() +" ) ; ";

    }

    public static String toString(PrepareRequest prepareRequest){
        if(prepareRequest == null) return "";
        return "PrepareRequest ( SeqNum: " + prepareRequest.getSequenceNumber() + " Sent by " +
                prepareRequest.getProcessId()+ " in "+ prepareRequest.getView() + " ) ; ";

    }

    public static String toString(PrePrepareRequest prePrepareRequest){
        if(prePrepareRequest == null) return "";
        return "PrePrepareRequest ( SeqNum: " + prePrepareRequest.getSequenceNumber() + " : "+ toString(prePrepareRequest.getTransaction())+ " Sent By " + prePrepareRequest.getProcessId() + " ) ; ";

    }

    public static String toString(PrePrepareResponse prePrepareResponse){
        if(prePrepareResponse == null) return "";
        return "PrePrepareResponse ( SeqNum: " + prePrepareResponse.getSequenceNumber() + " Success? "+ prePrepareResponse.getSuccess() + " Sent by " + prePrepareResponse.getProcessId() + " ) ; ";

    }

    public static String toString(PrepareResponse prepareResponse){
        if(prepareResponse == null) return "";
        return "PrepareResponse ( SeqNum: " + prepareResponse.getSequenceNumber() + " Success? "+ prepareResponse.getSuccess() + " Sent by " + prepareResponse.getProcessId() + " ) ; ";

    }

    public static String toString(CommitResponse commitResponse){
        if(commitResponse == null) return "";
        return "CommitResponse ( SeqNum: " + commitResponse.getSequenceNumber() + " Success? "+ commitResponse.getSuccess() + " Sent by " + commitResponse.getProcessId() + " ) ; ";

    }

    public static String toString(ViewChangeResponse viewChangeResponse){
        if(viewChangeResponse == null) return "";
        return "ViewChangeResponse ( SeqNum: " + viewChangeResponse.getView() + " Success? "+ viewChangeResponse.getSuccess() + " Sent by " + viewChangeResponse.getProcessId() + " ) ; ";

    }

    public static String toString(NewViewResponse newViewResponse){
        if(newViewResponse == null) return "";
        return "NewViewResponse ( View: " + newViewResponse.getView() + " Success? "+ newViewResponse.getSuccess() + " Sent by " + newViewResponse.getProcessId() + " ) ; ";

    }




    public static String Digest(Transaction transaction) {
        if(transaction == null) return "";
        return "Transaction ( "+transaction.getSender() + " -> " + transaction.getReceiver() + " = " + transaction.getAmount() + " ) ; ";
    }

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
