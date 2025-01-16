(ns aw.view.signup
  (:require
   [aw.view.common :as common]
   [reagent.core :as r]
   [clojure.string :as str]
   [aw.data :as data]))

(defn validate! [state]
  (if (and ((complement str/blank?) (:password @state))
           ((complement str/blank?) (:password-repeat @state))
           (not= (:password @state) (:password-repeat @state)))

    (swap! state update :errors conj :password-match)
    (swap! state update :errors disj :password-match))

  (if (data/check-password-length (:password @state))
    (swap! state update :errors conj :password-too-short)
    (swap! state update :errors disj :password-too-short))

  (if (data/check-username-length (:username @state))
    (swap! state update :errors conj :username-too-short)
    (swap! state update :errors disj :username-too-short)))

(defn submit [state e]
  (.preventDefault e)
  (validate! state)

  (if (seq (:errors @state))
    (js/console.error "Errors in signup form")
    (js/console.log "Submit!")))

(defn view []
  (let [state (r/atom {:errors #{}})]
    (fn []
      [:div
       [:h1 "AW Sign-Up"]

       [:form
        {:on-submit (partial submit state)}

        (when (contains? (:errors @state) :username-too-short)
          (common/error-p "Username too short"))

        (common/keyboard-input :username state)

        (when (contains? (:errors @state) :password-too-short)
          (common/error-p "Password too short"))

        (common/keyboard-input :password state {:type :password})

        (when (contains? (:errors @state) :password-match)
          (common/error-p "Passwords do not match"))

        (common/keyboard-input :password-repeat state
                               {:placeholder "Repeat Password"
                                :type :password})

        [:input {:type "submit" :value "Sign-Up"}]]

       [:p "Already registered? " [:a {:href "#/signin"} "Sign-In"]]])))
