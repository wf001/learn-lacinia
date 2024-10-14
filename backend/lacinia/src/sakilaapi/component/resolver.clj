(ns sakilaapi.component.resolver
  (:require
    [com.stuartsierra.component :as st.component]
    [sakilaapi.resolver.customer :as r.customer]))


(defn- build-resolver
  []
  {:Query/paginatedCustomers (r.customer/list-customers)})


(defrecord Resolver
  [resolver]

  st.component/Lifecycle

  (start
    [this]
    (assoc this :resolvers (build-resolver)))


  (stop
    [this]
    (assoc this :resolvers nil)))
