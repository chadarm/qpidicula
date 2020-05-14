(ns qpidicula.client.qpid.connection
  (:import
    (org.apache.qpid.jms JmsConnection JmsConnectionFactory JmsSession)))


;https://docs.oracle.com/javaee/7/api/javax/jms/ConnectionFactory.html
;https://docs.oracle.com/javaee/7/api/javax/jms/Connection.html
;https://docs.oracle.com/javaee/7/api/javax/jms/JMSContext.html


;;;;;
;; Using a connection factory we will make a connection or a context, which
;; is like a connection and a session mixed into one.

(defn create
  "Create connection to amqp broker for given amqp url
   - url needs to be in format 'amqp://username:passwored@host:port')
   - connection-name is optional
   - username and password not needed for anonymous connections"
  [^String url connection-name]
  (let [factory (JmsConnectionFactory. url)
        conn (.createConnection factory)]
    conn))

(defn stop
  [^JmsConnection conn]
  (.stop conn))

(defn close
  [^JmsConnection conn]
  (.close conn))
