package org.cse535.node;


import org.cse535.Main;
import org.cse535.configs.GlobalConfigs;
import org.cse535.configs.Utils;
import org.cse535.proto.*;
import org.cse535.threadimpls.ClientRequestThread;
import org.cse535.threadimpls.SendTnxWorkerThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.cse535.configs.GlobalConfigs.allServers;



public class ViewServer extends NodeServer{


    public static class TnxLine{
        public TransactionInputConfig transactionInputConfig;
        public List<String> maliciousServers;

        public TnxLine(TransactionInputConfig transactionInputConfig, List<String> maliciousServers){
            this.transactionInputConfig = transactionInputConfig;
            this.maliciousServers = maliciousServers;
        }
    }



    public AtomicInteger viewNumber = new AtomicInteger(0);

    public AtomicInteger primaryServerIndex = new AtomicInteger(0);

    public String primaryServerName = GlobalConfigs.initLeader;

    public int tnxCount = 1;
    public int lineNum = 0;

    public int TestSetNumber = 1;

    public HashMap<Integer, TransactionInputConfig> transactionInputConfigMap = new HashMap<>();
    public ConcurrentHashMap<Integer, HashSet<String>> transactionExecutionResponseMap = new ConcurrentHashMap<>();



    public static ViewServer viewServerInstance;


    public enum Command {
        PrintDB,
        PrintLog,
        PrintStatus,
        PrintView
    }

