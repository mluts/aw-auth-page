(ns aw.router
  (:require
   [reagent.core :as r]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [aw.session :as session]
   [aw.route-helpers :as rh]
   [aw.view.frontpage :as frontpage]
   [aw.view.game :as game]
   [aw.view.signin :as signin]
   [aw.view.signup :as signup]))

(def default-authorized-route :aw/frontpage)
(def default-unauthorized-route :aw/signin)

(def routes
  [["/"
    {:name :aw/frontpage
     :perms #{:authorized-only}
     :view #'frontpage/view}]

   ["/game/:game_id"
    {:name :aw/game
     :perms #{:authorized-only}
     :view #'game/view}]

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

(defn match-by-name [-name]
  (rf/match-by-name router -name))

(defn init! []
  (rfe/start!
   router
   (fn [m]
     (cond
       (and (not (session/perms-ok? (rh/route-perms m)))
            (session/authorized?))
       (do
         (reset! current-match (match-by-name default-authorized-route))
         (rh/redirect! default-authorized-route))

       (not (session/perms-ok? (rh/route-perms m)))
       (do
         (reset! current-match (match-by-name default-unauthorized-route))
         (rh/redirect! default-unauthorized-route))

       :else
       (reset! current-match m))

     (js/console.log "Current route:" m))

   {:use-fragment true}))
