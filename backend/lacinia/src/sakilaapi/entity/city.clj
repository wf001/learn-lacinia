(ns sakilaapi.entity.city
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.db :as u.db]
    [schema.core :as s])
  (:import
    (java.sql
      Timestamp)))


(s/defschema City
             {:city_id s/Int
              :city s/Str
              :country_id s/Int
              :last_update Timestamp})


(s/defn find-by-id
  :- (s/maybe City)
  [db :- sakila.schm/DBProfile
   city-id :- s/Int]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :city)
           (hsql.h/where [:= :city-id city-id]))
       (u.db/execute-one db)))
