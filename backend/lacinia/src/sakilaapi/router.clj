(ns sakilaapi.router
  (:require
    [camel-snake-kebab.core :as csk.core]
    [clojure.core.memoize :as clj.memo]
    [muuntaja.core :as muu.core]
    [muuntaja.middleware :as muu.mw]
    [reitit.ring :as rt.ring]
    [ring.middleware.defaults :as rg.mw.defautls]
    [sakilaapi.handler :as handler]
    [sakilaapi.middleware.db :as mw.db]
    [sakilaapi.middleware.exception :as mw.exception]))


(def ^:private ring-custom-config
  (-> rg.mw.defautls/api-defaults
      ;; ロードバランサーの後ろで動いていると想定して、
      ;; X-Forwarded-For と X-Forwarded-Proto に対応させる
      (assoc :proxy true)))


(def ^:private muuntaja-custom-config
  "https://cljdoc.org/d/metosin/muuntaja/0.6.8/doc/configuration"
  (-> muu.core/default-options
      ;; JSON以外のacceptでリクエストされたときに返らないように制限する
      (update :formats #(select-keys % ["application/json"]))
      muu.core/create))


(defn router
  [db]
  (rt.ring/router
    [["/health" {:name ::health
                 :handler handler/handler}]
     ["/api"
      ["/" {:middleware [[rg.mw.defautls/wrap-defaults ring-custom-config]
                         [muu.mw/wrap-format muuntaja-custom-config]
                         mw.exception/wrap-unexpected-exception
                         muu.mw/wrap-params
                         [mw.db/wrap-db-conn db]]}
       ["health" {:name ::health-json
                  :handler handler/handler}]
       ["rest/"
        ["customers" {:name ::list-customers
                      :handler handler/handler}]]]]]))
