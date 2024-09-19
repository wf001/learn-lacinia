(ns sakilaapi.handler.api.greeting
  (:require
   [ring.util.http-response :as rg.u.http-res]
   [sakilaapi.handler :as handler]
   [sakilaapi.router :as router]))

(defmethod handler/handler [::router/hello :get]
  [_]
  (rg.u.http-res/ok {:greeting "Hello sakilaapi!!"}))

(defmethod handler/handler [::router/goodbye :get]
  [_]
  (rg.u.http-res/ok {:greeting "Goodbye!"}))
