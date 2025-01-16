(ns aw.view.signin
  (:require
   [aw.view.common :as common]
   [reagent.core :as r]))

(defn submit [e]
  (.preventDefault e)
  (js/console.log "Submit!"))

(defn view []
  (let [state (r/atom {})]
    (fn []
      [:div
       [:h1 "AW Sign-In"]
       [:form
        {:on-submit #'submit}

        (common/keyboard-input :username state)
        (common/keyboard-input :password state {:type :password})

        [:input {:type "submit" :value "Sign-In"}]]
       [:p "Not registered? " [:a {:href "#/signup"} "Sign-Up"]]
       ])))
