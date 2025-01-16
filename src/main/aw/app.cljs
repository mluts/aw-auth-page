(ns aw.app
  (:require
   [aw.session :as session]
   [aw.router :as router]
   [aw.view.signin :as signin]))

(defn app []
  (let [{:keys [view perms]} (:data @router/current-match)]
    [:main {:class "container"}

     (if (session/perms-ok? (:perms @session/session) perms)
       [view]
       [signin/view])]))
