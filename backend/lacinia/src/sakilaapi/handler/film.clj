(ns sakilaapi.handler.film
  (:require
    [ring.util.http-response :as rg.u.http-res]
    [sakilaapi.entity.film :as e.film]
    [sakilaapi.handler :as handler]
    [sakilaapi.router :as r]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.http :as u.http]
    [schema.core :as s]))


(s/defn find-category->map
  [db :- sakila.schm/DBProfile
   film-id :- s/Int]
  (->> (e.film/find-category-by-id db film-id)
       (mapv (partial assoc {} :category))))


(s/defn find-actor->map
  [db :- sakila.schm/DBProfile
   film-id :- s/Int]
  (->> (e.film/find-actor-by-film-id db film-id)
       (mapv (partial assoc {} :actor))))


(defmethod handler/handler [::r/get-film :get]
  [{:keys [db path-params]}]
  (if-let [film-id (->> (:id path-params)
                        parse-long)]
    (if-let [film (e.film/find-by-id db film-id)]
      (let [category (find-category->map db film-id)
            actor (find-actor->map db film-id)]
        (-> (assoc film :film_category category)
            (assoc :film_actor actor)
            (rg.u.http-res/ok)))
      (u.http/not-found))
    (u.http/not-found)))
