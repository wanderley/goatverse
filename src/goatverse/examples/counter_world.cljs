(ns goatverse.examples.counter-world
  (:require [goatverse.core.test-universe :refer [defcard-universe]]
            [goatverse.examples.counter-universe :refer [counter-universe]]))

(defmulti  world-message (fn [type & _] type))

(defmethod world-message 'world-id [_ state _]
  {:state state})

(defmethod world-message :state [_ state new-state]
  {:state new-state})

(defmethod world-message :inc [_ state]
  {:universe-dispatch [:inc]})

(defmethod world-message :dec [_ state]
  {:universe-dispatch [:dec]})

(defn counter [state dispatch]
  [:div
   [:h1 "Counter"]
   [:button {:on-click #(dispatch :inc)} "+"]
   [:span {:style {:display "inline-block"
                   :min-width "2em"
                   :text-align "center"}}
    state]
   [:button {:on-click #(dispatch :dec)} "-"]])

(defonce counter-world
  {:initial-state 0
   :on-render counter
   :on-message world-message})

(defcard-universe two-clients-on-same-room
  {:universe counter-universe
   :world    counter-world}
  [:connect :Bob]
  [:connect :Alice])
