(defproject hiposfer/gtfs.edn "0.2.0-SNAPSHOT"
  :description "A simple gtfs library that exposes the GTFS spec as edn data"
  :url "https://github.com/hiposfer/kamal"
  :license {:name "LGPLv3"
            :url "https://github.com/hiposfer/kamal/blob/master/LICENSE"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0" :scope "provided"]
                 [org.clojure/clojurescript "1.9.946" :scope "provided"]]
  :profiles {:dev {:dependencies [[markdown2clj "0.1.3"]]
                   :plugins [[jonase/eastwood "0.2.6"]]}}
  ;; deploy to clojars as - lein deploy releases
  :deploy-repositories [["releases" {:sign-releases false :url "https://clojars.org/repo"}]])
