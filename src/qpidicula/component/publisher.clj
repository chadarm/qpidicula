(ns qpidicula.component.publisher
  (:require
    [clojure.tools.logging :as log]
    [com.stuartsierra.component :as component]
    [qpidicula.client.qpid.channel :as channel]
    [qpidicula.protocol :as protocol]
    [qpidicula.utils :as utils]))

(defrecord Publisher [rmq-connection channel serializer exchange-name]
  component/Lifecycle
  (start [component]
    (if (channel/open? channel)
      component
      (if (:connection rmq-connection)
        (let [{:keys [connection-name connection]} rmq-connection
              channel (channel/create connection)]
          (log/infof "start-publisher connection-name=%s default-exchange=%s"
                     connection-name exchange-name)
          (assoc component :channel channel))
        (throw (ex-info "missing-rmq-connection" {})))))

  (stop [component]
    (log/infof "stop-publisher connection-name=%s" (:connection-name rmq-connection))
    (when (channel/open? channel)
      (channel/close channel))
    (assoc component :channel nil))

  protocol/Publisher
  (publish [this routing-key body]
    (protocol/publish this exchange-name routing-key body {}))

  (publish [this routing-key body options]
    (protocol/publish this exchange-name routing-key body options))

  (publish [this exchange routing-key body options]
    (if (channel/open? channel)
      (channel/publish-message channel {:exchange exchange
                                        :routing-key routing-key
                                        :body (serializer body)
                                        :options options})
      ;; channel should be always available thanks to auto-recovery!
      (throw (ex-info "channel-not-available" {:channel channel
                                               :name name})))))

(defn create
  "Create publisher using serializer fn to convert clojure structure to AMQP message.
   Connection can be passed as argument or injected as dependency!
   Configurable values
   - serializer: by default json-serializer is used"
  [config]
  ;; rmq-connection is required to be injected as dependency
  (map->Publisher
    {:serializer (or (:serializer config) utils/json-serializer)
     :exchange-name (or (:exchange-name config) "")}))
