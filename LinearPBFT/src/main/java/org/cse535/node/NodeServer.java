package org.cse535.node;

import com.google.protobuf.InvalidProtocolBufferException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.Binary;
import org.cse535.configs.GlobalConfigs;
import org.cse535.configs.Utils;
import org.cse535.database.DatabaseService;
import org.cse535.loggers.LogUtils;
import org.cse535.proto.*;
import org.cse535.service.ActivateServersService;
import org.cse535.service.CommandsService;
import org.cse535.service.LinearPBFTService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.cse535.configs.GlobalConfigs.serversToPortMap;

public class NodeServer {

    public int port;
    public LogUtils logger;
    public LogUtils commandLogger;
    public LogUtils allLogsLogger;

    public String serverName;
    public Server server;

    public DatabaseService database;

    public HashMap<String, ManagedChannel> serversToChannel;
    public HashMap<String, LinearPBFTGrpc.LinearPBFTBlockingStub> serversToStub;
    public HashMap<String, CommandsGrpc.CommandsBlockingStub> serversToCommandsStub;
    public HashMap<String, ActivateServersGrpc.ActivateServersBlockingStub> serversToActivateServersStub;


    public ManagedChannel clientChannel;
    public LinearPBFTGrpc.LinearPBFTBlockingStub clientStub;


    public List<String> currentActiveServers;

    public NodeServer(String serverName, int port) {
        this.port = port;
        this.serverName = serverName;

        this.logger = new LogUtils(port);
        this.commandLogger = new LogUtils("Commands", port);
        this.allLogsLogger = new LogUtils("PrintLogs", port);

        this.currentActiveServers = new ArrayList<>( GlobalConfigs.allServers);

        initiateChannelsAndStubs();

        this.server = ServerBuilder.forPort(port)
                .addService(new CommandsService())
                .addService(new LinearPBFTService())
                .addService(new ActivateServersService())
                .build();

    }


    public void initiateChannelsAndStubs() {
        serversToStub = new HashMap<>();
        serversToChannel = new HashMap<>();
        serversToCommandsStub = new HashMap<>();
        serversToActivateServersStub = new HashMap<>();

        database = new DatabaseService(this.serverName);

        serversToPortMap.forEach((serverName, port) -> {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
            System.out.println("Channel created for server: " + serverName);

            serversToChannel.put(serverName, channel);
            serversToStub.put(serverName, LinearPBFTGrpc.newBlockingStub(channel));
            serversToActivateServersStub.put(serverName, ActivateServersGrpc.newBlockingStub(channel));
            serversToCommandsStub.put(serverName, CommandsGrpc.newBlockingStub(channel));
        });

        clientChannel = ManagedChannelBuilder.forAddress("localhost", GlobalConfigs.viewServerPort).usePlaintext().build();
        clientStub = LinearPBFTGrpc.newBlockingStub(clientChannel);

    }



    public String printDB() {

        StringBuilder sb = new StringBuilder();

        sb.append("Database: \n");
        sb.append("Server: ").append(this.serverName).append("\n");

        for (String client: GlobalConfigs.clients) {
            sb.append(client).append(" = ").append(this.database.accountsMap.get(client)).append("\n");

        }

        //logger.log(sb.toString());

        return sb.toString();
    }

    public String printStatuses() {
        StringBuilder sb = new StringBuilder();

        sb.append("Statuses : \n");
        sb.append("Server: ").append(this.serverName).append("\n");

        for( Map.Entry<Integer, DatabaseService.TransactionStatus> entry:  this.database.transactionStatusMap.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }


        return sb.toString();
    }


    public String getServerName() {
        return this.serverName;
    }



    public ConcurrentHashMap<Integer, HashSet<NewViewRequest>> newViewRequests = new ConcurrentHashMap<>();

    public AtomicInteger maxViewNum = new AtomicInteger(0);





