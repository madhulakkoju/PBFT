package org.cse535.service;

import io.grpc.stub.StreamObserver;
import org.cse535.Main;
import org.cse535.configs.Utils;
import org.cse535.database.DatabaseService;
import org.cse535.node.Node;
import org.cse535.proto.CommandInput;
import org.cse535.proto.CommandOutput;
import org.cse535.proto.CommandsGrpc;
import org.cse535.proto.Transaction;

public class CommandsService extends CommandsGrpc.CommandsImplBase {


    @Override
    public void printDB(CommandInput request, StreamObserver<CommandOutput> responseObserver) {

        String op = Main.node.printDB();

        op = Main.node.datastoreToString();

        responseObserver.onNext(CommandOutput.newBuilder().setOutput(op).build());
        responseObserver.onCompleted();

        Main.node.commandLogger.log(op);
    }

    @Override
    public void printLog(CommandInput request, StreamObserver<CommandOutput> responseObserver) {

        String op = " Log \n"; //Main.node.printLog();

        responseObserver.onNext(CommandOutput.newBuilder().setOutput(op).build());
        responseObserver.onCompleted();

        Main.node.commandLogger.log(op);

    }


    @Override
    public void printStatus(CommandInput request, StreamObserver<CommandOutput> responseObserver) {

            String op = Main.node.generateStatusString();

            responseObserver.onNext(CommandOutput.newBuilder().setOutput(op).build());
            responseObserver.onCompleted();

            //Main.node.commandLogger.log(op);


            Main.node.commandLogger.log( "Statuses:\n" + Main.node.printStatuses());

    }

    @Override
    public void flushDB(CommandInput request, StreamObserver<CommandOutput> responseObserver) {

        String serverName = Main.node.serverName;
        int port = Main.node.port;

        //Main.node = new Node(serverName, port);

        Main.node.database = new DatabaseService(Main.node.serverName);

        responseObserver.onNext(CommandOutput.newBuilder().setOutput("Database Flushed").build());
        responseObserver.onCompleted();

        System.out.println("Server is now Flushed");

        Main.node.commandLogger.log("===============================================================================================================================\n");
        Main.node.commandLogger.log("                       " +  request.getInput() + "     \n");
        Main.node.commandLogger.log("===============================================================================================================================\n");

        Main.node.logger.log("===============================================================================================================================\n");
        Main.node.commandLogger.log("                       " +  request.getInput() + "     \n");
        Main.node.logger.log("===============================================================================================================================\n");


    }


    @Override
    public void makeByzantine(CommandInput request, StreamObserver<CommandOutput> responseObserver) {
        Main.node.database.isServerByzantine.set(!request.getInput().equals("0"));
        responseObserver.onNext(CommandOutput.newBuilder().setOutput("Server is now "+(Main.node.database.isServerByzantine.get()?"Byzantine":"Honest")).build());
        responseObserver.onCompleted();

        System.out.println("Server is now "+(Main.node.database.isServerByzantine.get() ?"Byzantine":"Honest"));
    }
}
