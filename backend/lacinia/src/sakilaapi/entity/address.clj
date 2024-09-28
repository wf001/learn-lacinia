(ns sakilaapi.entity.address
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.db :as u.db]
    [schema.core :as s])
  (:import
    (java.sql
      Timestamp)))


(s/defschema Address
             {:address_id s/Int
              :address s/Str
              :address2 (s/maybe s/Str)
              :district s/Str
              :city_id s/Int
              :postal_code s/Str
              :phone s/Str
              :location s/Any ; TODO: 本来はGeometry型
              :last_update Timestamp})


(s/defn find-by-id
  :- (s/maybe Address)
  [db :- sakila.schm/DBProfile
   address-id :- s/Int]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :address)
           (hsql.h/where [:= :address-id address-id]))
       (u.db/execute-one db)))
