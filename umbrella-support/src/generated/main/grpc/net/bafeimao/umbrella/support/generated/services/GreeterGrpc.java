package net.bafeimao.umbrella.support.generated.services;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "net.bafeimao.umbrella.support.generated.services.Greeter";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest,
      net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply> METHOD_SAY_HELLO =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "net.bafeimao.umbrella.support.generated.services.Greeter", "SayHello"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply.getDefaultInstance()));

  public static GreeterStub newStub(io.grpc.Channel channel) {
    return new GreeterStub(channel);
  }

  public static GreeterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GreeterBlockingStub(channel);
  }

  public static GreeterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GreeterFutureStub(channel);
  }

  public static interface Greeter {

    public void sayHello(net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request,
        io.grpc.stub.StreamObserver<net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply> responseObserver);
  }

  public static interface GreeterBlockingClient {

    public net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply sayHello(net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request);
  }

  public static interface GreeterFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply> sayHello(
        net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request);
  }

  public static class GreeterStub extends io.grpc.stub.AbstractStub<GreeterStub>
      implements Greeter {
    private GreeterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    @java.lang.Override
    public void sayHello(net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request,
        io.grpc.stub.StreamObserver<net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request, responseObserver);
    }
  }

  public static class GreeterBlockingStub extends io.grpc.stub.AbstractStub<GreeterBlockingStub>
      implements GreeterBlockingClient {
    private GreeterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply sayHello(net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request) {
      return blockingUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request);
    }
  }

  public static class GreeterFutureStub extends io.grpc.stub.AbstractStub<GreeterFutureStub>
      implements GreeterFutureClient {
    private GreeterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply> sayHello(
        net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request);
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final Greeter serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
      .addMethod(
        METHOD_SAY_HELLO,
        asyncUnaryCall(
          new io.grpc.stub.ServerCalls.UnaryMethod<
              net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest,
              net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply>() {
            @java.lang.Override
            public void invoke(
                net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloRequest request,
                io.grpc.stub.StreamObserver<net.bafeimao.umbrella.support.generated.services.ServiceProto.HelloReply> responseObserver) {
              serviceImpl.sayHello(request, responseObserver);
            }
          })).build();
  }
}
