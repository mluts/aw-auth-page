(ns aw.view.common
  (:require [clojure.string :as str]))

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

(defn error-p [text] [:p {:class "pico-color-pink-500"} text])
