package org.apache.dubbo.rpc.protocol.grpc.api;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.dubbo.rpc.protocol.grpc.GreeterGrpc;
import org.apache.dubbo.rpc.protocol.grpc.HelloService;

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
        this(ManagedChannelBuilder.forTarget("dns:///localhost:8848").usePlaintext().build());
    }

    public void greet() {

        HelloService.HelloRequest request = HelloService.HelloRequest.newBuilder().setRequestData("Hello!").build();
        System.out.println("client send " + request.getRequestData() + System.currentTimeMillis());
        HelloService.HelloResponse response;
        response = blockingStub.helloWorld(request);
        System.out.println(response.getResponseData());
        request = HelloService.HelloRequest.newBuilder().setRequestData("Hello!").build();
        System.out.println("client send " + request.getRequestData() + System.currentTimeMillis());
    }

    public void shutDown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        gRPCClient client = new gRPCClient("localhost", 8848);
        client.greet();
    }
}
