package org.cse535.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: linearpbft.proto")
public final class LinearPBFTGrpc {

  private LinearPBFTGrpc() {}

  public static final String SERVICE_NAME = "LinearPBFT";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig,
      org.cse535.proto.TxnResponse> getRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Request",
      requestType = org.cse535.proto.TransactionInputConfig.class,
      responseType = org.cse535.proto.TxnResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig,
      org.cse535.proto.TxnResponse> getRequestMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig, org.cse535.proto.TxnResponse> getRequestMethod;
    if ((getRequestMethod = LinearPBFTGrpc.getRequestMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getRequestMethod = LinearPBFTGrpc.getRequestMethod) == null) {
          LinearPBFTGrpc.getRequestMethod = getRequestMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.TransactionInputConfig, org.cse535.proto.TxnResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "Request"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.TransactionInputConfig.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.TxnResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("Request"))
                  .build();
          }
        }
     }
     return getRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig,
      org.cse535.proto.TxnRelayResponse> getRelayRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RelayRequest",
      requestType = org.cse535.proto.TransactionInputConfig.class,
      responseType = org.cse535.proto.TxnRelayResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig,
      org.cse535.proto.TxnRelayResponse> getRelayRequestMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig, org.cse535.proto.TxnRelayResponse> getRelayRequestMethod;
    if ((getRelayRequestMethod = LinearPBFTGrpc.getRelayRequestMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getRelayRequestMethod = LinearPBFTGrpc.getRelayRequestMethod) == null) {
          LinearPBFTGrpc.getRelayRequestMethod = getRelayRequestMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.TransactionInputConfig, org.cse535.proto.TxnRelayResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "RelayRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.TransactionInputConfig.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.TxnRelayResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("RelayRequest"))
                  .build();
          }
        }
     }
     return getRelayRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.PrePrepareRequest,
      org.cse535.proto.PrePrepareResponse> getPrePrepareMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PrePrepare",
      requestType = org.cse535.proto.PrePrepareRequest.class,
      responseType = org.cse535.proto.PrePrepareResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.PrePrepareRequest,
      org.cse535.proto.PrePrepareResponse> getPrePrepareMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.PrePrepareRequest, org.cse535.proto.PrePrepareResponse> getPrePrepareMethod;
    if ((getPrePrepareMethod = LinearPBFTGrpc.getPrePrepareMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getPrePrepareMethod = LinearPBFTGrpc.getPrePrepareMethod) == null) {
          LinearPBFTGrpc.getPrePrepareMethod = getPrePrepareMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.PrePrepareRequest, org.cse535.proto.PrePrepareResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "PrePrepare"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.PrePrepareRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.PrePrepareResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("PrePrepare"))
                  .build();
          }
        }
     }
     return getPrePrepareMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.PrepareRequest,
      org.cse535.proto.PrepareResponse> getPrepareMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Prepare",
      requestType = org.cse535.proto.PrepareRequest.class,
      responseType = org.cse535.proto.PrepareResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.PrepareRequest,
      org.cse535.proto.PrepareResponse> getPrepareMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.PrepareRequest, org.cse535.proto.PrepareResponse> getPrepareMethod;
    if ((getPrepareMethod = LinearPBFTGrpc.getPrepareMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getPrepareMethod = LinearPBFTGrpc.getPrepareMethod) == null) {
          LinearPBFTGrpc.getPrepareMethod = getPrepareMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.PrepareRequest, org.cse535.proto.PrepareResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "Prepare"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.PrepareRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.PrepareResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("Prepare"))
                  .build();
          }
        }
     }
     return getPrepareMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.CommitRequest,
      org.cse535.proto.CommitResponse> getCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Commit",
      requestType = org.cse535.proto.CommitRequest.class,
      responseType = org.cse535.proto.CommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.CommitRequest,
      org.cse535.proto.CommitResponse> getCommitMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.CommitRequest, org.cse535.proto.CommitResponse> getCommitMethod;
    if ((getCommitMethod = LinearPBFTGrpc.getCommitMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getCommitMethod = LinearPBFTGrpc.getCommitMethod) == null) {
          LinearPBFTGrpc.getCommitMethod = getCommitMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.CommitRequest, org.cse535.proto.CommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "Commit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.CommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.CommitResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("Commit"))
                  .build();
          }
        }
     }
     return getCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.ExecutionReplyRequest,
      org.cse535.proto.ExecutionReplyResponse> getExecutionReplyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExecutionReply",
      requestType = org.cse535.proto.ExecutionReplyRequest.class,
      responseType = org.cse535.proto.ExecutionReplyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.ExecutionReplyRequest,
      org.cse535.proto.ExecutionReplyResponse> getExecutionReplyMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.ExecutionReplyRequest, org.cse535.proto.ExecutionReplyResponse> getExecutionReplyMethod;
    if ((getExecutionReplyMethod = LinearPBFTGrpc.getExecutionReplyMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getExecutionReplyMethod = LinearPBFTGrpc.getExecutionReplyMethod) == null) {
          LinearPBFTGrpc.getExecutionReplyMethod = getExecutionReplyMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.ExecutionReplyRequest, org.cse535.proto.ExecutionReplyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "ExecutionReply"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.ExecutionReplyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.ExecutionReplyResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("ExecutionReply"))
                  .build();
          }
        }
     }
     return getExecutionReplyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.ViewChangeRequest,
      org.cse535.proto.ViewChangeResponse> getViewChangeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ViewChange",
      requestType = org.cse535.proto.ViewChangeRequest.class,
      responseType = org.cse535.proto.ViewChangeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.ViewChangeRequest,
      org.cse535.proto.ViewChangeResponse> getViewChangeMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.ViewChangeRequest, org.cse535.proto.ViewChangeResponse> getViewChangeMethod;
    if ((getViewChangeMethod = LinearPBFTGrpc.getViewChangeMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getViewChangeMethod = LinearPBFTGrpc.getViewChangeMethod) == null) {
          LinearPBFTGrpc.getViewChangeMethod = getViewChangeMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.ViewChangeRequest, org.cse535.proto.ViewChangeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "ViewChange"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.ViewChangeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.ViewChangeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("ViewChange"))
                  .build();
          }
        }
     }
     return getViewChangeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.NewViewRequest,
      org.cse535.proto.NewViewResponse> getNewViewMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NewView",
      requestType = org.cse535.proto.NewViewRequest.class,
      responseType = org.cse535.proto.NewViewResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.NewViewRequest,
      org.cse535.proto.NewViewResponse> getNewViewMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.NewViewRequest, org.cse535.proto.NewViewResponse> getNewViewMethod;
    if ((getNewViewMethod = LinearPBFTGrpc.getNewViewMethod) == null) {
      synchronized (LinearPBFTGrpc.class) {
        if ((getNewViewMethod = LinearPBFTGrpc.getNewViewMethod) == null) {
          LinearPBFTGrpc.getNewViewMethod = getNewViewMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.NewViewRequest, org.cse535.proto.NewViewResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LinearPBFT", "NewView"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.NewViewRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.NewViewResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LinearPBFTMethodDescriptorSupplier("NewView"))
                  .build();
          }
        }
     }
     return getNewViewMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LinearPBFTStub newStub(io.grpc.Channel channel) {
    return new LinearPBFTStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LinearPBFTBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new LinearPBFTBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LinearPBFTFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new LinearPBFTFutureStub(channel);
  }

  /**
   */
  public static abstract class LinearPBFTImplBase implements io.grpc.BindableService {

    /**
     */
    public void request(org.cse535.proto.TransactionInputConfig request,
        io.grpc.stub.StreamObserver<org.cse535.proto.TxnResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRequestMethod(), responseObserver);
    }

    /**
     */
    public void relayRequest(org.cse535.proto.TransactionInputConfig request,
        io.grpc.stub.StreamObserver<org.cse535.proto.TxnRelayResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRelayRequestMethod(), responseObserver);
    }

    /**
     */
    public void prePrepare(org.cse535.proto.PrePrepareRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.PrePrepareResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPrePrepareMethod(), responseObserver);
    }

    /**
     */
    public void prepare(org.cse535.proto.PrepareRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.PrepareResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPrepareMethod(), responseObserver);
    }

    /**
     */
    public void commit(org.cse535.proto.CommitRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.CommitResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCommitMethod(), responseObserver);
    }

    /**
     */
    public void executionReply(org.cse535.proto.ExecutionReplyRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.ExecutionReplyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getExecutionReplyMethod(), responseObserver);
    }

    /**
     */
    public void viewChange(org.cse535.proto.ViewChangeRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.ViewChangeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getViewChangeMethod(), responseObserver);
    }

    /**
     */
    public void newView(org.cse535.proto.NewViewRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.NewViewResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getNewViewMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRequestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.TransactionInputConfig,
                org.cse535.proto.TxnResponse>(
                  this, METHODID_REQUEST)))
          .addMethod(
            getRelayRequestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.TransactionInputConfig,
                org.cse535.proto.TxnRelayResponse>(
                  this, METHODID_RELAY_REQUEST)))
          .addMethod(
            getPrePrepareMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.PrePrepareRequest,
                org.cse535.proto.PrePrepareResponse>(
                  this, METHODID_PRE_PREPARE)))
          .addMethod(
            getPrepareMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.PrepareRequest,
                org.cse535.proto.PrepareResponse>(
                  this, METHODID_PREPARE)))
          .addMethod(
            getCommitMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.CommitRequest,
                org.cse535.proto.CommitResponse>(
                  this, METHODID_COMMIT)))
          .addMethod(
            getExecutionReplyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.ExecutionReplyRequest,
                org.cse535.proto.ExecutionReplyResponse>(
                  this, METHODID_EXECUTION_REPLY)))
          .addMethod(
            getViewChangeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.ViewChangeRequest,
                org.cse535.proto.ViewChangeResponse>(
                  this, METHODID_VIEW_CHANGE)))
          .addMethod(
            getNewViewMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.NewViewRequest,
                org.cse535.proto.NewViewResponse>(
                  this, METHODID_NEW_VIEW)))
          .build();
    }
  }

  /**
   */
  public static final class LinearPBFTStub extends io.grpc.stub.AbstractStub<LinearPBFTStub> {
    private LinearPBFTStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LinearPBFTStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LinearPBFTStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LinearPBFTStub(channel, callOptions);
    }

    /**
     */
    public void request(org.cse535.proto.TransactionInputConfig request,
        io.grpc.stub.StreamObserver<org.cse535.proto.TxnResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void relayRequest(org.cse535.proto.TransactionInputConfig request,
        io.grpc.stub.StreamObserver<org.cse535.proto.TxnRelayResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRelayRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void prePrepare(org.cse535.proto.PrePrepareRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.PrePrepareResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPrePrepareMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void prepare(org.cse535.proto.PrepareRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.PrepareResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPrepareMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commit(org.cse535.proto.CommitRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.CommitResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void executionReply(org.cse535.proto.ExecutionReplyRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.ExecutionReplyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getExecutionReplyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void viewChange(org.cse535.proto.ViewChangeRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.ViewChangeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getViewChangeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void newView(org.cse535.proto.NewViewRequest request,
        io.grpc.stub.StreamObserver<org.cse535.proto.NewViewResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNewViewMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LinearPBFTBlockingStub extends io.grpc.stub.AbstractStub<LinearPBFTBlockingStub> {
    private LinearPBFTBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LinearPBFTBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LinearPBFTBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LinearPBFTBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.cse535.proto.TxnResponse request(org.cse535.proto.TransactionInputConfig request) {
      return blockingUnaryCall(
          getChannel(), getRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.TxnRelayResponse relayRequest(org.cse535.proto.TransactionInputConfig request) {
      return blockingUnaryCall(
          getChannel(), getRelayRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.PrePrepareResponse prePrepare(org.cse535.proto.PrePrepareRequest request) {
      return blockingUnaryCall(
          getChannel(), getPrePrepareMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.PrepareResponse prepare(org.cse535.proto.PrepareRequest request) {
      return blockingUnaryCall(
          getChannel(), getPrepareMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.CommitResponse commit(org.cse535.proto.CommitRequest request) {
      return blockingUnaryCall(
          getChannel(), getCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.ExecutionReplyResponse executionReply(org.cse535.proto.ExecutionReplyRequest request) {
      return blockingUnaryCall(
          getChannel(), getExecutionReplyMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.ViewChangeResponse viewChange(org.cse535.proto.ViewChangeRequest request) {
      return blockingUnaryCall(
          getChannel(), getViewChangeMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cse535.proto.NewViewResponse newView(org.cse535.proto.NewViewRequest request) {
      return blockingUnaryCall(
          getChannel(), getNewViewMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LinearPBFTFutureStub extends io.grpc.stub.AbstractStub<LinearPBFTFutureStub> {
    private LinearPBFTFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LinearPBFTFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LinearPBFTFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LinearPBFTFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.TxnResponse> request(
        org.cse535.proto.TransactionInputConfig request) {
      return futureUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.TxnRelayResponse> relayRequest(
        org.cse535.proto.TransactionInputConfig request) {
      return futureUnaryCall(
          getChannel().newCall(getRelayRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.PrePrepareResponse> prePrepare(
        org.cse535.proto.PrePrepareRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPrePrepareMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.PrepareResponse> prepare(
        org.cse535.proto.PrepareRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPrepareMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.CommitResponse> commit(
        org.cse535.proto.CommitRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.ExecutionReplyResponse> executionReply(
        org.cse535.proto.ExecutionReplyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getExecutionReplyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.ViewChangeResponse> viewChange(
        org.cse535.proto.ViewChangeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getViewChangeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.NewViewResponse> newView(
        org.cse535.proto.NewViewRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNewViewMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST = 0;
  private static final int METHODID_RELAY_REQUEST = 1;
  private static final int METHODID_PRE_PREPARE = 2;
  private static final int METHODID_PREPARE = 3;
  private static final int METHODID_COMMIT = 4;
  private static final int METHODID_EXECUTION_REPLY = 5;
  private static final int METHODID_VIEW_CHANGE = 6;
  private static final int METHODID_NEW_VIEW = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LinearPBFTImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LinearPBFTImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST:
          serviceImpl.request((org.cse535.proto.TransactionInputConfig) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.TxnResponse>) responseObserver);
          break;
        case METHODID_RELAY_REQUEST:
          serviceImpl.relayRequest((org.cse535.proto.TransactionInputConfig) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.TxnRelayResponse>) responseObserver);
          break;
        case METHODID_PRE_PREPARE:
          serviceImpl.prePrepare((org.cse535.proto.PrePrepareRequest) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.PrePrepareResponse>) responseObserver);
          break;
        case METHODID_PREPARE:
          serviceImpl.prepare((org.cse535.proto.PrepareRequest) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.PrepareResponse>) responseObserver);
          break;
        case METHODID_COMMIT:
          serviceImpl.commit((org.cse535.proto.CommitRequest) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.CommitResponse>) responseObserver);
          break;
        case METHODID_EXECUTION_REPLY:
          serviceImpl.executionReply((org.cse535.proto.ExecutionReplyRequest) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.ExecutionReplyResponse>) responseObserver);
          break;
        case METHODID_VIEW_CHANGE:
          serviceImpl.viewChange((org.cse535.proto.ViewChangeRequest) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.ViewChangeResponse>) responseObserver);
          break;
        case METHODID_NEW_VIEW:
          serviceImpl.newView((org.cse535.proto.NewViewRequest) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.NewViewResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class LinearPBFTBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LinearPBFTBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.cse535.proto.Linearpbft.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LinearPBFT");
    }
  }

  private static final class LinearPBFTFileDescriptorSupplier
      extends LinearPBFTBaseDescriptorSupplier {
    LinearPBFTFileDescriptorSupplier() {}
  }

  private static final class LinearPBFTMethodDescriptorSupplier
      extends LinearPBFTBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LinearPBFTMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LinearPBFTGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LinearPBFTFileDescriptorSupplier())
              .addMethod(getRequestMethod())
              .addMethod(getRelayRequestMethod())
              .addMethod(getPrePrepareMethod())
              .addMethod(getPrepareMethod())
              .addMethod(getCommitMethod())
              .addMethod(getExecutionReplyMethod())
              .addMethod(getViewChangeMethod())
              .addMethod(getNewViewMethod())
              .build();
        }
      }
    }
    return result;
  }
}
