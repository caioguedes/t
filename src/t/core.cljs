(ns t.core
  (:require [reagent.dom.client :as rd]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend :as rf]
            [ajax.core :as http]
            [cljs.pprint :refer [pprint]]))

(def config {:api-url "https://dummyjson.com"})
(defn api-url [path]
  (str (:api-url config) path))

(defonce match (r/atom nil))

;; Pages
(defn home-page []
  [:h1 "Home"])

(defn contact-page []
  [:h1 "Contact"])

(defn private-page []
  [:h1 "Welcome"])

(defn handle-signin-submit [e d]
  (.preventDefault e)
  (println  (http/POST
              (api-url "/auth/login")
              {:format :json
               :params (select-keys d [:username :password])
               :credentials "include"}))
  (println d))

(defn profile-page []
  [:h1 "Profile"])

(defn signin-page []
  (let [f (r/atom {})]
    (fn []
      [:<>
       [:h1 "Sign In"]
       [:form {:method :post :on-submit #(handle-signin-submit % @f)}
        [:label {:for :username} "Username"]
        [:input {:type :text
                 :name :username
                 :required true
                 :on-change #(reset! f (assoc @f :username (-> % .-target .-value)))}]
        [:label {:for :password} "Password"]
        [:input {:type :password
                 :name :password
                 :required true
                 :on-change #(reset! f (assoc @f :password (-> % .-target .-value)))}]
        [:button {:type :submit} "Sign in"]]
       [:pre (with-out-str (pprint @f))]])))

(defn notfound-page []
  [:h1 "Not Found!"])

(defn navbar []
  (let [items {:home "Home"
               :contact "Contact"
               :signin "Sign In"}]
    [:ul
     (for [item items]
       [:li {:key (first item)}
        [:a {:href (rfe/href (first item))} (second item)]])]))

;; App
(defn app []
  [:<>
   [navbar]
   (if @match
     (let [view (-> @match :data :view)]
       [view @match])
     [notfound-page])
   [:pre (with-out-str (pprint @match))]])

(def routes
  [["/"
    ["" {:name :home
         :view home-page}]
    ["contact" {:name :contact
                :view contact-page}]
    ["singin" {:name :signin
               :view signin-page}]]])

(defonce root (rd/create-root (js/document.getElementById "root")))

(defn ^:dev/after-load init []
  (rfe/start!
   (rf/router routes)
   (fn [new-match]
     (reset! match new-match))
   {:use-fragment false})
  (rd/render root [app]))
