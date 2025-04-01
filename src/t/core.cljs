(ns t.core
  (:require [reagent.dom.client :as rd]))

(defn app []
  [:h1 "Hello! Again!"])

(defonce root (rd/create-root (js/document.getElementById "root")))

(rd/render root [app])