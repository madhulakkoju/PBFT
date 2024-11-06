package org.cse535.threadimpls;

import org.cse535.configs.GlobalConfigs;
import org.cse535.node.Node;
import org.cse535.proto.LinearPBFTGrpc;
import org.cse535.proto.NewViewRequest;
import org.cse535.proto.NewViewResponse;
import org.cse535.proto.ViewChangeRequest;

public class NewViewWorkerThread extends Thread{
    private final Node node;
    public final int targetPort;
    public final String targetServerName;

    private final NewViewRequest newViewRequest;

    public NewViewWorkerThread(Node node, int targetPort, String targetServerName, NewViewRequest newViewRequest) {
        this.node = node;
        this.targetPort = targetPort;
        this.targetServerName = targetServerName;
        this.newViewRequest = newViewRequest;
    }

    @Override
    public void run() {
//        if(this.targetPort == this.node.port && this.node.port != GlobalConfigs.viewServerPort){
//            return;
//        }
//        Main.node.logger.log("PrepareWorkerThread: " + this.targetServerName + " started");
//        Main.node.logger.log("Server to channel: " + this.node.serversToChannel.toString());
//        this.node.logger.log("New View Response from View Server: CCCCCCCCCCC \n" + targetPort + " " + targetServerName + " " + newViewRequest.toString());

        LinearPBFTGrpc.LinearPBFTBlockingStub blockingStub = LinearPBFTGrpc.newBlockingStub(
                this.node.serversToChannel.get(this.targetServerName)
        );

        NewViewResponse resp = blockingStub.newView(this.newViewRequest);

//            this.node.logger.log("New View Response from View Server: CCCCCCCCCCC \n"+ targetPort+ " \n" + resp.toString());
//            System.out.println("New View Response from View Server: CCCCCCCCCCC \n" + resp.toString());


    }
}
