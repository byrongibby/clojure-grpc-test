(ns hello-world.server
  (:import [io.grpc.examples.helloworld GreeterGrpc$GreeterImplBase HelloRequest HelloReply]
           ))

(defn make-service []
  (proxy [GreeterGrpc$GreeterImplBase] []
    (sayHello [^HelloRequest request response]
      (let [name (.getName request)
            builder (-> (HelloReply/newBuilder)
                        (.setMessage (str "Hello " name)))]
        (.onNext response (.build builder))
        (.onCompleted response)))))
