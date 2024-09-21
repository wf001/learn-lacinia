(ns sakilaapi.util.http
  (:require
    [ring.util.http-response :as rg.u.http-res]))


(defn internal-server-error
  ([] (internal-server-error {:message "Internal server error"}))
  ([body] (rg.u.http-res/internal-server-error body)))
