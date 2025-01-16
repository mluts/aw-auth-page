(ns aw.session
  (:require
   [aw.route-helpers :as rh]
   [clojure.set :as set]
   [reagent.core :as r]))

(def unauthorized-perms #{:unauthorized-only})
(def authorized-perms #{:authorized-only})

(def session (r/atom {:perms unauthorized-perms}))

(defn authorize! []
  (swap! session update :perms set/union authorized-perms))

(defn deauthorize! []
  (swap! session assoc :perms unauthorized-perms)
  (rh/redirect! :aw/signin))

(defn perms-ok?
  ([object-perms] (perms-ok? (:perms @session) object-perms))
  ([user-perms object-perms]
   (set/superset? (or user-perms #{}) (or object-perms #{}))))

(defn authorized? []
  (set/subset? authorized-perms (:perms @session)))
