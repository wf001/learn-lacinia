(ns sakilaapi.handler.health
  (:require
   [sakilaapi.handler :as h]
   [sakilaapi.router :as r]
   [ring.util.http-response :as res]))

(defmethod h/handler [::r/health :get]
  [_]
  (res/ok "Application is runnig"))
