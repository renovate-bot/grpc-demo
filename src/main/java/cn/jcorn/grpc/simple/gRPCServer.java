package cn.jcorn.grpc.simple;

import cn.jcorn.helloworld.GreeterGrpc;
import cn.jcorn.helloworld.HelloRequest;
import cn.jcorn.helloworld.HelloResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class gRPCServer {
  private Server server;
  private int port;

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
    public void helloWorld(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
      HelloResponse response = HelloResponse.newBuilder().setMessage("server reply:" + request.getName() + System.currentTimeMillis()).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }

    @Override
    public void sayHelloAgain(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
      responseObserver.onNext(HelloResponse.newBuilder().setMessage("server reply:" + request.getName() + System.currentTimeMillis()).build());
      responseObserver.onCompleted();
    }
  }
}
