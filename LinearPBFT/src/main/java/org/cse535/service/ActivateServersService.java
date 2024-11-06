package org.cse535.service;

import io.grpc.stub.StreamObserver;
import org.cse535.Main;
import org.cse535.proto.ActivateServersGrpc;
import org.cse535.proto.DeactivateServerRequest;
import org.cse535.proto.DeactivateServerResponse;

public class ActivateServersService extends ActivateServersGrpc.ActivateServersImplBase {

    @Override
    public void activateServer(org.cse535.proto.ActivateServerRequest request,
                               io.grpc.stub.StreamObserver<org.cse535.proto.ActivateServerResponse> responseObserver) {
        Main.node.logger.log("Server activated");
        Main.node.database.isServerActive.set(true);
        responseObserver.onNext(org.cse535.proto.ActivateServerResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void deactivateServer(DeactivateServerRequest request, StreamObserver<DeactivateServerResponse> responseObserver) {
        Main.node.logger.log("Server deactivated");
        Main.node.database.isServerActive.set(false);
        responseObserver.onNext(DeactivateServerResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }
}
