(ns sakilaapi.entity.payment
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


(s/defschema Payment
             {:payment_id s/Int
              :customer_id s/Int
              :staff_id s/Int
              :rental_id s/Int
              :amount s/Num
              :payment_date LocalDateTime
              :last_update Timestamp})


(s/defn find-by-customer-id
  :- (s/maybe [Payment])
  [db :- sakila.schm/DBProfile
   customer-id :- s/Int]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :payment)
           (hsql.h/where [:= :customer_id customer-id]))
       (u.db/execute-all db)))
