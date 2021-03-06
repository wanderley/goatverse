(ns goatverse.core.test-universe)

(defmacro defcard-universe [name settings & steps]
  `(let [steps# [~@steps]
         snapshots# (steps->snapshots steps# ~settings)
         reality# (first snapshots#)]
     (devcards.core/defcard-rg ~name
       (fn [state# _#]
         [render-snapshot state# steps# snapshots# ~settings])
       (reagent.core/atom
        {:index 0
         :steps [~@steps]
         :reality reality#})
       {:inspect-data true
        :history true})))
