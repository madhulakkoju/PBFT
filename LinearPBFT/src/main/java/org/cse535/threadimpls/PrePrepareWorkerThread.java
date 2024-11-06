package org.cse535.threadimpls;

import org.cse535.node.Node;
import org.cse535.proto.LinearPBFTGrpc;
import org.cse535.proto.PrePrepareRequest;
import org.cse535.proto.PrePrepareResponse;
import org.cse535.proto.PrepareResponse;

import java.util.ArrayList;

public class PrePrepareWorkerThread extends Thread{
    private final Node node;
    private final int targetPort;
    private final String targetServerName;

    private final PrePrepareRequest prePrepareRequest;

    public PrePrepareWorkerThread(Node node, int targetPort, String targetServerName, PrePrepareRequest prePrepareRequest) {
        this.node = node;
        this.targetPort = targetPort;
        this.targetServerName = targetServerName;
        this.prePrepareRequest = prePrepareRequest;
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

        PrePrepareResponse resp = blockingStub.prePrepare(this.prePrepareRequest);

        if(!this.node.database.prePrepareResponseMap.containsKey(this.prePrepareRequest.getSequenceNumber())){
            this.node.database.prePrepareResponseMap.put(this.prePrepareRequest.getSequenceNumber(), new ArrayList<>());
        }

        this.node.database.prePrepareResponseMap
                .get(this.prePrepareRequest.getSequenceNumber())
                .add(resp);

    }

}
