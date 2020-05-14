(ns qpidicula.client.qpid.session
  (:import
    (org.apache.qpid.jms JmsSession JMSSessionMode JmsConnection)
    (javax.jms JMSSessionMode)))

;; I believe AMQP 1.0 changed channel to session, both seem to be started from
;; connection and then are able to create publishers and consumers

;; Biggest problem is AMQP 1.0 does not allow creation of queues and exchanges
;; RabbitMQ uses/creates exchanges and queues extensively and AMQP 1.0 doesn't seem to pay attention to them


;;;; links
;https://docs.oracle.com/javaee/7/api/javax/jms/Session.html#createQueue-java.lang.String-

(def acknowledgement-mode {:auto       JmsSession/AUTO_ACKNOWLEDGE
                           :client     JmsSession/CLIENT_ACKNOWLEDGE
                           :dup-ok     JmsSession/DUPS_OK_ACKNOWLEDGE
                           :transacted JmsSession/SESSION_TRANSACTED})



(defn open?
  [^JmsSession session]
  (not (.isClosed session)))


(defn rollback
  [^JmsSession session]
  (.rollback session))


(defn commit
  [^JmsSession session]
  (.commit session))


(defn create
  ([^JmsConnection connection]
   (create connection (:auto acknowledgement-mode)))
  ([^JmsConnection connection session-mode]
   (let [session (.createSession connection session-mode)]
     session)))

(defn close
  [^JmsSession session]
  (if (open? session)
    (.close session)
    session))


(defn make-queue
  [^JmsSession session name]
  (.createQueue session name))

