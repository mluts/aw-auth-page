(ns aw.view.frontpage
  (:require
   [aw.view.common :as common]
   [aw.route-helpers :as rh]
   [aw.data :as data]))

(defn view []
  [:div
   [:h1 "AW Frontpage"]
   [:ul
    (map
     (fn [[id game _url]]
       [:li {:key id}
        [:a {:href (rh/resolve-href :aw/game {:game_id id})} game]])
     (sort-by first (data/find-games)))]
   [:a {:href "#" :on-click common/sign-out} "Sign-Out"]])
