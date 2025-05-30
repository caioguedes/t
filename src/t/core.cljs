(ns t.core
  (:require [reagent.dom.client :as rd]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend :as rf]
            [cljs.pprint :refer [pprint]]))

(defonce match (r/atom nil))

;; Pages
(defn home-page []
  [:h1 "Home"])

(defn contact-page []
  [:h1 "Contact"])

(defn notfound-page []
  [:h1 "Not Found!"])

(defn navbar []
  [:ul
   [:li
    [:a {:href (rfe/href :home)} "Home"]]
   [:li
    [:a {:href (rfe/href :contact)} "Contact"]]])

;; App
(defn app []
  [:<>
   [navbar]
   (when @match
     (let [view (:view (:data @match))]
       [view @match]))
   [:pre (with-out-str (cljs.pprint/pprint @match))]])

(def routes
  [["/"
    ["" {:name :home
         :view home-page}]
    ["contact" {:name :contact
                :view contact-page}]]])

(defonce root (rd/create-root (js/document.getElementById "root")))

(defn ^:dev/after-load init []
  (rfe/start!
   (rf/router routes)
   (fn [new-match]
     (reset! match new-match))
   {:use-fragment true})
  (rd/render root [app]))
