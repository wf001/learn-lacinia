(ns sakilaapi.util.db
  (:require
    [clojure.tools.logging :as tools.log]
    [honey.sql :as hsql]
    [next.jdbc :as n.jdbc]
    [next.jdbc.result-set :as n.jdbc.rs]
    [sakilaapi.schema :as sakila.schm]
    [schema.core :as s]))


(s/defn -execute
  [exec-fn
   db :- sakila.schm/DBProfile
   query :- {s/Keyword s/Any}]
  (let [q (hsql/format query)]
    (tools.log/info q)
    (exec-fn db q {:builder-fn n.jdbc.rs/as-unqualified-lower-maps})))


(s/defn execute-all
  [db :- sakila.schm/DBProfile
   query :- {s/Keyword s/Any}]
  (-execute (fn [db query opt] (n.jdbc/execute! db query opt)) db query))


(s/defn execute-one
  [db :- sakila.schm/DBProfile
   query :- {s/Keyword s/Any}]
  (-execute (fn [db query opt] (n.jdbc/execute-one! db query opt)) db query))
