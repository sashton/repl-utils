(ns sashton.repl-utils.test-runner
  (:require [kaocha.repl]
            [clojure.test]
            [clojure.java.io :as jio]
            [clojure.edn :as edn]))

(def filename ".sashton/repl-utils/test-runner.edn")

(defonce focus-tests
  (atom []))

(defn ^:private read-test-symbols
  []
  (try
    (edn/read-string (slurp filename))
    (catch Exception _
      #{})))

(defn ^:private write-test-symbols
  [symbols]
  (try
    (jio/make-parents filename)
    (spit filename (pr-str symbols))
    (catch Exception _)))

(defn add-focus-test
  [ns-or-symbol]
  (-> (read-test-symbols)
      (conj ns-or-symbol)
      (write-test-symbols)))

(defn clear-focus-tests
  []
  (write-test-symbols #{}))

(defn print-focus-tests
  []
  (println "-----------------------------")
  (println "Focus Tests")
  (doseq [test (read-test-symbols)]
    (println "\t" test))
  (println "-----------------------------"))

(defn run-focus-tests
  []
  (doseq [test @focus-tests]
    ;; (kaocha.repl/run test)
    (clojure.test/test-var (resolve test))))

(defn run-test
  [ns-or-symbol]
  (clear-focus-tests)
  (add-focus-test ns-or-symbol)
  (run-focus-tests))
