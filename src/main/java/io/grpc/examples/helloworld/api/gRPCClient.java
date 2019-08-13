package io.grpc.examples.helloworld.api;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

import java.util.concurrent.TimeUnit;

public class gRPCClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public gRPCClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public gRPCClient(String host, int port) {
//        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
        this(ManagedChannelBuilder.forTarget("dns:///localhost:50051").usePlaintext().build());
    }

    public void greet() {

        HelloRequest request = HelloRequest.newBuilder().setName("Hello!").build();
        System.out.println("client send " + request.getName() + System.currentTimeMillis());
        HelloReply response;
        response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());
        request = HelloRequest.newBuilder().setName("Hello!").build();
        System.out.println("client send " + request.getName() + System.currentTimeMillis());
    }

    public void shutDown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        gRPCClient client = new gRPCClient("localhost", 50051);
        client.greet();
    }
}
