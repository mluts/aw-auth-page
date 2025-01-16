(ns aw.view.signup
  (:require
   [aw.view.common :as common]
   [aw.route-helpers :as rh]
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
    (swap! state update :errors disj :password-too-short)
    (swap! state update :errors conj :password-too-short))

  (if (data/check-username-length (:username @state))
    (swap! state update :errors disj :username-too-short)
    (swap! state update :errors conj :username-too-short)))

(defn submit [state e]
  (.preventDefault e)
  (swap! state assoc :errors #{})
  (validate! state)

  (if (seq (:errors @state))
    (js/console.error "Errors in signup form: " (str/join ", " (:errors @state)))
    (if (data/user-exists? (:username @state))
      (swap! state update :errors conj :user-already-exists)
      (do (data/register-user (:username @state) (:password @state))
          (rh/redirect! :aw/signin)))))

(defn view []
  (let [state (r/atom {:errors #{}})]
    (fn []
      [:div
       [:h1 "AW Sign-Up"]

       [:form
        {:on-submit (partial submit state)}

        (cond
          (contains? (:errors @state) :username-too-short)
          (common/error-p "Username too short")

          (contains? (:errors @state) :user-already-exists)
          (common/error-p "Username already exists"))

        (common/keyboard-input :username state {:flags #{:username}})

        (when (contains? (:errors @state) :password-too-short)
          (common/error-p "Password too short"))

        (common/keyboard-input :password state {:type :password :flags #{:new-password}})

        (when (contains? (:errors @state) :password-match)
          (common/error-p "Passwords do not match"))

        (common/keyboard-input :password-repeat state
                               {:placeholder "Repeat Password"
                                :flags #{:new-password}
                                :type :password})

        [:input {:type "submit" :value "Sign-Up"}]]

       [:p "Already registered? " [:a {:href (rh/resolve-href :aw/signin)} "Sign-In"]]])))
