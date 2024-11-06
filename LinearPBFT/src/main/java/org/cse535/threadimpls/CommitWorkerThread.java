package org.cse535.threadimpls;

import org.cse535.node.Node;
import org.cse535.proto.*;

public class CommitWorkerThread extends Thread {

    private final Node node;
    private final int targetPort;
    private final String targetServerName;

    private final CommitRequest commitRequest;

    public CommitWorkerThread(Node node, int targetPort, String targetServerName, CommitRequest commitRequest) {
        this.node = node;
        this.targetPort = targetPort;
        this.targetServerName = targetServerName;
        this.commitRequest = commitRequest;
    }

    @Override
    public void run() {
        if(this.targetPort == this.node.port){
            return;
        }
//        Main.node.logger.log("PrepareWorkerThread: " + this.targetServerName + " started");
//        Main.node.logger.log("Server to channel: " + this.node.serversToChannel.toString());

        LinearPBFTGrpc.LinearPBFTBlockingStub blockingStub = LinearPBFTGrpc.newBlockingStub(
                this.node.serversToChannel.get(this.targetServerName)
        );

        blockingStub.commit(this.commitRequest);

    }

}