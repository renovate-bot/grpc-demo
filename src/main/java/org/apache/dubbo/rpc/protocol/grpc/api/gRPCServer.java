package org.apache.dubbo.rpc.protocol.grpc.api;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.dubbo.rpc.protocol.grpc.GreeterGrpc;
import org.apache.dubbo.rpc.protocol.grpc.HelloService;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class gRPCServer {
    private Server server;
    private int port = 8848;

    public gRPCServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();
    }

    public void stop() {
        if (server != null)
            server.shutdown();
    }

    public void blockUntilShutDown() throws InterruptedException {
        if (server != null)
            server.awaitTermination();
    }

    private static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void helloWorld(HelloService.HelloRequest request, StreamObserver<HelloService.HelloResponse> responseObserver) {
            HelloService.HelloResponse response = HelloService.HelloResponse.newBuilder().setResponseData("server reply:" + request.getRequestData() + System.currentTimeMillis()).build();
            responseObserver.onNext(response);
            System.out.println(response);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        gRPCServer server = new gRPCServer(8848);
        server.start();
        new CountDownLatch(1).await();
    }
}
