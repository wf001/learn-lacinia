(ns sakilaapi.component.server
  (:require
   [com.stuartsierra.component :as st.component]
   [ring.adapter.jetty9 :as rg.jetty]))

(defrecord Jetty9Server [handler opts server]
  ;; handlerは :handler をキーにもつマップ(= コンポーネント)であることを期待している
  st.component/Lifecycle
  (start [this]
    (if server
      this
      (assoc this :server (rg.jetty/run-jetty (:handler handler) opts))))
  (stop [this]
    (when server
      (rg.jetty/stop-server server))
    (assoc this :server nil)))
