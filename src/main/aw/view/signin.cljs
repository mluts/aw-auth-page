(ns aw.view.signin
  (:require
   [reagent.core :as r]
   [clojure.string :as str]))

(defn- ensure-coll [v]
  (if (coll? v) v [v]))

(defn keyboard-input [-name state & [{placeholder :placeholder
                                      -type :type
                                      kp :kp
                                      :or {-type "text"
                                           kp [-name]
                                           placeholder (str/capitalize (name -name))}}]]
  {:pre [(some? -name) (some? state)]}

  [:input {:type -type
           :placeholder (or placeholder (str/capitalize (name -name)))
           :name (name -name)
           :value (get-in @state (ensure-coll kp))
           :on-change (fn [event]
                        (swap! state assoc-in (ensure-coll kp) (-> event .-target .-value)))}])

(defn signin-form [state]
  [:form
   {:on-submit (fn [e]
                 (.preventDefault e)
                 (js/console.log "Submit!"))}
   (keyboard-input :username state :username)
   (keyboard-input :password state :password)

   [:input {:type "submit" :value "Sign-In"}]])

(defn view []
  (let [signin-state (r/atom {})]
    (fn []
      [:div
       [:h1 "AW Sign-In"]
       [signin-form signin-state]])))
