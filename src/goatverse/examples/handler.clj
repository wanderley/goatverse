 (ns goatverse.examples.handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [goatverse.core.universe :refer [make-universe]]
            [goatverse.examples.counter-universe :refer [counter-universe]]
            [ring.util.response :as resp]))

(defonce universe (make-universe counter-universe))
(defonce big-bang! (:big-bang! universe))

(comment
  ;; Start game loop on universe
  (big-bang!))

(defroutes app
  (GET "/restart" [] (fn [_]
                       ((:big-chrunch! universe))
                       {:status 200
                        :headers {"Content-Type" "text/plain"}
                        :body    "Big Chrunch just happened!"}))
  (GET "/ws" [] (:ws-handler universe))
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (route/resources "/")
  (route/not-found "<h1>Universe not found!  Have you started the simulation?</h1>"))
