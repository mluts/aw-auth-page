(ns aw.user
  (:require [shadow.cljs.devtools.api :as shadow]))

(defn cljs-repl []
  (shadow/repl :app))

(comment
 (require 'md5.core)

 (md5.core/string->md5-hex "foo@example.com")

  (cljs-repl)

  :cljs/quit
  )
