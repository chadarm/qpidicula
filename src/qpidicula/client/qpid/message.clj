(ns qpidicula.client.qpid.message
  (:import
    (org.apache.qpid.jms  JmsSession JmsConnectionFactory)
    (javax.jms BytesMessage Message MapMessage TextMessage Connection
               ObjectMessage StreamMessage)))



;https://docs.oracle.com/javaee/7/api/javax/jms/Message.html


(def default-options {:delivery-mode Message/DEFAULT_DELIVERY_MODE
                      :priority      Message/DEFAULT_PRIORITY
                      :time-to-live  Message/DEFAULT_TIME_TO_LIVE
                      :delay         Message/DEFAULT_DELIVERY_DELAY})


(defn awk [message]
  (.acknowledge message))


(defn create-basic
  ""
  [^JmsSession session]
  (.createMessage session))

;(defn write-body
;  [^Message mess]
;  (. mess))

(defn read-basic
  ""
  [^Message message typ]
  (.getBody message typ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Creating a string message

(defn create-text
  [^JmsSession session ^String text]
  (.createTextMessage session text))

(defn read-text
  [^TextMessage message]
  (.getText message))

(comment
  ;; @TODO - Should eventually move this to a test
  (do
    (def url "amqp://0.0.0.0:1111")
    (def factory (JmsConnectionFactory. url))
    (def ^Connection conn (.createConnection factory))
    (def session (.createSession conn false JmsSession/AUTO_ACKNOWLEDGE))
    (def mess (create-text session "Hello"))
    (read-text mess)))
;;;;



(defn create-bytes
  ""
  [^JmsSession session bytes]
  (let [mess (.createBytesMessage session)]
    (.writeBytes mess bytes)))

;(defn read-bytes
;  ""
;  [^BytesMessage mess]
;  (let [length    (.getBodyLength mess)
;        new-array (Byte [length])
;        mess-body (.readBytes new-array length)]
;    mess-body))

