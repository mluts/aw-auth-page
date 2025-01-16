(ns aw.view.common
  (:require [clojure.string :as str]))

(defn- ensure-coll [v]
  (if (coll? v) v [v]))

(defn keyboard-input [-name state & [{placeholder :placeholder
                                      -type :type
                                      kp :kp
                                      flags :flags
                                      :or {-type "text"
                                           kp [-name]
                                           flags #{}
                                           placeholder (str/capitalize (name -name))}}]]
  {:pre [(some? -name) (some? state)]}

  [:input {:type -type
           :placeholder (or placeholder (str/capitalize (name -name)))
           :autoComplete (cond
                           (contains? flags :username) "username"
                           (contains? flags :new-password) "new-password"
                           (contains? flags :current-password) "current-password")
           :name (name -name)
           :value (get-in @state (ensure-coll kp))
           :on-change (fn [event]
                        (swap! state assoc-in (ensure-coll kp) (-> event .-target .-value)))}])

(defn error-p [text] [:p {:class "pico-color-pink-500"} text])
