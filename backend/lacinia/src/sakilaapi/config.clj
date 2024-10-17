(ns sakilaapi.config
  (:require
    [aero.core :as aero.core]
    [clojure.java.io :as clj.io]))


(defn read-config
  [profile]
  {:pre [(contains? #{:dev :prod :test} profile)]}
  (-> (clj.io/resource "config.edn")
      (aero.core/read-config {:profile profile})
      (assoc :profile profile)
      ;; Regex literals are not allowed in EDN, so set here.
      (assoc :allowed-origin (if (= profile :prod)
                               #"https://api-learn-lacinia.mkdirp.com"
                               #"http://localhost:11001"))))
