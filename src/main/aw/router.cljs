(ns aw.router
  (:require
   [reagent.core :as r]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [aw.session :as session]
   [aw.route-helpers :as rh]
   [aw.view.frontpage :as frontpage]
   [aw.view.signin :as signin]
   [aw.view.signup :as signup]))

(def routes
  [["/"
    {:name :aw/frontpage
     :perms #{:authorized-only}
     :view #'frontpage/view}]

   ["/signin"
    {:name :aw/signin
     :perms #{:unauthorized-only}
     :view #'signin/view}]

   ["/signup"
    {:name :aw/signup
     :perms #{:unauthorized-only}
     :view #'signup/view}]])

(def router
  (rf/router routes))

(defonce current-match (r/atom nil))

(defn init! []
  (rfe/start!
   router
   (fn [m]
     (when-not (session/perms-ok? (get-in m [:data :perms]))
       (rh/redirect! :aw/signin))

     (reset! current-match m)

     (js/console.log "Current route:" m))

   {:use-fragment true}))

(defn match-by-name [-name]
  (rf/match-by-name router -name))
