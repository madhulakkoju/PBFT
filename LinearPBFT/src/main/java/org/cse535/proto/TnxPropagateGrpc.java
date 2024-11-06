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
public final class TnxPropagateGrpc {

  private TnxPropagateGrpc() {}

  public static final String SERVICE_NAME = "TnxPropagate";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig,
      org.cse535.proto.TxnResponse> getPropagateTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "propagateTransaction",
      requestType = org.cse535.proto.TransactionInputConfig.class,
      responseType = org.cse535.proto.TxnResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig,
      org.cse535.proto.TxnResponse> getPropagateTransactionMethod() {
    io.grpc.MethodDescriptor<org.cse535.proto.TransactionInputConfig, org.cse535.proto.TxnResponse> getPropagateTransactionMethod;
    if ((getPropagateTransactionMethod = TnxPropagateGrpc.getPropagateTransactionMethod) == null) {
      synchronized (TnxPropagateGrpc.class) {
        if ((getPropagateTransactionMethod = TnxPropagateGrpc.getPropagateTransactionMethod) == null) {
          TnxPropagateGrpc.getPropagateTransactionMethod = getPropagateTransactionMethod = 
              io.grpc.MethodDescriptor.<org.cse535.proto.TransactionInputConfig, org.cse535.proto.TxnResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TnxPropagate", "propagateTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.TransactionInputConfig.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cse535.proto.TxnResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new TnxPropagateMethodDescriptorSupplier("propagateTransaction"))
                  .build();
          }
        }
     }
     return getPropagateTransactionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TnxPropagateStub newStub(io.grpc.Channel channel) {
    return new TnxPropagateStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TnxPropagateBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TnxPropagateBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TnxPropagateFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TnxPropagateFutureStub(channel);
  }

  /**
   */
  public static abstract class TnxPropagateImplBase implements io.grpc.BindableService {

    /**
     */
    public void propagateTransaction(org.cse535.proto.TransactionInputConfig request,
        io.grpc.stub.StreamObserver<org.cse535.proto.TxnResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPropagateTransactionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPropagateTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.cse535.proto.TransactionInputConfig,
                org.cse535.proto.TxnResponse>(
                  this, METHODID_PROPAGATE_TRANSACTION)))
          .build();
    }
  }

  /**
   */
  public static final class TnxPropagateStub extends io.grpc.stub.AbstractStub<TnxPropagateStub> {
    private TnxPropagateStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TnxPropagateStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TnxPropagateStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TnxPropagateStub(channel, callOptions);
    }

    /**
     */
    public void propagateTransaction(org.cse535.proto.TransactionInputConfig request,
        io.grpc.stub.StreamObserver<org.cse535.proto.TxnResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPropagateTransactionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TnxPropagateBlockingStub extends io.grpc.stub.AbstractStub<TnxPropagateBlockingStub> {
    private TnxPropagateBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TnxPropagateBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TnxPropagateBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TnxPropagateBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.cse535.proto.TxnResponse propagateTransaction(org.cse535.proto.TransactionInputConfig request) {
      return blockingUnaryCall(
          getChannel(), getPropagateTransactionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TnxPropagateFutureStub extends io.grpc.stub.AbstractStub<TnxPropagateFutureStub> {
    private TnxPropagateFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TnxPropagateFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TnxPropagateFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TnxPropagateFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cse535.proto.TxnResponse> propagateTransaction(
        org.cse535.proto.TransactionInputConfig request) {
      return futureUnaryCall(
          getChannel().newCall(getPropagateTransactionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PROPAGATE_TRANSACTION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TnxPropagateImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TnxPropagateImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PROPAGATE_TRANSACTION:
          serviceImpl.propagateTransaction((org.cse535.proto.TransactionInputConfig) request,
              (io.grpc.stub.StreamObserver<org.cse535.proto.TxnResponse>) responseObserver);
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

  private static abstract class TnxPropagateBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TnxPropagateBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.cse535.proto.Linearpbft.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TnxPropagate");
    }
  }

  private static final class TnxPropagateFileDescriptorSupplier
      extends TnxPropagateBaseDescriptorSupplier {
    TnxPropagateFileDescriptorSupplier() {}
  }

  private static final class TnxPropagateMethodDescriptorSupplier
      extends TnxPropagateBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TnxPropagateMethodDescriptorSupplier(String methodName) {
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
      synchronized (TnxPropagateGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TnxPropagateFileDescriptorSupplier())
              .addMethod(getPropagateTransactionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
