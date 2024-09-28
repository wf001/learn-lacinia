(ns sakilaapi.entity.rental
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.db :as u.db]
    [schema.core :as s])
  (:import
    (java.sql
      Timestamp)
    (java.time
      LocalDateTime)))


(s/defschema Rental
             {:rental_id s/Int
              :rental_date LocalDateTime
              :inventory_id s/Int
              :customer_id s/Int
              :return_date LocalDateTime
              :staff_id s/Int
              :last_update Timestamp})


(s/defn find-by-id
  :- (s/maybe [Rental])
  [db :- sakila.schm/DBProfile
   rental-id :- [s/Int]]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :rental)
           (hsql.h/where [:in :rental-id rental-id]))
       (u.db/execute-all db)))