    public String datastoreToString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Server: ").append(this.serverName).append(" : ");
        for ( String client : GlobalConfigs.clients) {
            sb.append( StringUtils.leftPad( String.valueOf( this.database.accountsMap.get(client) ), 2, " ") ).append(" - ");
        }
        return sb.toString();
    }

    public String datastoreHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append("Accounts  :");
        for ( String client : GlobalConfigs.clients) {
            //sb.append( client).append(" - ");
            sb.append( StringUtils.leftPad( client, 2, " ") ).append(" - ");
        }
        return sb.toString();
    }




    public String generateStatusString(){

        StringBuilder finalSb = new StringBuilder();
        this.database.transactionStatusMap.forEach( (seqNum, status) -> {
            finalSb.append(seqNum).append(",").append(Utils.statusToString(status)).append(";");
        });

        return finalSb.toString();

//       StringBuilder sb= new StringBuilder();
//
//        for(int i=0; i < this.database.maxAddedSeqNum.get(); i++){
//            sb.append("").append(i).append(",")
//                    .append(this.database.transactionStatusMap.containsKey(i) ?
//                            Utils.statusToString(this.database.transactionStatusMap.get(i)) :
//                            "-").append(";");
//        }
//
//        this.logger.log( " AAAAAAAAAAAAAAAAAAAA Max seqnum: " + this.database.maxAddedSeqNum.get()   +  "Status String: " + sb.toString());


//        return sb.toString();
    }


    public String printNewViewRequests(){

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= this.maxViewNum.get(); i++) {

            sb.append("View: ").append(i).append(" : \nNew View Requests:\n");

            if( this.newViewRequests.containsKey(i) ){
                for( NewViewRequest req: this.newViewRequests.get(i) ){
                    sb.append(req.getProcessId()).append(" - ").append("\n----------------START NEW VIEW------------\n");

                    sb.append(req.toString());
                    sb.append("\n----------------END NEW VIEW------------\n");
                }
            }

        }


//        for( Map.Entry<Integer, HashSet<NewViewRequest>> entry: this.newViewRequests.entrySet() ){
//            sb.append("View: ").append(entry.getKey()).append(" : ");
//            for( NewViewRequest req: entry.getValue() ){
//                sb.append(req.getProcessId()).append(" - ");
//            }
//            sb.append("\n");
//        }

        return sb.toString();


    }


    public void printLogOfRequests(){

        StringBuilder sb = new StringBuilder("\n---------------------------- Log of All Requests: -----------------------------\n");

        for(Map.Entry<Integer, Transaction> entry: this.database.transactionMap.entrySet()){
            sb.append("========>>> Seq Num: ").append(entry.getKey()).append(" : ").append( Utils.toString(entry.getValue()) ).append("\n");

            sb.append("\nPrePrepare Request: \n");
            sb.append( Utils.toString( this.database.prePrepareRequestMap.get(entry.getKey()) ));

            sb.append("\nPrePrepare Responses: \n");
            if(this.database.prePrepareResponseMap.get(entry.getKey())!=null)
                this.database.prePrepareResponseMap.get(entry.getKey()).forEach( prePrepareResponse -> sb.append( Utils.toString(prePrepareResponse) ));

            sb.append("\nPrepare Request: \n");
            sb.append( Utils.toString( this.database.prepareRequestMap.get(entry.getKey()) ));

            sb.append("\nPrepare Responses: \n");
            if (this.database.prepareResponseMap.get(entry.getKey()) != null)
                this.database.prepareResponseMap.get(entry.getKey()).forEach( prepareResponse -> sb.append( Utils.toString(prepareResponse) ));

            sb.append("\nCommit Request: \n");
            sb.append( Utils.toString( this.database.commitRequestMap.get(entry.getKey()) ));

            sb.append("\nCommit Responses: \n");
            if( this.database.commitResponseMap.get(entry.getKey()) != null)
                this.database.commitResponseMap.get(entry.getKey()).forEach( commitResponse -> sb.append( Utils.toString(commitResponse) ));

            sb.append("Execution Reply: \n");
            sb.append( Utils.toString( this.database.executionReplyMap.get(entry.getKey()) ));

        }

        sb.append("\n---------------------------- End of Log of All Requests -----------------------------\n");

        this.allLogsLogger.log(sb.toString());

    }




}
