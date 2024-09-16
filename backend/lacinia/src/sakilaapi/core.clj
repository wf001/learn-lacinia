(ns sakilaapi.core
  (:gen-class)
  (:require
   [sakilaapi.system :as system]))

(defn -main
  [& _args]
  (system/start :prod))
