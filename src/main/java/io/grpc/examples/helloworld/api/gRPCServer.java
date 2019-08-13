package io.grpc.examples.helloworld.api;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class gRPCServer {
    private Server server;
    private int port;

    public gRPCServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();
        List<ServerServiceDefinition> services = server.getServices();
        for (ServerServiceDefinition service : services) {
            System.out.println(service.getServiceDescriptor());
            System.out.println(service.getMethods());
        }
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
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            HelloReply response = HelloReply.newBuilder().setMessage("server reply:" + request.getName() + System.currentTimeMillis()).build();
            responseObserver.onNext(response);
            System.out.println(response);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        gRPCServer server = new gRPCServer(50051);
        server.start();
        new CountDownLatch(1).await();
    }
}
