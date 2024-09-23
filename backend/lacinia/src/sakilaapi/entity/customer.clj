(ns sakilaapi.entity.customer
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.util.db :as u.db]))


(defn find-all
  [db]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :customer))
       (u.db/execute-all db)))
