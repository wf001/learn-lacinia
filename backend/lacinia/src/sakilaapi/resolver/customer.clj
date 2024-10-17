(ns sakilaapi.resolver.customer
  (:require
    [com.walmartlabs.lacinia.executor :as wal.la.executor]
    [sakilaapi.entity.address :as e.address]
    [sakilaapi.entity.city :as e.city]
    [sakilaapi.entity.country :as e.country]
    [sakilaapi.entity.customer :as e.customer]
    [sakilaapi.entity.payment :as e.payment]
    [sakilaapi.handler.customer :as h.customer]
    [sakilaapi.schema :as resovler.s]
    [sakilaapi.util.resolver :as u.resolver]
    [schema.core :as s]))


(defn list-customers
  []
  (s/fn [{:keys [db]} :- resovler.s/Context
         {:keys [offset limit]} :- {:offset s/Int :limit s/Int}
         _ :- s/Any]
    {:customers  (e.customer/find-all db offset limit)
     :totalCount (->> (e.customer/count-all db)
                      :total)}))


#_{:clj-kondo/ignore [:line-length]}


(defn get-customer
  []
  (s/fn [{:keys [db] :as ctx}  :- resovler.s/Context
         {:keys [customer_id]} :- {:customer_id s/Int}
         _ :- s/Any]
    ;; The variable that can be used in the prisma of the apollo project is fixed to 'customer_id', 
    ;; so no choice but to use kebab case.
    (let [update-rental-field (fn [parent ctx db]
                                (cond-> parent
                                  (wal.la.executor/selects-field? ctx :Payment/rental)
                                  (h.customer/update-rental-field db)))
          update-inventory-field (fn [parent ctx db]
                                   (cond-> parent
                                     (wal.la.executor/selects-field? ctx :Rental/inventory)
                                     (h.customer/update-inventory-field db)))]
      (-> (e.customer/find-by-id db customer_id)
          (some-> (u.resolver/update-nested-field ctx
                                                  :Customer/address
                                                  e.address/find-by-id
                                                  db
                                                  [:address_id]
                                                  [:address])
                  (u.resolver/update-nested-field ctx
                                                  :Address/city
                                                  e.city/find-by-id
                                                  db
                                                  [:address :city_id]
                                                  [:address :city])
                  (u.resolver/update-nested-field ctx
                                                  :City/country
                                                  e.country/find-by-id
                                                  db
                                                  [:address :city :country_id]
                                                  [:address :city :country]))
          (some-> (u.resolver/update-nested-field ctx
                                                  :Customer/payment
                                                  e.payment/find-by-customer-id
                                                  db
                                                  [:customer_id]
                                                  [:payment])
                  (update-rental-field ctx db)
                  (update-inventory-field ctx db))))))
