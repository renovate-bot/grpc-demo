A quick start demo for gRPC with protobuf.


## Write a .proto file
[HelloWorld.proto](src/main/proto/HelloWorld.proto)

## Generate .java files
Run `mvn clean install` with [pom.xml](pom.xml), then you will find .java files at [target dir](target/generated-sources/protobuf).

## Write your code
Then write your [server and client code](src/main/java/cn/jcorn/grpc)
