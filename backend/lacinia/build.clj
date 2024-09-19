(ns build
  (:require
   [clojure.tools.build.api :as clj.build.api]))

(def class-dir "target/classes")
(def basis (clj.build.api/create-basis {:project "deps.edn"}))
(def uber-file "target/sakilaapi.jar")

(defn uber [_]
  (clj.build.api/copy-dir {:src-dirs ["src" "resources"]
                           :target-dir class-dir})
  (clj.build.api/compile-clj {:basis basis
                              :src-dirs ["src"]
                              :class-dir class-dir})
  (clj.build.api/uber {:class-dir class-dir
                       :uber-file uber-file
                       :basis basis
                       :main 'sakilaapi.core}))
