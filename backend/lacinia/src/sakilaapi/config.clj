(ns sakilaapi.config
  (:require
   [aero.core :as aero.core]
   [clojure.java.io :as clj.io]))

(defn read-config [profile]
  {:pre [(contains? #{:dev :prod :test} profile)]}
  (-> (clj.io/resource "config.edn")
      (aero.core/read-config {:profile profile})
      (assoc :profile profile)))