    public ViewServer(String serverName, int port) {
        super(serverName, port);
        try {
            this.server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TnxLine parseTnxConfig(String line, int tnxCount) {




//        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
//
//
//        // Parse the values, trimming whitespace
//        int testCaseCount = Integer.parseInt(parts[0].replaceAll("[^0-9]", "").trim());  // Trimmed to remove any whitespace
//
//        String[] tnx = parts[1].replaceAll("\"", "").replace("(","").replace(")","").trim()
//                .split(",");  // Clean and trim
//
//        String listString = parts[2].replaceAll("[\\[\\]\"]", "").trim();  // Clean and trim
//
//        List<String> activeServers = Arrays.asList(listString.split(","));
//
//        String[] maliciousServers = parts[3].replaceAll("[\\[\\]]", "").replaceAll("\"","").trim().split(",");  // Clean and trim
//
//        Transaction transaction = Transaction.newBuilder()
//                .setSender(tnx[0])
//                .setReceiver(tnx[1])
//                .setAmount(Integer.parseInt(tnx[2].replace(" ","")))
//                .setTransactionNum(tnxCount)
//                .build();





        //String line = "2,\"(F, B, 3)\",\"[S1, S2, S3, S4, S5, S6, S7]\",\"[S4, S6]\"";

// Split by commas, respecting quoted commas.
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

// Parse the test case count (assuming the first part is a simple number)
        int testCaseCount = Integer.parseInt(parts[0].trim()); // No need to use replaceAll for numbers

        viewServerInstance.TestSetNumber = testCaseCount;

// Parse the transaction details (sender, receiver, amount)
        String[] tnx = parts[1].replaceAll("[()\"]", "").trim().split(","); // Clean up and split
        String sender = tnx[0].trim();
        String receiver = tnx[1].trim();
        int amount = Integer.parseInt(tnx[2].trim());  // Parse amount as an integer

// Parse the list of active servers
        String[] activeServers = parts[2].replaceAll("[\\[\\]\"]", "").trim().split(",");

        List<String> activeServersList = new ArrayList<>();

        for(int i =0;i<activeServers.length;i++){
            if(activeServers[i].trim().length() > 0)
            activeServersList.add( activeServers[i].trim());
        }

// Parse the list of malicious servers
        String[] maliciousServers = parts[3].replaceAll("[\\[\\]\"]", "").trim().split(",");

        List<String> maliciousServersList = new ArrayList<>();

        for(int i =0;i<maliciousServers.length;i++){
            if(maliciousServers[i].trim().length() > 0)
            maliciousServersList.add( maliciousServers[i].trim());
        }

// Now you can create your transaction
        Transaction transaction = Transaction.newBuilder()
                .setSender(sender)
                .setReceiver(receiver)
                .setAmount(amount)
                .setTransactionNum(tnxCount) // Assuming you want to set this as the transaction number
                .build();




        return new TnxLine(TransactionInputConfig.newBuilder()
                .setSetNumber(testCaseCount)
                .setView(viewServerInstance.viewNumber.get())
                .setTransaction(transaction)
                .addAllServerNames(activeServersList)
                .build(), maliciousServersList);
    }


    public void propogateTransactionToServers(TransactionInputConfig transactionInputConfig, AtomicBoolean isSent) {

        try {

            SendTnxWorkerThread[] threads = new SendTnxWorkerThread[allServers.size()];

            for (int i = 0; i < allServers.size(); i++) {
                threads[i] = new SendTnxWorkerThread(
                        this,
                        GlobalConfigs.serversToPortMap.get(allServers.get(i)),
                        allServers.get(i),
                        transactionInputConfig,
                        isSent
                );
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                threads[i].start();
            }

            for (int i = 0; i < this.currentActiveServers.size(); i++) {
                threads[i].join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public boolean sendTransactionToServers(TransactionInputConfig transactionInputConfig, HashMap<String, Boolean> activeServersStatusMap) {

        try {

            if(activeServersStatusMap.containsKey(this.primaryServerName) && activeServersStatusMap.get(this.primaryServerName)) {

                this.logger.log("Sending to Leader: " + this.primaryServerName + " View : " + this.viewNumber.get());

                transactionExecutionResponseMap.put(transactionInputConfig.getTransaction().getTransactionNum(), new HashSet<>());

                LinearPBFTGrpc.LinearPBFTBlockingStub stub = this.serversToStub.get(this.primaryServerName);

                TxnResponse response = stub.request(transactionInputConfig);
                this.logger.log("Transaction sent  : " + response.getSuccess() + "  to server : "+ response.getServerName());

                return response.getSuccess();
            }
            else {
                //System.out.println("Primary Node might be Inactive -> Response is null");
                // Broadcast request to all servers

                HashMap<String, TxnRelayResponse> responses = new HashMap<>();

                ClientRequestThread[] threads = new ClientRequestThread[allServers.size()];

                for (int i = 0; i < allServers.size(); i++) {

                    threads[i] = new ClientRequestThread(
                            GlobalConfigs.serversToPortMap.get(allServers.get(i)),
                            allServers.get(i),
                            transactionInputConfig,
                            this,
                            responses
                    );

                }

                for (int i = 0; i < this.currentActiveServers.size(); i++) {
                    threads[i].start();
                }

                for (int i = 0; i < this.currentActiveServers.size(); i++) {
                    threads[i].join();
                }

                AtomicInteger sendAgain = new AtomicInteger();
                AtomicInteger receivingReply = new AtomicInteger();

                responses.forEach((server, txnResponse) -> {

                    if (txnResponse != null){
                        if(txnResponse.getOption() == 1){
                            receivingReply.getAndIncrement();
                            this.transactionExecutionResponseMap.get(transactionInputConfig.getTransaction().getTransactionNum()).add(server);
                        }
                        else{
                            sendAgain.getAndIncrement();
                        }
                    }
                });


                Thread.sleep(100);

                if(receivingReply.get() >= GlobalConfigs.f + 1){
                    this.logger.log("Transaction executed on f+1 servers");
                    return true;
                }
                else{
                    this.logger.log("Transaction not executed on f+1 servers");

                    Thread.sleep(1500);
                    // wait until view change

                    return false;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void triggerPrintDB(){

        StringBuilder sb = new StringBuilder();
        sb.append("\n\nCommand: PrintDB \n");

        sb.append(this.datastoreHeader()).append("\n");
        for( String server : allServers) {
            CommandInput commandInput = CommandInput.newBuilder().setInput(" Test Set "+ (TestSetNumber-1)).build();
            CommandOutput op = this.serversToCommandsStub.get(server).printDB(commandInput);
            sb.append(op.getOutput()).append("\n");
        }
        sb.append("\n\n");

        this.commandLogger.log(sb.toString());

    }

    public void triggerPrintLog(){

        StringBuilder sb = new StringBuilder();
        sb.append("\n\nCommand: PrintDB \n");

        sb.append(this.datastoreHeader()).append("\n");
        for( String server : allServers) {
            CommandInput commandInput = CommandInput.newBuilder().build();
            CommandOutput op = this.serversToCommandsStub.get(server).printDB(commandInput);
            sb.append(op.getOutput()).append("\n");
        }
        sb.append("\n\n");

        this.commandLogger.log(sb.toString());

    }

    public void triggerPrintStatus(){

        StringBuilder sb = new StringBuilder();
        sb.append("\n\nCommand: Print Status - All \n");

        AtomicInteger minSequenceNumber = new AtomicInteger(1);
        AtomicInteger maxSequenceNumber = new AtomicInteger(1);

        HashMap<String, HashMap<Integer, String> > statusesAcrossServers = new HashMap<>();

        for( String server : allServers) {
            CommandInput commandInput = CommandInput.newBuilder().build();
            CommandOutput op = this.serversToCommandsStub.get(server).printStatus(commandInput);

            this.logger.log("Server: " + server + " Status: " + op.getOutput());

            statusesAcrossServers.put(server, Utils.statusToMap(op.getOutput(), minSequenceNumber, maxSequenceNumber));
        }
        sb.append("Server :  S1 - S2 - S3 - S4 - S5 - S6 - S7\n");
        for(int i = minSequenceNumber.get(); i <= maxSequenceNumber.get(); i++) {

            sb.append(" seq ").append(i).append(" : ");
            for( String server : allServers) {

                this.logger.log("Server: " + server + " SeqNum: " + i + " Status: " + statusesAcrossServers.get(server).get(i));

                sb.append( String.format("%2s", (statusesAcrossServers.get(server).get(i) != null ?  statusesAcrossServers.get(server).get(i) : "X")  )).append(" - ");
            }
            sb.append("\n");
        }


        this.commandLogger.log(sb.toString());

    }

    public void triggerPrintView(){

        this.commandLogger.log("Print View:\n" + this.printNewViewRequests() );

    }





    public void sendCommandToServers(Command commandType, HashMap<String, Boolean> activeServersStatusMap) throws InterruptedException {


        switch (commandType) {
            case PrintDB:
                triggerPrintDB();
                // op = stub.printDB(commandInput);
                break;
            case PrintLog:
                triggerPrintLog();
                break;
            case PrintStatus:
                triggerPrintStatus();
                break;
            case PrintView:
                triggerPrintView();
                break;
        }





        CommandInput commandInput = CommandInput.newBuilder().build();

        //Thread.sleep(10);


        // For only Active Servers

//        activeServersStatusMap.forEach((server, isActive) -> {
//            if (!server.equals(this.serverName) && isActive) {
//                CommandsGrpc.CommandsBlockingStub stub = this.serversToCommandsStub.get(server);
//                CommandOutput op  = CommandOutput.newBuilder().setOutput("No Output").build() ;
//
//                switch (commandType) {
//                    case PrintDB:
//                        op = stub.printDB(commandInput);
//                        break;
//                    case PrintBalance:
//                        op = stub.printBalance(commandInput);
//                        break;
//                    case PrintLog:
//                        op = stub.printLog(commandInput);
//                        break;
//                    case Performance:
//                        op = stub.performance(commandInput);
//                        break;
//                }
//
//                this.logger.log("Command: " + commandType + "\n server: " + server + "\n output: \n"+ op.getOutput());
//                //System.out.println("Command: " + commandType + "\n server: " + server + "\n output: \n"+ op.getOutput());
//            }
//        });

        activeServersStatusMap.forEach((server, isActive) -> {

            CommandsGrpc.CommandsBlockingStub stub = this.serversToCommandsStub.get(server);
            CommandOutput op  = CommandOutput.newBuilder().setOutput("No Output").build() ;

            //this.logger.log("Command: " + commandType + "\n server: " + server + "\n output: \n"+ op.getOutput());

        });

    }



    public void flush() {
        this.transactionInputConfigMap = new HashMap<>();
        this.transactionExecutionResponseMap = new ConcurrentHashMap<>();
        this.viewNumber.set(0);
        this.primaryServerName = GlobalConfigs.initLeader;
        this.tnxCount = 1;

        this.commandLogger.log("===============================================================================================================================\n");
        this.commandLogger.log("                     Test Set Number :  " + (TestSetNumber-1) + "     \n");
        this.commandLogger.log("===============================================================================================================================\n");


    }



    public static void main(String[] args) throws InterruptedException, IOException {

        ViewServer viewServer = new ViewServer(GlobalConfigs.viewServerName, GlobalConfigs.viewServerPort);

        viewServerInstance = viewServer;

        HashSet<String> commandsSet = new HashSet<>();
        commandsSet.add("PrintDB");
        commandsSet.add("PrintBalance");
        commandsSet.add("PrintLog");
        commandsSet.add("Performance");
        commandsSet.add("PrintView");


        HashMap<String, Boolean> activeServersStatusMap = new HashMap<>();

        for (String server : GlobalConfigs.allServers) {
            activeServersStatusMap.put(server, true);
        }

        String path = "src/main/resources/test(Lab2 - PBFT).csv";

        //File file = new File("C:\\Users\\mlakkoju\\apaxos-madhulakkoju\\apaxos\\src\\main\\resources\\input_file.csv");
        File file = new File(path);
        String line;
        if (file.exists()) {
            System.out.println("File exists");

            // Read the file
            BufferedReader br = new BufferedReader(new FileReader(path));

            int prevSetNumber = 0;

            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                viewServerInstance.lineNum++;

               // Thread.sleep(5);

               // System.out.println("Line: " + line);
                viewServer.logger.log("-------------------------------------------------------------\nLine: "+ viewServerInstance.lineNum +" : "+ line);

                TnxLine tnxLine = parseTnxConfig(line, viewServerInstance.tnxCount++);

                if (tnxLine == null) {
                    //System.out.println("Invalid transaction");
                    viewServerInstance.tnxCount -- ;

                    if(commandsSet.contains(line)) {
                        viewServer.sendCommandToServers(Command.valueOf(line), activeServersStatusMap);
                    }
                    continue;
                }

                TransactionInputConfig transactionInputConfig = tnxLine.transactionInputConfig;



                // Trigger Inactive servers to stop accepting transactions
                if (transactionInputConfig.getServerNamesList().isEmpty()) {
                    System.out.println("No servers to send the transaction to");
                    continue;
                }

                //Activate or deactivate Servers
                if(prevSetNumber != transactionInputConfig.getSetNumber()) {
                    prevSetNumber = transactionInputConfig.getSetNumber();

                    viewServerInstance.flush();

                    TransactionInputConfig transactionInputConfigBuf = TransactionInputConfig.newBuilder()
                            .setSetNumber(transactionInputConfig.getSetNumber())
                            .setView(transactionInputConfig.getView())
                            .setTransaction(

                                    Transaction.newBuilder()
                                            .setTransactionNum(viewServerInstance.tnxCount++)
                                            .setSender(transactionInputConfig.getTransaction().getSender())
                                            .setReceiver(transactionInputConfig.getTransaction().getReceiver())
                                            .setAmount(transactionInputConfig.getTransaction().getAmount())
                                            .setTransactionHash(transactionInputConfig.getTransaction().getTransactionHash())
                                            .setTimestamp(transactionInputConfig.getTransaction().getTimestamp())
                                            .build()
                            )
                            .addAllServerNames( transactionInputConfig.getServerNamesList() )
                            .build();

                    transactionInputConfig = transactionInputConfigBuf;

                    // If the Test Set Number changes, then trigger the inactive servers to stop accepting transactions

                    // Set all servers inactive
                    for (String server : allServers) {
                        activeServersStatusMap.put(server, false);
                    }
                    // Set the active servers
                    for (String server : transactionInputConfig.getServerNamesList()) {
                        activeServersStatusMap.put(server, true);
                    }

                   // Thread.sleep(100);
                    System.out.println("Press Enter to continue to next Test set. This will activate the servers and publish transactions to servers."+transactionInputConfig.getSetNumber());
                    String a  = System.console().readLine();


                    viewServer.sendCommandToServers(Command.valueOf("PrintDB"), activeServersStatusMap);
                    viewServer.sendCommandToServers(Command.valueOf("PrintStatus"), activeServersStatusMap);
                    viewServer.sendCommandToServers(Command.valueOf("PrintView"), activeServersStatusMap);



                    Thread.sleep(100);

                    for( String server : allServers) {
                        viewServerInstance.serversToCommandsStub.get(server).flushDB(CommandInput.newBuilder().setInput("Test Set : "+ viewServerInstance.TestSetNumber ).build());

                        if(activeServersStatusMap.get(server)) {
                            //System.out.println("Server: " + server + " is active");

                            ActivateServerRequest request = ActivateServerRequest.newBuilder()
                                    .setServerName(server)
                                    .build();

                            ActivateServerResponse response = viewServer.serversToActivateServersStub.get(server).activateServer(request);

//                            if(response.getSuccess()) {
//                                System.out.println("Server: " + server + " is activated");
//                            } else {
//                                System.out.println("Server: " + server + " is not activated");
//                            }

                        }

                        else {
                           // System.out.println("Server: " + server + " is inactive");

                            DeactivateServerRequest request = DeactivateServerRequest.newBuilder()
                                    .setServerName(server)
                                    .build();

                            ActivateServersGrpc.ActivateServersBlockingStub stub = viewServer.serversToActivateServersStub.get(server);

                            DeactivateServerResponse response = stub.deactivateServer(request);
//
//                            if(response.getSuccess()) {
//                                System.out.println("Server: " + server + " is deactivated");
//                            } else {
//                                System.out.println("Server: " + server + " is not deactivated");
//                            }

                        }

                        viewServerInstance.serversToCommandsStub.get(server).makeByzantine(CommandInput.newBuilder().setInput("0").build());
                        // 0 -> Normal, 1 -> Byzantine
                    }
//                    viewServerInstance.flush();

                    //Thread.sleep(100);


                    for (String server : tnxLine.maliciousServers) {

                        if(viewServerInstance.serversToCommandsStub.containsKey(server))
                            viewServerInstance.serversToCommandsStub.get(server).makeByzantine(CommandInput.newBuilder().setInput("1").build());

                    }


                }
                else{
                    //System.out.println("Same set number");
                }

                boolean isSuccess = viewServer.sendTransactionToServers(transactionInputConfig, activeServersStatusMap);

                Thread.sleep(1000);

//                if(! isSuccess)
//                    viewServer.sendTransactionToServers(transactionInputConfig, activeServersStatusMap);

                // Multicast Transactions to active servers

            }

        }
        else {
            System.out.println("File does not exist");
        }

        System.out.println("Running All Commands on all servers");
        Thread.sleep(1000);

        viewServer.sendCommandToServers(Command.valueOf("PrintStatus"), activeServersStatusMap);
        viewServer.sendCommandToServers(Command.valueOf("PrintDB"), activeServersStatusMap);



        System.out.println("All the Commands have been executed and you can find outputs in Logs. \nFreestyle from now on. use Commands only. Type STOP to stop the view server / Client");

        while(true) {
            String inputCommand = System.console().readLine();

            if(commandsSet.contains(inputCommand)) {
                viewServer.sendCommandToServers(Command.valueOf(inputCommand), activeServersStatusMap);
            }
            if(inputCommand.equalsIgnoreCase("STOP")) {
                break;
            }
        }


        viewServer.server.awaitTermination();

    }


}
