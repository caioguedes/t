(ns t.core
  (:require [reagent.dom.client :as rd]
            [reagent.core :as r]
            [clojure.string :as str]))

(def state (r/atom {:route "/"}))

;; History
(defn push-state [event]
  (let [event (or event js/window.event)]
    (.preventDefault event)
    (js/history.pushState {} "" (.-href event.target))
    (swap! state assoc :route (str/replace-first js/location.hash "#" ""))))

;; Components
(defn link [href children]
  [:a {:on-click push-state :href (str "#" href)} children])

;; Page
(defn home-page []
  [:h1 "Home"])

(defn contact-page []
  [:h1 "Contact"])

(defn notfound-page []
  [:h1 "Not Found!"])

;; Router
(defn router [path]
  (cond
    (or (= "/home" path) (= "" path) (= "/" path)) [home-page]
    (= "/contact" path) [contact-page]
    :else [notfound-page]))

;; App
(defn app []
  [:<>
   [:ul
    [:li
     [link "/home" "Home"]]
    [:li
     [link "/contact" "Contact"]]]
   (router (:route @state))
   [:pre (pr-str @state)]])

(defonce root (rd/create-root (js/document.getElementById "root")))

(defn ^:dev/after-load init []
  (set! (.-onpopstate js/window) push-state)
  (rd/render root [app]))