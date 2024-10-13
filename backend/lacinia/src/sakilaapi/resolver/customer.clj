(ns sakilaapi.resolver.customer
  (:require
    [sakilaapi.entity.customer :as e.customer]
    [sakilaapi.schema :as resovler.s]
    [schema.core :as s]))


(defn list-customers
  []
  (s/fn [{:keys [db]} :- resovler.s/Context
         _ :- s/Any
         _ :- s/Any]
    (e.customer/find-all db)))
