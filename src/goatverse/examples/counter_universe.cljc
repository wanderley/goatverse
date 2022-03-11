(ns goatverse.examples.counter-universe)

(defmulti universe-message (fn [type & _] type))

(defmethod universe-message :default [_ state & _]
  {:state state})

(defmethod universe-message :inc [_ state world-id]
  (let [state (inc state)]
    {:state state
     :world-messages [[world-id :state state]]}))

(defmethod universe-message :dec [_ state world-id]
  (let [state (dec state)]
    {:state state
     :world-messages [[world-id :state state]]}))

(defonce ^:export counter-universe
  {:initial-state 0
   :tick-rate (/ 1 10)
   :on-message universe-message})
