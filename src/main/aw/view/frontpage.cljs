(ns aw.view.frontpage
  (:require
   [aw.view.common :as common]
   [reagent.core :as r]))

(defn view []
  (let [state (r/atom {})]
    (fn []
      [:div
       [:h1 "AW Frontpage"]
       ])))
