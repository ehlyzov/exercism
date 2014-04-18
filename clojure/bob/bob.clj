(ns bob
  (:require [clojure.string :refer [upper-case trim]]))

(def ^:private properties-map
  (hash-map
   :question   #(= \? (last %))
   :letters    #(re-find #"\p{Alpha}" %)
   :stress     #(= (upper-case %) %)
   :empty      #(= "" %)))
   

(def ^:private reaction-map 
  (hash-map
   :yelling "Woah, chill out!"
   :asking "Sure."
   :nothing "Fine. Be that way!"
   :something "Whatever."))

(defn- guess-intention [properties]
  (condp (fn [a b] (every? b a)) properties
   #{:stress :letters} :yelling
   #{:question} :asking
   #{:empty} :nothing
   :something))

(defn- collect-properties [msg]
  (set (keys (filter #((last %) msg) properties-map))))

(defn response-for [msg]
  (reaction-map (guess-intention (collect-properties (trim msg)))))
