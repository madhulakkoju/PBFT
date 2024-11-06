package org.cse535.threadimpls;

import org.cse535.node.Node;
import org.cse535.proto.*;

public class ViewChangeWorkerThread extends Thread{
    private final Node node;
    private final int targetPort;
    private final String targetServerName;

    private final ViewChangeRequest viewChangeRequest;

    public ViewChangeWorkerThread(Node node, int targetPort, String targetServerName, ViewChangeRequest viewChangeRequest) {
        this.node = node;
        this.targetPort = targetPort;
        this.targetServerName = targetServerName;
        this.viewChangeRequest = viewChangeRequest;
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

        blockingStub.viewChange(this.viewChangeRequest);
    }
}
