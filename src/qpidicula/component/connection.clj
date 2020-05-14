(ns qpidicula.component.connection
  "Component holds open connection to AMQP Broker"
  (:require
    [qpidicula.client.qpid.connection :as connection]
    [com.stuartsierra.component :as component]
    [clojure.tools.logging :as log]
    [clojure.string :as string])
  (:import (java.net URI)))

(defn- connection-url
  [{:keys [host port username password]}]
  (format "amqp://%s:%s@%s:%s"
          username password host port))

;; @TODO - taken out url parameters and making it all be passed as a url right now
(defrecord Connection [url connection-name  connection]
  component/Lifecycle
  (start [this]
    (if connection
      this
      (let [conn (connection/create url connection-name)]
        (log/infof "amqp-connection start name=%s" connection-name)
        (assoc this :connection conn))))
  (stop [this]
    (log/infof "amqp-connection stop name=%s" connection-name)
    (when connection
      (connection/close connection))
    (assoc this :connection nil)))

(defn extract-server-config
  "If there is not a host or port but there is a url,  pull the host and port out of url"
  [{:keys [url host port username password connection-name]}]
  (if-not (and (some? username) (some? password))
    {:url url
     :connection-name connection-name}
    ({:post [#(string? (:host %))
             #(string? (:port %))
             #(string? (:username %))
             #(string? (:password %))]}
     (if-let [^java.net.URI uri (and url (java.net.URI. url))]
       (let [[username password] (string/split (.getUserInfo uri) #":")]
         {:host (.getHost uri)
          :port (.getPort uri)
          :username username
          :password password
          :connection-name (or connection-name username)})
       {:host host
        :port port
        :username username
        :password password
        :connection-name (or connection-name username)}))))

(defn create
  "Create AMQP broker connection for given connection config.
   Config can either contain ':url' key with full url
   or can be map with username, password, host and port values.
   - {:url 'amqp://user:password@localhost:5492'}
   - {:username 'user' :password 'password' :host 'localhost' :port '5492'}
   Optional config params
   - :connection-name
     - if not specified username is used as connection-name
     - {:url 'amqp://user:password@localhost:5492' :connection-name 'conn1'}"
  [config]
  (map->Connection (extract-server-config config)))
