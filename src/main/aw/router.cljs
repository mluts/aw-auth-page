(ns aw.router
  (:require
   [reagent.core :as r]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [aw.view.signin :as signin]))

(def routes
  [["/"
    {:name :aw/frontpage
     :perms #{:authorized-only}
     :view nil}]
   ["/signin"
    {:name :aw/signin
     :perms #{:unauthorized-only}
     :view signin/view}]
   ["/signup"
    {:name :aw/signup
     :perms #{:unauthorized-only}
     }]])

(def router
  (rf/router routes))

(defonce current-match (r/atom nil))

(defn init! []
  (rfe/start!
   router
   (fn [m]
     (js/console.log "Current route:" m)
     (reset! current-match m))
   {:use-fragment true}))

(comment
  (deref current-match))
