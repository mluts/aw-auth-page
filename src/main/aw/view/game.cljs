(ns aw.view.game
  (:require
   [aw.view.common :as common]
   [aw.route-helpers :as rh]
   [aw.data :as data]))

(defn view [route-match]
  (let [[game url] (data/find-game (parse-long (get-in route-match [:path-params :game_id])))]
    (fn []
      [:div
       [:h1 game]

       [:iframe {:src url :width 600 :height 600}]
       [:br]

       [:a {:href (rh/resolve-href :aw/frontpage)} "Back to Frontpage"]
       [:br]
       [:a {:href "#" :on-click common/sign-out} "Sign-Out"]])))
