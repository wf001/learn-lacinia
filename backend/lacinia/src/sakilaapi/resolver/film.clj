(ns sakilaapi.resolver.film
  (:require
    [sakilaapi.entity.film :as e.film]
    [sakilaapi.handler.film :as h.film]
    [sakilaapi.schema :as resovler.s]
    [sakilaapi.util.resolver :as u.resolver]
    [schema.core :as s]))


#_{:clj-kondo/ignore [:line-length]}


(defn get-film
  []
  (s/fn [{:keys [db] :as ctx} :- resovler.s/Context
         {:keys [film_id]} :- {:film_id s/Int}
         _ :- s/Any]
    ;; The variable that can be used in the prisma of the apollo project is fixed to 'customer_id', 
    ;; so no choice but to use kebab case.
    (some-> (e.film/find-by-id db film_id)
            (u.resolver/update-nested-field ctx
                                            :Film/film_category
                                            h.film/find-category->map
                                            db
                                            [:film_id]
                                            [:film_category])
            (u.resolver/update-nested-field ctx
                                            :Film/film_actor
                                            h.film/find-actor->map
                                            db
                                            [:film_id]
                                            [:film_actor]))))
