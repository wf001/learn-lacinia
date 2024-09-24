(ns sakilaapi.middleware.db
  (:require
    [sakilaapi.schema :as sakila.schm]
    [schema.core :as s]))


(s/defn wrap-db-conn
  [handler
   db :- sakila.schm/DBProfile]
  (fn [context]
    (let [ctx (assoc context :db db)]
      (handler ctx))))
