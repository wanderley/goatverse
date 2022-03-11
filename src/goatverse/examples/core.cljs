(ns goatverse.examples.core
  (:require [goatverse.core.world :refer [make-world]]
            [goatverse.examples.counter-world :refer [counter-world]]
            [reagent.dom :refer [render]]))

(enable-console-print!)

(defn app [world]
  [(:on-render world)
   @(:state world)
   (:dispatch world)])

(when-let [container (. js/document (getElementById "app"))]
  (defonce world (make-world counter-world))
  (render [app world]
          container))

(defn on-js-reload []
  ;; optionally touch your state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)
  )
