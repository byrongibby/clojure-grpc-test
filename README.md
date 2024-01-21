# clojure-grpc-test

This is a demonstration of how to make GRPC calls in Clojure. For now
this covers only the most basic use case.

This uses integrant for starting the server.

# Usage
 
1. Run `gradle build` to generate the java classes from the proto file.
2. The server is started from the `(go)` in `dev/clj/user.clj`
3. Run the client in `client.clj`.

Or

1. Run `gradle build` to generate the java classes from the proto file.
2. Run `clj -T:build uber` to generate the uberjar.
3. Run `java -cp target/clojure-grpc-test-0.0.3.jar clojure.main -m hello-world.core yourname`
