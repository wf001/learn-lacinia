(ns sakilaapi.entity.film
  (:require
    [honey.sql.helpers :as hsql.h]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.db :as u.db]
    [schema.core :as s])
  (:import
    (java.sql
      Timestamp)))


(s/defschema FilmCategory
             {:category_id s/Int
              :name s/Str})


(s/defschema FilmActor
             {:actor_id s/Int
              :first_name s/Str
              :last_name s/Str})


(s/defschema Film
             {:film_id s/Int
              :title s/Str
              :description s/Str
              :release_year s/Any
              :language_id s/Int
              :original_language_id (s/maybe s/Int)
              :rental_duration s/Int
              :rental_rate s/Num
              :length s/Int
              :replacement_cost s/Num
              :rating s/Str
              :special_features s/Str
              :last_update Timestamp})


(s/defn find-by-id
  :- (s/maybe Film)
  [db :- sakila.schm/DBProfile
   film-id :- s/Int]
  (->> (-> (hsql.h/select :*)
           (hsql.h/from :film)
           (hsql.h/where [:= :film-id film-id]))
       (u.db/execute-one db)))


(s/defn find-category-by-id
  :- (s/maybe [FilmCategory])
  [db :- sakila.schm/DBProfile
   film-id :- s/Int]
  (->> (-> (hsql.h/select
             [:category.category-id :category_id]
             :name)
           (hsql.h/from :film_category)
           (hsql.h/where [:= :film-id film-id])
           (hsql.h/inner-join :category
                              [:=
                               :category.category-id
                               :film_category.category-id]))
       (u.db/execute-all db)))


(s/defn find-actor-by-film-id
  :- (s/maybe [FilmActor])
  [db :- sakila.schm/DBProfile
   film-id :- s/Int]
  (->> (-> (hsql.h/select
             [:actor.first_name :first_name]
             [:actor.last_name :last_name]
             [:actor.actor_id :actor_id])
           (hsql.h/from :film_actor)
           (hsql.h/where [:= :film-id film-id])
           (hsql.h/inner-join :actor [:= :actor.actor-id :film_actor.actor-id]))
       (u.db/execute-all db)))
