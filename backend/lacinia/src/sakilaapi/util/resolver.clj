(ns sakilaapi.util.resolver
  (:require
    [com.walmartlabs.lacinia.executor :as wal.la.executor]))


#_{:clj-kondo/ignore [:line-length]}


(defn update-nested-field
  "HACK : should make it less arity"
  [parent ctx child-field find-by-id db input-id-keys output-keys]
  (cond
    (wal.la.executor/selects-field? ctx child-field) (->> (get-in parent
                                                                  input-id-keys)
                                                          (find-by-id db)
                                                          (assoc-in parent
                                                                    output-keys))
    :else parent))
