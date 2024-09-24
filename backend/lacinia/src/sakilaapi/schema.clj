(ns sakilaapi.schema
  (:require
    [schema.core :as s]))


(s/defschema DBProfile
             {:db (s/maybe s/Any)
              :config (s/maybe s/Any)
              :dbtype s/Str
              :dbname s/Str
              :host s/Str
              :port s/Int
              :user s/Str
              :password s/Str})
