package cn.jcorn.grpc;

import cn.jcorn.helloworld.GreeterGrpc;
import cn.jcorn.helloworld.HelloRequest;
import cn.jcorn.helloworld.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class gRpcClient {
  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  public gRpcClient(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public gRpcClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
  }

  public void greet() {
    HelloRequest request = HelloRequest.newBuilder().setName("Hello!").build();
    System.out.println("client send " + request.getName() + System.currentTimeMillis());
    HelloResponse response;
    response = blockingStub.helloWorld(request);
    System.out.println(response.getMessage());
    request = HelloRequest.newBuilder().setName("Hello!").build();
    System.out.println("client send " + request.getName() + System.currentTimeMillis());
    response = blockingStub.sayHelloAgain(request);
    System.out.println(response.getMessage());
  }

  public void shutDown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
}
