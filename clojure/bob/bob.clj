(ns bob
  (:require [clojure.string :refer [upper-case trim]]))

(defn- classify [msg]
  (cond
   (and (re-find #"\p{Alpha}" msg) 
        (= (upper-case msg) msg)) 
   :loud

   (= \? (last msg)) 
   :question

   (= "" msg) 
   :nothing

   :else :normal)
  )

(def ^:private msg-map 
  {
   :nothing "Fine. Be that way!"
   :loud "Woah, chill out!"
   :question "Sure."
   :normal "Whatever."
   })

(defn response-for [msg]
  (msg-map (classify (trim msg))))
