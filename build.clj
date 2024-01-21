(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'com.github.coderaanalytics/clojure-grpc-test)
(def version (format "0.0.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def uber-file (format "target/%s-%s.jar" (name lib) version))

;; delay to defer side effects (artifact downloads)
(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn compile [_]
  (b/javac {:src-dirs ["build/generated/source/proto/main/grpc"
                       "build/generated/source/proto/main/java"]
            :class-dir class-dir
            :basis @basis
            :javac-opts ["--release" "11"]}))

(defn jar [_]
  (compile nil)
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis @basis
                :src-dirs ["src/main/clj"]})
  (b/copy-dir {:src-dirs ["src/main/clj" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))

(defn uber [_]
  (clean nil)
  (compile nil)
  (b/copy-dir {:src-dirs ["src/main/clj" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis @basis
                  :src-dirs ["src/main/clj"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis @basis
           :main 'hello-world.core}))
