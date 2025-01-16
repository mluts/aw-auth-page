(ns aw.user
  (:require [shadow.cljs.devtools.api :as shadow]))

(defn cljs-repl []
  (shadow/repl :app))

(comment
  (cljs-repl)

  :cljs/quit)
