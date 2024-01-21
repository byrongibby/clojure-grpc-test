(ns hello-world.client
  (:import [io.grpc.examples.helloworld GreeterGrpc HelloRequest]
           [io.grpc ManagedChannelBuilder]))

(def client (GreeterGrpc/newBlockingStub
              (-> (ManagedChannelBuilder/forAddress "localhost" (int 35000))
                  .usePlaintext
                  .build)))

(defn say-hello [name]
  (println 
   (.getMessage
     (.sayHello client (-> (HelloRequest/newBuilder)
                           (.setName name)
                           .build)))))
