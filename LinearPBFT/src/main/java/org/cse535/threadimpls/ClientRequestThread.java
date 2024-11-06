package org.cse535.threadimpls;

import org.cse535.node.ViewServer;
import org.cse535.proto.LinearPBFTGrpc;
import org.cse535.proto.TransactionInputConfig;
import org.cse535.proto.TxnRelayResponse;
import org.cse535.proto.TxnResponse;

import java.util.HashMap;

public class ClientRequestThread extends Thread {

    public ViewServer viewServer;
    public int targetPort;
    public String targetServerName;
    public TransactionInputConfig request;
    public HashMap<String, TxnRelayResponse> responseMap;

    public ClientRequestThread(int targetPort, String targetServerName,
                               TransactionInputConfig request, ViewServer viewServer, HashMap<String, TxnRelayResponse> responseMap) {
        this.targetPort = targetPort;
        this.targetServerName = targetServerName;
        this.request = request;
        this.viewServer = viewServer;
        this.responseMap = responseMap;
    }

    @Override
    public void run() {
        LinearPBFTGrpc.LinearPBFTBlockingStub blockingStub = LinearPBFTGrpc.newBlockingStub(
                this.viewServer.serversToChannel.get(this.targetServerName)
        );

        TxnRelayResponse response = blockingStub.relayRequest(this.request);
        this.responseMap.put(this.targetServerName, response);
    }
}
