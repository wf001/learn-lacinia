(ns sakilaapi.handler.health
  (:require
   [ring.util.http-response :as rg.u.http-res]
   [sakilaapi.handler :as handler]
   [sakilaapi.router :as router]))

(defmethod handler/handler [::router/health :get]
  [_]
  (rg.u.http-res/ok "Application is runnig"))
