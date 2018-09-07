(defproject hiposfer/gtfs.edn "0.1.0"
  :description "A simple gtfs to parse the Markdown gtfs reference into edn data"
  :url "https://github.com/hiposfer/kamal"
  :license {:name "LGPLv3"
            :url "https://github.com/hiposfer/kamal/blob/master/LICENSE"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :profiles {:dev {:dependencies [[markdown2clj "0.1.3"]]
                   :plugins [[jonase/eastwood "0.2.6"]]}})
