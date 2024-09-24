(ns sakilaapi.middleware.exception
  (:require
    [clojure.tools.logging :as tools.log]
    [sakilaapi.util.http :as u.http]))


(defn wrap-unexpected-exception
  [handler]
  (fn [ctx]
    (try
      (handler ctx)
      (catch Throwable e
        (tools.log/error (.getMessage e))
        (tools.log/error (apply str (interpose "\n" (.getStackTrace e))))
        (u.http/internal-server-error)))))
