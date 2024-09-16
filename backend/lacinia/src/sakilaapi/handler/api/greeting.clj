(ns sakilaapi.handler.api.greeting
  (:require
   [sakilaapi.handler :as h]
   [sakilaapi.router :as r]
   [ring.util.http-response :as res]))

(defmethod h/handler [::r/hello :get]
  [_]
  (res/ok {:greeting "Hello sakilaapi!!"}))

(defmethod h/handler [::r/goodbye :get]
  [_]
  (res/ok {:greeting "Goodbye!"}))
