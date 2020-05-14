(ns qpidicula.client.qpid.consumer
  (:import
    (com.rabbitmq.client BasicProperties Envelope Channel Consumer)
    (org.apache.qpid.jms JmsConsumer JmsSession MessageConsumer)
    (javax.jms MessageConsumer)))


;; JmsConsumer is built around using JmsContext, which we aren't doing currently

(defn receive
  ([^MessageConsumer consumer]
   (.receiveNoWait consumer))
  ([^JmsConsumer consumer typ]
   (.receiveBodyNoWait consumer typ)))



(defn create
  "Creates a MessageConsumer for the specified destination, using a message selector.
   Since Queue and Topic both inherit from Destination, they can be used in the
   destination parameter to create a MessageConsumer.
   A client uses a MessageConsumer object to receive messages that have been sent to a destination.

   Parameters:
   - session - session that this consumer is created under
   - destination - the Destination to access
   - messageSelector - only messages with properties matching the message selector expression are
                       delivered. A value of null\nor an empty string indicates that there is no
                       message selector for the message consumer.
   - no-local - if true, and the destination is a topic, then the MessageConsumer
               will not receive messages published to the topic by its own connection."
  ([^JmsSession session destination]
   (create session destination ""))
  ([^JmsSession session destination message-selector]
   (.createConsumer session destination message-selector))
  ([^JmsSession session destination message-selector no-local]
   (.createConsumer session destination message-selector no-local)))







(defn- envelope-to-map
  [^Envelope envelope]
  {:routing-key  (.getRoutingKey envelope)
   :exchange     (.getExchange envelope)
   :redelivered? (.isRedeliver envelope)
   :delivery-tag (.getDeliveryTag envelope)})

(defn- properties-to-map
  [^BasicProperties properties]
  {:app-id           (.getAppId properties)
   :content-encoding (.getContentEncoding properties)
   :content-type     (.getContentType properties)
   :correlation-id   (.getCorrelationId properties)
   :delivery-mode    (.getDeliveryMode properties)
   :expiration       (.getExpiration properties)
   :headers          (.getHeaders properties)
   :message-id       (.getMessageId properties)
   :priority         (.getPriority properties)
   :reply-to         (.getReplyTo properties)
   :timestamp        (.getTimestamp properties)
   :type             (.getType properties)
   :user-id          (.getUserId properties)})

(defn- consumer-from-handler
  "Generate a consumer for the given consumable based on the given message-handler.
   `f` will be called on message envelope, properties (both parsed to clojure map)
    and raw body."
  ^Consumer
  [message-handler]
  (reify Consumer
    (handleCancel [_ _])
    (handleCancelOk [_ _])
    (handleConsumeOk [_ _])
    (handleRecoverOk [_ _])
    (handleShutdownSignal [_ _ _])
    (handleDelivery [_ consumer-tag envelope properties body]
      (message-handler
        (envelope-to-map envelope)
        (properties-to-map properties)
        body))))

(defn consume [channel qname message-handler]
  (let [consumer (consumer-from-handler message-handler)]
    (.basicConsume ^Channel channel qname consumer)))

(defn cancel [^Channel channel ^String consumer-tag]
  (.basicCancel channel consumer-tag))

