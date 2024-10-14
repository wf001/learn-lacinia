(ns sakilaapi.resolver.customer
  (:require
    [sakilaapi.entity.customer :as e.customer]
    [sakilaapi.schema :as resovler.s]
    [schema.core :as s]))


(defn list-customers
  []
  (s/fn [{:keys [db]} :- resovler.s/Context
         {:keys [offset limit]} :- {:offset s/Int :limit s/Int}
         _ :- s/Any]
    {:customers  (e.customer/find-all db offset limit)
     :totalCount (->> (e.customer/count-all db)
                      :total)}))
