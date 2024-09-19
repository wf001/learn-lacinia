(ns sakilaapi.component.handler
  (:require
   [com.stuartsierra.component :as st.component]
   [reitit.ring :as rt.ring]
   [ring.logger :as rg.logger]
   [ring.middleware.lint :as rg.mw.lint]
   [ring.middleware.reload :as rg.mw.reload]
   [ring.middleware.stacktrace :as rg.mw.stacktrace]
   [sakilaapi.handler.health]
   [sakilaapi.router :as router]))

(def ^:private dev-middlewares
  "開発時だけ有効化する"
  [[rg.mw.reload/wrap-reload {:dirs ["src"]
                              :reload-compile-errors? true}]
   rg.mw.lint/wrap-lint
   [rg.mw.stacktrace/wrap-stacktrace {:color? true}]])

(defn- build-handler
  [profile]
  (let [common-middlewares [rg.logger/wrap-with-logger]
        middlewares (if (= profile :prod)
                      common-middlewares
                      (apply conj dev-middlewares common-middlewares))]
    (rt.ring/ring-handler
     router/router
     nil
     {:middleware middlewares})))

(defrecord Handler [handler profile]
  st.component/Lifecycle
  (start [this]
    (assoc this :handler (build-handler profile)))
  (stop [this]
    (assoc this :handler nil)))
