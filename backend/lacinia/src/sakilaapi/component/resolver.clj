(ns sakilaapi.component.resolver
  (:require
    [com.stuartsierra.component :as st.component]
    [sakilaapi.resolver.customer :as r.customer]
    [sakilaapi.resolver.film :as r.film]))


(defn- build-resolver
  []
  {:Query/paginatedCustomers (r.customer/list-customers)
   :Query/customer (r.customer/get-customer)
   :Query/film (r.film/get-film)})


(defrecord Resolver
  [resolver]

  st.component/Lifecycle

  (start
    [this]
    (assoc this :resolvers (build-resolver)))


  (stop
    [this]
    (assoc this :resolvers nil)))
