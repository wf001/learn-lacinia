(ns sakilaapi.handler.customer
  (:require
    [ring.util.http-response :as rg.u.http-res]
    [sakilaapi.entity.address :as e.address]
    [sakilaapi.entity.city :as e.city]
    [sakilaapi.entity.country :as e.country]
    [sakilaapi.entity.customer :as e.customer]
    [sakilaapi.entity.inventory :as e.inventory]
    [sakilaapi.entity.payment :as e.payment]
    [sakilaapi.entity.rental :as e.rental]
    [sakilaapi.handler :as handler]
    [sakilaapi.router :as r]
    [sakilaapi.schema :as sakila.schm]
    [sakilaapi.util.http :as u.http]
    [schema.core :as s]))


#_{:clj-kondo/ignore [:line-length]}


;; TODO: apply ignore to only comment 


(s/defn update-rental-field
  "
   {:last_name 'John' :payment [{:amount 2.00M :rental_id 1} {...}]}
   => {:last_name 'John' :payment [{:amount 2.00M :rental_id 1 :rental {:rental_id 1 :inventory_id 3}} {...}]}
   "
  [parent
   db :- sakila.schm/DBProfile]
  (let [rental-ids (->> parent
                        :payment
                        (mapv #(get-in % [:rental_id])))
        rental-map (->> rental-ids
                        (e.rental/find-by-id db)
                        (group-by :rental_id))]
    (-> parent
        (update-in [:payment]
                   (fn [payments]
                     (mapv (fn [payment]
                             (let [rental-id (get-in payment [:rental_id])
                                   rental (->> rental-id
                                               rental-map
                                               first)]
                               (assoc-in payment [:rental] rental)))
                           payments))))))


#_{:clj-kondo/ignore [:line-length]}


;; TODO: apply ignore to only comment 


(s/defn update-inventory-field
  "
  {:last_name 'John' :payment [{:amount 2.00M :rental_id 1 :rental {:rental_id 1 :inventory_id 3}} {...}]}
  => {:last_name 'John' :payment [{:amount 2.00M :rental_id 1 :rental {:rental_id 1 :inventory_id 3 :inventory {:film_id 1}}} {...}]}
  "
  [parent
   db :- sakila.schm/DBProfile]
  (let [inventory-ids (->> parent
                           :payment
                           (mapv #(get-in % [:rental :inventory_id])))
        inventory-map (->> inventory-ids
                           (e.inventory/find-by-ids db)
                           (group-by :inventory_id))]
    (-> parent
        (update-in [:payment]
                   (fn [payments]
                     (mapv (fn [payment]
                             (let [inventory-id (get-in payment
                                                        [:rental :inventory_id])
                                   inventory (->> inventory-id
                                                  inventory-map
                                                  first)]
                               (assoc-in payment
                                         [:rental :inventory]
                                         inventory)))
                           payments))))))


(defmethod handler/handler [::r/list-customers :get]
  [{:keys [db]}]
  (->> (e.customer/find-all db)
       rg.u.http-res/ok))


(defmethod handler/handler [::r/get-address-by-customer :get]
  [{:keys [db path-params]}]
  (if-let [customer (some->> (:id path-params)
                             parse-long
                             (e.customer/find-by-id db))]
    (let [address (e.address/find-by-id db (:address_id customer))
          city (e.city/find-by-id db (:city_id address))
          country (e.country/find-by-id db (:country_id city))]
      (->> country
           (assoc city :country)
           (assoc address :city)
           (assoc customer :address)
           rg.u.http-res/ok))
    (u.http/not-found)))


(defmethod handler/handler [::r/get-rental-info-by-customer :get]
  [{:keys [db path-params]}]
  (if-let [customer (some->> (:id path-params)
                             parse-long
                             (e.customer/find-by-id db))]
    (let [payment (e.payment/find-by-customer-id db (:customer_id customer))]
      (-> customer
          (assoc :payment payment)
          (update-rental-field db)
          (update-inventory-field db)
          rg.u.http-res/ok))
    (u.http/not-found)))
