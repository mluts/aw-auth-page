(ns aw.session
  (:require
   [clojure.set :as set]
   [reagent.core :as r]))

(def unauthorized-session {:perms #{:unauthorized-only}})
(def authorized-session {:perms #{:authorized-only}})

(def session (r/atom unauthorized-session))

(defn perms-ok? [user-perms object-perms]
  (set/superset? (or user-perms #{}) (or object-perms #{})))
