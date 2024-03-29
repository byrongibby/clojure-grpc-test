(ns hello-world.core
  (:require [integrant.core :as ig]
            [hello-world.client :refer [say-hello]]
            [hello-world.server :refer [make-service]])
  (:import [io.grpc ServerBuilder])
  (:gen-class))

(def system-config
  {:system/builder {:port 35000}
   :system/server {:builder (ig/ref :system/builder)}})

(defmethod ig/init-key :system/builder [_ {port :port}]
  (ServerBuilder/forPort port))

(defmethod ig/init-key :system/server [_ {builder :builder}]
  (let [service (make-service)]
    (.addService builder service)
    (let [server (.build builder)]
      (.start server)
      server)))

(defmethod ig/halt-key! :system/server [_ server]
  (.shutdown server))

(defn -main [& args]
  (let [system (ig/init system-config)] 
    (say-hello (first args))
    (ig/halt! system)))
