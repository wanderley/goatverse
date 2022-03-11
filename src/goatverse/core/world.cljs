(ns goatverse.core.world
  (:require [cljs.core.async :as async :include-macros true]
            [chord.client :refer [ws-ch]]
            [reagent.core :refer [atom]]))

(defn- receive-messages! [ws-channel incoming-messages]
  (async/go-loop []
    (let [{:keys [message]} (async/<! ws-channel)]
      (async/>! incoming-messages message)
      (recur))))

(defn- send-messages! [ws-channel outgoing-messages]
  (async/go-loop []
    (when-let [message (async/<! outgoing-messages)]
      (async/>! ws-channel message)
      (recur))))

(defn- connect!
  "Connects with the universe and starts the input and output channels."
  [ws-url outgoing-messages incoming-messages]
  (async/go
    (let [{:keys [ws-channel error]} (async/<! (ws-ch ws-url))]
      (if error
        (println "Connection failed with" (str error))
        (do
          (println "Connected!")
          (send-messages! ws-channel outgoing-messages)
          (receive-messages! ws-channel incoming-messages))))))


;; TODO Rename on-render to render
;; TODO Rename on-message to message-handler
(defn make-world [{:keys [initial-state
                          on-render
                          on-message]}]
  (let [state (atom initial-state)
        incoming-messages (async/chan (async/sliding-buffer 10))
        outgoing-messages (async/chan (async/sliding-buffer 10))
        universe-dispatch! (fn [& message]
                             (async/put! outgoing-messages message))
        dispatch (fn [type & params]
                   (let [next (apply on-message (into [type @state] params))]
                     (when (:state next)
                       (swap! state #(identity (:state next))))
                     (when (:universe-dispatch next)
                       (apply universe-dispatch! (:universe-dispatch next)))))
        connection (connect! (str
                              (case (.. js/document -location -protocol)
                                "https:" "wss:"
                                "ws:")
                              "//" (.. js/document -location -host) "/ws")
                             outgoing-messages
                             incoming-messages)]

    (async/go-loop []
      (let [message (async/<! incoming-messages)]
        (apply dispatch message))
      (recur))
    {:state state
     :dispatch dispatch
     :on-render on-render}))
