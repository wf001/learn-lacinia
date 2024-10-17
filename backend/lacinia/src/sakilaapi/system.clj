(ns sakilaapi.system
  (:require
    [clojure.tools.logging :as clj.log]
    [com.stuartsierra.component :as st.component]
    [sakilaapi.component.db :as c.db]
    [sakilaapi.component.handler :as c.handler]
    [sakilaapi.component.resolver :as c.resolver]
    [sakilaapi.component.server :as c.server]
    [sakilaapi.config :as config]
    [schema.core :as s]
    [toyokumo.commons.experimental.graphql.lacinia :as tk.c.lacinia]
    [unilog.config :as unilog.cnf]))


(defn- new-system
  [{:as config :keys [:profile]}]
  (st.component/system-map
    :db (c.db/map->Database (:db config))
    :resolver (c.resolver/map->Resolver {})
    :lacinia (st.component/using
               (tk.c.lacinia/map->Lacinia (:graphql config))
               [:resolver])
    :handler (st.component/using
               (c.handler/map->Handler {:profile profile
                                        :allowed-origin (:allowed-origin config)})
               [:db :lacinia])
    :server (st.component/using
              (c.server/map->Jetty9Server (:server config))
              [:handler])))


(defn- init-logging!
  [config]
  (unilog.cnf/start-logging! (:logging config)))


(defn start
  [profile]
  (let [config (config/read-config profile)
        system (new-system config)
        _ (init-logging! config)
        _ (clj.log/info "system is ready to start")
        started-system (st.component/start system)]
    (clj.log/info "system is started")
    (s/set-fn-validation! true)
    started-system))


(defn stop
  [system]
  (st.component/stop system))
