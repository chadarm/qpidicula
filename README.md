# qpidicula

A clone of bunnicula, made for AMQP 1.0 in clojure.

[![CircleCI](https://circleci.com/gh/nomnom-insights/nomnom.bunnicula.svg?style=svg)](https://circleci.com/gh/nomnom-insights/nomnom.bunnicula)


## Usage

NOT AVAILABLE YET

**Leiningen** (via [Clojars](https://clojars.org/nomnom/bunnicula))

[![](https://img.shields.io/clojars/v/nomnom/bunnicula.svg)](https://clojars.org/nomnom/bunnicula)


## Documentation

See [here](doc/components.md)

## Release notes

### 0.0.3

- Demolition of some of bunnicula's core components to take out rabbitmq
and anything amqp 9.1 related, like queue and exchange declaration
- Replacing tests to match new codebase
- Changing project.clj to reflect that this is a different project than
bunnicula

### 0.0.2

- Added qpid jms client side code, replacing rabbitmq client side
- Created some basic testing for repl

### 0.0.1

- Started to progress toward a project clean of rabbitmq

## Forked from Bunnicula at this point
### v2.1.0 (18.4.2019)

- update all dependencies
- make it work with Clojure 1.10
- *potentially breaking change* consumer config is now more strict and will throw exceptions if invalid configuration is passed

### v2.0.2 (15.9.2018)

- Open source nomnom/qpidicula library
