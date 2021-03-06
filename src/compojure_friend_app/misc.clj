(ns compojure-friend-app.misc
  (:require [clojure.string :as str])
  (:import java.net.URI))

(def github-base-url
  "https://github.com/cemerick/friend-demo/blob/master/src/clj/")

(defn github-url-for
  [ns-name]
  (str github-base-url
       (-> (name ns-name)
         (.replace \. \/)
         (.replace \- \_))
       ".clj"))

(defn github-link
  [req]
  [:div {:style "float:right; width:50%; margin-top:1em; text-align:right"}
   [:a {:class "button" :href (github-url-for (-> req :demo :ns-name))} "View source"]
   " "
   [:a {:class "button secondary" :href "/"} "All demos"]])

(defn resolve-uri
  [context uri]
  (let [context (if (instance? URI context) context (URI. context))]
    (.resolve context uri)))

(defn context-uri
  "Resolves a [uri] against the :context URI (if found) in the provided
   Ring request.  (Only useful in conjunction with compojure.core/context.)"
  [{:keys [context]} uri]
  (if-let [base (and context (str context "/"))]
    (str (resolve-uri base uri))
    uri))

(defn request-url
  "Returns the full URL that provoked the given Ring request as a string."
  [{:keys [scheme server-name server-port uri query-string]}]
  (let [port (when (or (and (= :http scheme) (not= server-port 80))
                       (and (= :https scheme) (not= server-port 443)))
               (str ":" server-port))]
    (str (name scheme) "://" server-name port uri
         (when query-string (str "?" query-string)))))

(def ns-prefix "cemerick.friend-demo")
(defn ns->context
  [ns]
  (str "/" (-> ns ns-name name (subs (inc (count ns-prefix))))))

(def pretty-head
  [:head [:link {:href "/css/normalize.css" :rel "stylesheet" :type "text/css"}]
         [:link {:href "/css/foundation.min.css" :rel "stylesheet" :type "text/css"}]
         [:style {:type "text/css"} "ul { padding-left: 2em }"]
         [:script {:src "/js/foundation.min.js" :type "text/javascript"}]])

(defn pretty-body
  [& content]
  [:body {:class "row"}
   (into [:div {:class "columns small-12"}] content)])
