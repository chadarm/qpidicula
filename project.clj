(defproject qpidicula "0.0.2"
  :description "qpidicula: AMQP client"
  :url "https://github.com/chadarm/qpidicula"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "0.6.0"]
                 [org.apache.qpid/qpid-jms-client "0.51.0"]
                 [com.stuartsierra/component "0.4.0"]
                 [cheshire "5.10.0"]]
  :deploy-repositories {"clojars" {:sign-releases false}}
  :min-lein-version "2.5.0"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"
            :year 2020
            :key "mit"}
  :profiles {:dev
             {:dependencies  [[ch.qos.logback/logback-classic "1.2.3"]]}})
