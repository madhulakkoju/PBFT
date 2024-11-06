package org.cse535.threadimpls;

import org.cse535.node.Node;
import org.cse535.node.ViewServer;
import org.cse535.proto.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class SendTnxWorkerThread  extends Thread{
    private final ViewServer node;
    private final int targetPort;
    private final String targetServerName;

    private final TransactionInputConfig transactionInputConfig;
    private final AtomicBoolean isTransactionSent;

    public SendTnxWorkerThread(ViewServer node, int targetPort, String targetServerName, TransactionInputConfig transactionInputConfig, AtomicBoolean isTransactionSent) {
        this.node = node;
        this.targetPort = targetPort;
        this.targetServerName = targetServerName;
        this.transactionInputConfig = transactionInputConfig;
        this.isTransactionSent = isTransactionSent;
    }

    @Override
    public void run() {
//        Main.node.logger.log("PrepareWorkerThread: " + this.targetServerName + " started");
//        Main.node.logger.log("Server to channel: " + this.node.serversToChannel.toString());

        LinearPBFTGrpc.LinearPBFTBlockingStub blockingStub = LinearPBFTGrpc.newBlockingStub(
                this.node.serversToChannel.get(this.targetServerName)
        );

        TxnResponse resp = blockingStub.request(this.transactionInputConfig);

        this.isTransactionSent.set( this.isTransactionSent.get() || resp.getSuccess());

    }

}
