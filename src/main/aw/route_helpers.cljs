(ns aw.route-helpers
  (:require [reitit.frontend.easy :as rfe]))

(defn resolve-href [to]
  (rfe/href to))

(defn redirect! [to & [push]]
  (if push
    (rfe/push-state to)
    (rfe/replace-state to)))

(defn route-perms [match]
  (get-in match [:data :perms]))

(defn route-view [match]
  (get-in match [:data :view]))

(defn route-name [match]
  (get-in match [:data :name]))
