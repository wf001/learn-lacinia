(ns sakilaapi.component.db
  (:require
    [com.stuartsierra.component :as st.component]))


(defrecord Database
  [config]

  st.component/Lifecycle

  (start
    [this]
    (assoc this :db config))


  (stop
    [this]
    (assoc this :db nil)))
