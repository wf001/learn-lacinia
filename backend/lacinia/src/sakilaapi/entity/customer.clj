(ns sakilaapi.entity.customer
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


(s/defschema Customer
             {:customer_id s/Int
              :store_id s/Int
              :first_name s/Str
              :last_name s/Str
              :email s/Str
              :address_id s/Int
              :active s/Bool
              :create_date LocalDateTime
              :last_update Timestamp})


(s/defn find-all
  :- (s/maybe [Customer])
  [db :- sakila.schm/DBProfile]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :customer))
       (u.db/execute-all db)))
