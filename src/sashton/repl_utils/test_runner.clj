(ns sashton.repl-utils.test-runner
  (:require [kaocha.repl]))

(defonce focus-tests
  (atom []))

(defn add-focus-test
  [ns-or-symbol]
  (swap! focus-tests conj ns-or-symbol))

(defn clear-focus-tests
  []
  (reset! focus-tests []))

(defn print-focus-tests
  []
  (println "-----------------------------")
  (println "FOCUS >>")
  (doseq [test @focus-tests]
    (println "\t" test))
  (println "-----------------------------"))

(defn run-focus-tests
  []
  (doseq [test @focus-tests]
    (kaocha.repl/run test)))

(defn run-test
  [ns-or-symbol]
  (reset! focus-tests [])
  (add-focus-test ns-or-symbol)
  (run-focus-tests))