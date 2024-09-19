(ns sakilaapi.handler
  (:require
   [ring.util.http-response :as rg.u.http-res]))

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
  (rg.u.http-res/not-found "not found"))
