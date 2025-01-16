(ns aw.view.frontpage
  (:require
   [aw.view.common :as common]
   [aw.route-helpers :as rh]
   [reagent.core :as r]))

(defn view []
  (let [state (r/atom {})]
    (fn []
      [:div
       [:h1 "AW Frontpage"]
       [:a {:href "#" :on-click common/sign-out} "Sign-Out"]
       ])))
