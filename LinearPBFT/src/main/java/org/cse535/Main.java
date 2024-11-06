package org.cse535;

import org.cse535.configs.GlobalConfigs;
import org.cse535.loggers.LogUtils;
import org.cse535.node.Node;

import java.util.logging.Logger;

public class Main {

    public static LogUtils commonLogger;

    public static Node node;

    public static void main(String[] args) throws InterruptedException {
        String serverName = args[0];
        commonLogger = new LogUtils( "Common", GlobalConfigs.serversToPortMap.get(serverName));

        node = new Node( serverName, GlobalConfigs.serversToPortMap.get(serverName));
        node.server.awaitTermination();
    }

}
