(ns aw.crypto
  (:require [md5.core :as md5]))

(def calc-hash #'md5/string->md5-hex)
