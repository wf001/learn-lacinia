#!/usr/bin/env bb

(require
  '[cheshire.core :as chs.core]
  '[clj-http.lite.client :as clj-http.client])


(def failed-count (atom 0))
(def warning-count (atom 0))
(def passed-count (atom 0))
(def assertion-count (atom 0))


(defn endpoint-url-map
  [name]
  ({:ring-dev "http://localhost:11004"
    :ring-jar "http://localhost:11015"} name))


(defn- failed-color
  [text]
  (str "\033[0;31m" text "\033[0m "))


(defn- passed-color
  [text]
  (str "\033[0;32m" text "\033[0m "))


(defn- warning-color
  [text]
  (str "\033[0;33m" text "\033[0m "))


(defn- reset-counter
  []
  (reset! assertion-count 0)
  (reset! failed-count 0)
  (reset! passed-count 0)
  (reset! warning-count 0))


(defn- info
  [message]
  (-> " 💡 "
      (str message)
      println))


(defn- passed
  [message]
  (swap! passed-count inc)
  (-> " ✅ "
      (str message)
      println))


(defn- failed
  [message]
  (swap! failed-count inc)
  (-> " ❌ "
      (str message)
      println))


(defn- warning
  [message]
  (swap! warning-count inc)
  (-> " ❗ "
      (str message)
      println))


(defn- assert-status-code
  [response expected-status-code]
  (if (= (:status response) expected-status-code)
    (passed (str "Status code => " (:status response)))
    (failed (str "Status code => expected: " expected-status-code
                 ", but got: " (:status response)))))


(defn- assert-body
  [response-json target-key expected-value is-body-array]
  (let [actual-value (if is-body-array
                       (-> [] (into response-json) (-> (get-in target-key)))
                       (get-in response-json target-key))]
    (if (= actual-value expected-value)
      (passed (str "Body => '" target-key "' is " actual-value))
      ((failed (str "expected: '" target-key "' to be '" expected-value
                    "', but got: '" actual-value "'"))
       (info (str "Response body is \" " response-json "" \"))))))


(defn- assert-request
  ([description http-request expected]
   (assert-request description http-request expected false))
  ([description http-request expected is-body-array]
   (println (str "Testing : " description))
   (let [response (http-request)]
     (assert-status-code response (:status expected))
     (try
       (swap! assertion-count inc)
       (let [response-json (chs.core/parse-string (:body response) true)]
         (doseq [[key value] expected]
           (when (not= key :status)
             (assert-body response-json key value is-body-array))))
       (catch Exception e
         (warning "Response is not in JSON format")
         (info (str "Response body is \" " response "" \"))
         (info (str "Exception: " e))))
     (prn))))


(defn- rest-request-get
  [endpoint]
  (fn [] (clj-http.client/get endpoint {:as :json :throw-exceptions false})))


(defn- test-rest
  [url]
  (println)
  (println "==== REST " url " ====")
  (println)

  (assert-request "/api/health"
                  (->> "/api/health"
                       (str url)
                       rest-request-get)
                  {:status 200 [:message] "Application is runnig"})

  (assert-request "/api/not-found"
                  (->> "/api/not-found"
                       (str url)
                       rest-request-get)
                  {:status 404})
  (assert-request "/api/rest/customers"
                  (->> "/api/rest/customers"
                       (str url)
                       rest-request-get)
                  {:status 200 [0 :email] "MARY.SMITH@sakilacustomer.org"}
                  true))


(defn- main
  []
  (reset-counter)

  (test-rest (endpoint-url-map :ring-dev))
  (test-rest (endpoint-url-map :ring-jar))

  (println " ---------------------------------------------------------")
  (println "|" (failed-color "Total Errors:") @failed-count (warning-color ", Total Warnings:") @warning-count (passed-color ", Total Passed:") @passed-count)
  (println "| Total assertion: " @assertion-count)
  (println " ---------------------------------------------------------")
  (when (> @failed-count 0)
    (System/exit 1)))


(main)
