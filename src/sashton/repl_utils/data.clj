(ns sashton.repl-utils.data)


(defn deep-sorted-map
  [m]
  #_(clojure.walk/prewalk
   (fn [form]
     (cond

       (map? form)
       (do
         (try (-> (into (sorted-map) form)
                  (dissoc :timezone-id))
              (catch Exception _
                (into (sorted-map) form))))

       (instance? ZonedDateTime form)
       (do
         (order-dates/format-order-date form))

       (sequential? form)
       (vec form)

       :else
       form))
   m))
