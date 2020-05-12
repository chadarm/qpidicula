(defproject nomnom/bunnicula "2.1.0"
  :description "Bunnicula: RabbitMQ client"
  :url "https://github.com/nomnom-insights/nomnom.bunnicula"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "0.6.0"]
                 [org.apache.qpid/qpid-jms-client "0.51.0"]
                 [com.stuartsierra/component "0.4.0"]
                 [cheshire "5.10.0"]]
  :deploy-repositories {"clojars" {:sign-releases false}}
  :min-lein-version "2.5.0"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"
            :year 2018
            :key "mit"}
  :profiles {:dev
             {:dependencies  [[ch.qos.logback/logback-classic "1.2.3"]]}})
