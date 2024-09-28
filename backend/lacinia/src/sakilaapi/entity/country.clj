(ns sakilaapi.entity.country
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.db :as u.db]
    [schema.core :as s])
  (:import
    (java.sql
      Timestamp)))


(s/defschema Country
             {:country_id s/Int
              :country s/Str
              :last_update Timestamp})


(s/defn find-by-id
  :- (s/maybe Country)
  [db :- sakila.schm/DBProfile
   country-id :- s/Int]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :country)
           (hsql.h/where [:= :country-id country-id]))
       (u.db/execute-one db)))
