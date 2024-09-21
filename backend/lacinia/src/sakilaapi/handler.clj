(ns sakilaapi.handler
  (:require
    [clojure.tools.logging :as clj.log]
    [sakilaapi.util.http :as u.http]))


(defmulti handler
  "引数はリクエストマップ
   reititで作られたHandler(reitit.ring/ring-handlerで作ったHandler)を通すと
   リクエストマップには :reitit.core/match というキーでマッチしたrouteのdataが含まれる。
   そこからマッチしたrouteの名前を取り出す。
   またRingの仕様により :request-method にHTTP Methodが含まれる。
   この2つを組み合わせて [route-name, request-method] という2つの情報でHandlerの実装とマッチさせる。"
  (fn [req]
    [(get-in req [:reitit.core/match :data :name])
     (get req :request-method)]))


(defmethod handler :default
  [_]
  "存在しないhandlerが呼び出された場合に呼び出される
   存在しないルーティングに対しては、ring-handlerの第2引数の内容が返される"
  (clj.log/error "The specified handler not implemented.")
  (u.http/internal-server-error))
