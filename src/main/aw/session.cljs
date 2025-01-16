(ns aw.session
  (:require
   [aw.route-helpers :as rh]
   [clojure.set :as set]
   [reagent.core :as r]))

(def unauthorized-session {:perms #{:unauthorized-only}})
(def authorized-session {:perms #{:authorized-only}})

(def session (r/atom unauthorized-session))

(defn authorize! []
  (reset! session authorized-session))

(defn deauthorize! []
  (reset! session unauthorized-session)
  (rh/redirect! :aw/signin))

(defn perms-ok?
  ([object-perms] (perms-ok? (:perms @session) object-perms))
  ([user-perms object-perms]
   (set/superset? (or user-perms #{}) (or object-perms #{}))))

(comment
 (deref session)
 (perms-ok? #{:autho}))
