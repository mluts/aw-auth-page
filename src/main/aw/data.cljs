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
   :password-hash {:db/cardinality :db.cardinality/one}

   :game-name {:db/cardinality :db.cardinality/one
               :db/unique :db.unique/identity}})

(def db-conn
  (-> (d/empty-db schema)
      (d/db-with
       [{:db/id 1 :game-name "Mai Chan Sweet Buns" :url "/pico8/mai_chan_sweet_buns"}
        {:db/id 2 :game-name "Breakout Hero" :url "/pico8/breakout_hero"}
        {:db/id 3 :game-name "Porklike" :url "/pico8/porklike"}
        {:db/id 4 :game-name "Willo" :url "/pico8/willo"}])
      (d/conn-from-db)))

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

(defn find-games []
  (d/q '[:find ?e ?game ?url
         :where
         [?e :game-name ?game]
         [?e :url ?url]]
       @db-conn))

(defn find-game [id]
  (d/q [:find '[?game ?url]
        :where
        [id :game-name '?game]
        [id :url '?url]]
       @db-conn))

(def min-password-legnth 6)
(def min-username-length 2)

(defn check-password-length [password]
  (and (not (str/blank? password))
       (>= (count password) min-password-legnth)))

(defn check-username-length [username]
  (and (not (str/blank? username))
       (>= (count username) min-username-length)))
