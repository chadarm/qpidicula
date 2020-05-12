(ns qpidicula.client.qpid.connection
  (:import
    (org.apache.qpid.jms JmsConnection JmsConnectionFactory)))

(defn create
  "Create rabbit connection for given rmq url
   - url needs to be in format 'amqp://username:passwored@host:port/vhost')
   - connection-name is optional"
  [^String rmq-url connection-name]
  (let [factory (ConnectionFactory.)]
    (.setUri factory rmq-url)
    (.newConnection factory ^String connection-name)))

(defn close
  [^Connection conn]
  (if (.isOpen ^Connection conn)
    (.close ^Connection conn)))
