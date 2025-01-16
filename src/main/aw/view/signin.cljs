(ns aw.view.signin
  (:require
   [aw.view.common :as common]
   [aw.route-helpers :as rh]
   [reagent.core :as r]
   [aw.session :as session]
   [aw.data :as data]))

(defn submit [state e]
  (.preventDefault e)
  (swap! state assoc :errors #{})

  (if (data/user-exists? (:username @state) (:password @state))
    (do
      (session/authorize!)
      (rh/redirect! :aw/frontpage))
    (swap! state update :errors conj :password-incorrect)))

(defn view []
  (let [state (r/atom {:errors #{}})]
    (fn []
      [:div
       [:h1 "AW Sign-In"]
       [:form
        {:on-submit (partial submit state)}

        (common/keyboard-input :username state {:flags #{:username}})

        (when (contains? (:errors @state) :password-incorrect)
          (common/error-p "Username or password incorrect"))
        (common/keyboard-input :password state {:type :password
                                                :flags #{:current-password}})

        [:input {:type "submit" :value "Sign-In"}]]
       [:p "Not registered? " [:a {:href (rh/resolve-href :aw/signup)} "Sign-Up"]]])))
