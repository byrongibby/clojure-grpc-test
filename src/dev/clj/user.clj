(ns user
  (:require [integrant.core :as ig]
            [integrant.repl :as ig-repl]
            [hello-world.server :refer [make-service]])
  (:import [io.grpc ServerBuilder]))

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

(ig-repl/set-prep! (constantly system-config))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
