(ns sakilaapi.handler.customer
  (:require
    [ring.util.http-response :as rg.u.http-res]
    [sakilaapi.entity.customer :as e.customer]
    [sakilaapi.handler :as handler]
    [sakilaapi.router :as r]))


(defmethod handler/handler [::r/list-customers :get]
  [{:keys [db]}]
  (->> (e.customer/find-all db)
       rg.u.http-res/ok))
