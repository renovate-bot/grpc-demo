package cn.jcorn.grpc.simple;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    int port = 6666;
    gRPCServer server = new gRPCServer(port);
    gRPCClient client = new gRPCClient("127.0.0.1",port);
    new Thread(()->{
      try {
        server.start();
        server.blockUntilShutDown();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    new Thread(()->{
      client.greet();
      try {
        client.shutDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
