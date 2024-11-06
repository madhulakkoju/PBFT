package org.cse535.threadimpls;

import org.cse535.node.Node;

public class DatabaseBackupThread extends Thread{

    public Node node;

    public DatabaseBackupThread(Node node){
        this.node = node;
    }

    @Override

    public void run() {
        while(true){
            try {
                Thread.sleep(10000);

             //   this.node.saveDatabaseSnapshot();

                System.out.println("Backingup Database");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
