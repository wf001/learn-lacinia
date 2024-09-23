(ns sakilaapi.middleware.db
  (:require
    [schema.core :as s]))


(s/defn wrap-db-conn
  [handler
   db]
  (fn [context]
    (let [ctx (assoc context :db db)]
      (handler ctx))))
