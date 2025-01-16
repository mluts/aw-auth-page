(ns aw.app
  (:require
   [aw.router :as router]
   [aw.route-helpers :as rh]))

(defn app []
  (let [view (rh/route-view @router/current-match)]
    [:main {:class "container"}

     [view @router/current-match]]))
