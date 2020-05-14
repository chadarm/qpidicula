(ns qpidicula.client.qpid.producer
  (:import
    (org.apache.qpid.jms JmsSession Message)
    (javax.jms MessageProducer CompletionListener Message)))

;; JmsProducer is made to utilize JmsContext, which we aren't doing (yet)




(defn send
  ([^MessageProducer prod ^Message mess]
   (.send prod mess))
  ([^MessageProducer prod ^Message mess ^CompletionListener listener]
   (.send prod mess listener))
  ([^MessageProducer prod ^Message mess deliverMode priority time-to-live]
   (.send prod mess deliverMode priority time-to-live))
  ([^MessageProducer prod ^Message mess deliverMode priority time-to-live ^CompletionListener listener]
   (.send prod mess deliverMode priority time-to-live listener)))





(defn create
  "Creates a MessageProducer to send messages to the specified destination.
  A client uses a MessageProducer object to send messages to a destination.
  Since Queue and Topic both inherit from Destination, they can be used in the
  destination parameter to create a MessageProducer object.

  Parameters:
  - destination - the Destination to send to, or null if this is a producer which does
                  not have a specified destination (e.g a queue named 'queue')

  Throws:
  - JMSException - if the session fails to create a MessageProducer due to some internal error.
  - InvalidDestinationException - if an invalid destination is specified."
  [^JmsSession session destination]
  (.createProducer session destination))


;;;;;;;;;;
;; Utility
(defn set-delivery-delay  [^MessageProducer msg-prod time]
  (.setDeliveryDelay msg-prod time))

(defn set-time-to-live  [^MessageProducer msg-prod time]
  (.setTimeToLive msg-prod time))

(defn set-delivery-mode [^MessageProducer msg-prod mode]
  (.setDeliveryMode msg-prod mode))

(defn set-priority  [^MessageProducer msg-prod priority]
  (.setPriority msg-prod priority))

(defn set-disable-message-id  [^MessageProducer msg-prod id]
  (.setDisableMessageID msg-prod id))

(defn set-disable-message-timestamp  [^MessageProducer msg-prod value]
  (.setDisableMessageTimestamp msg-prod value))
;;;;
