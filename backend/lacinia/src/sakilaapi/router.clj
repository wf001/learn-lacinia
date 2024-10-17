(ns sakilaapi.router
  (:require
    [camel-snake-kebab.core :as csk.core]
    [clojure.core.memoize :as clj.memo]
    [clojure.tools.logging :as tools.log]
    [com.walmartlabs.lacinia :as l]
    [muuntaja.core :as muu.core]
    [muuntaja.middleware :as muu.mw]
    [reitit.ring :as rt.ring]
    [ring.middleware.cors :as rg.mw.cors]
    [ring.middleware.defaults :as rg.mw.defautls]
    [ring.util.http-response :as rg.u.http-response]
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
  [db lacinia allowed-origin]
  (rt.ring/router
    [["/health" {:name ::health
                 :handler handler/handler}]
     ["/api"
      ["/" {:middleware [[rg.mw.defautls/wrap-defaults ring-custom-config]
                         [muu.mw/wrap-format muuntaja-custom-config]
                         mw.exception/wrap-unexpected-exception
                         muu.mw/wrap-params
                         [rg.mw.cors/wrap-cors
                          :access-control-allow-origin allowed-origin
                          :access-control-allow-methods [:get :post]]
                         [mw.db/wrap-db-conn db]]}
       ["health" {:name ::health-json
                  :handler handler/handler}]
       ["rest/"
        ["customers" {:name ::list-customers
                      :handler handler/handler}]
        ["customers/count" {:name ::count-customers
                            :handler handler/handler}]
        ["customer/:id/address" {:name ::get-address-by-customer
                                 :handler handler/handler}]
        ["customer/:id/rental" {:name ::get-rental-info-by-customer
                                :handler handler/handler}]
        ["film/:id" {:name ::get-film
                     :handler handler/handler}]]
       ["graphql" {:post {:handler (fn [{:as req :keys [:params]}]
                                     (tools.log/info (:query params))
                                     (-> (l/execute (:compiled-schema lacinia)
                                                    (:query params)
                                                    (:variables params)
                                                    req)
                                         rg.u.http-response/ok))}}]]]]))
