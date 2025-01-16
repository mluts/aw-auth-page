(ns aw.data
  (:require [datascript.core :as d]
            ; [datascript.db :as db]
            [clojure.string :as str]
            [aw.crypto :as crypto]))

;; https://docs.datomic.com/schema/schema-reference.html
(def schema
  {:username {:db/cardinality :db.cardinality/one
              :db/unique :db.unique/identity
              :db/doc "Unique Username"}
   :password-hash {:db/cardinality :db.cardinality/one}})

(defonce db-conn (d/create-conn schema))

(defn user-exists?
  ([username]
   (boolean
    (seq (d/q [:find '?e :where ['?e :username username]]
              @db-conn))))
  ([username password]
   (boolean
    (seq (d/q [:find '?e
               :where
               ['?e :username username]
               ['?e :password-hash (crypto/calc-hash password)]]
              @db-conn)))))

(defn register-user [username password]
  (d/transact! db-conn [[:db/add -1 :username username]
                        [:db/add -1 :password-hash (crypto/calc-hash password)]]))

(def min-password-legnth 6)
(def min-username-length 2)

(defn check-password-length [password]
  (and (not (str/blank? password))
       (>= (count password) min-password-legnth)))

(defn check-username-length [username]
  (and (not (str/blank? username))
       (>= (count username) min-username-length)))
