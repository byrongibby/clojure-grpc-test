(ns hello-world.core
  (:import [prototest PersonProto$Person])
  (:gen-class))

(def person (.. (#(PersonProto$Person/newBuilder))
                (setName "Byron Botha")
                (setAge 35)
                build))

(defn -main [& _]
  (println (.toString person)))
