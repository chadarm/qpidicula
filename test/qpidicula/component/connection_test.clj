(ns qpidicula.component.connection-test
  (:require
    [clojure.test :refer :all]
    [qpidicula.component.connection :refer :all]
    [com.stuartsierra.component :as component]))

(def amqp-config
  ;{:url (or (System/getenv "RABBIT_URL")
  ;             "amqp://rabbit:password@127.0.0.1:5672")
  ; :vhost (or (System/getenv "RABBIT_VHOST")
  ;           "%2Fmain")
  {:url "amqp://0.0.0.0:1111"
   :connection-name "test-connection"})

(deftest connection-test
  (testing "connection-test"
    (let [c (atom (create amqp-config))]
      (is (nil? (:connection@ c)))
      (swap! c component/start)
      (is (some? (:connection @c)))
      (swap! c component/stop)
      (is (nil? (:connection @c))))))
