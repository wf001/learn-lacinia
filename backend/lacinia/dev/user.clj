(ns user
  (:require
    [sakilaapi.config :as conf]
    [sakilaapi.system :as system]))


(defonce system (atom nil))


(defn start
  []
  (reset! system (system/start :dev)))


(defn stop
  []
  (when @system
    (reset! system (system/stop @system))))


(defn go
  []
  (stop)
  (start))


(defn conf
  [kw]
  (-> (conf/read-config :dev)
      kw))
