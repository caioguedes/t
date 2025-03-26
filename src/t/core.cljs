(ns t.core)

(defonce root (js/document.getElementById "root"))

(set! (.-innerHTML root) "<h1>Hello World!</h1>")

