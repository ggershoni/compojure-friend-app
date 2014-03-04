(defproject compojure-friend-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [com.cemerick/friend "0.2.0"]]
  :plugins [[lein-ring "0.8.10"]]
;;;  :ring {:handler compojure-friend-app.handler/app}
  :ring {:handler compojure-friend-app.handler/page}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
