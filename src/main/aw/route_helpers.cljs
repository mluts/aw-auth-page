(ns aw.route-helpers
  (:require [reitit.frontend.easy :as rfe]))

(defn resolve-href [to]
  (rfe/href to))

(defn redirect! [to & [push]]
  (if push
    (rfe/push-state to)
    (rfe/replace-state to)))
