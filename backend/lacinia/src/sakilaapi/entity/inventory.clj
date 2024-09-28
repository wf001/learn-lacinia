(ns sakilaapi.entity.inventory
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.db :as u.db]
    [schema.core :as s])
  (:import
    (java.sql
      Timestamp)))


(s/defschema Inventory
             {:inventory_id s/Int
              :film_id s/Int
              :store_id s/Int
              :last_update Timestamp})


(s/defn find-by-ids
  :- (s/maybe [Inventory])
  [db :- sakila.schm/DBProfile
   inventory-id :- [s/Int]]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :inventory)
           (hsql.h/where [:in :inventory_id inventory-id]))
       (u.db/execute-all db)))
