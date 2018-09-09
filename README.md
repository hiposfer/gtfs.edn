# gtfs.edn

[![Build Status](https://travis-ci.com/hiposfer/gtfs.edn.svg?branch=master)](https://travis-ci.com/hiposfer/gtfs.edn)
[![Clojars Project](https://img.shields.io/clojars/v/hiposfer/gtfs.edn.svg)](https://clojars.org/hiposfer/gtfs.edn)

The General Transit Feed Specification (GTFS) as pure edn data

Original data taken from https://github.com/google/transit

This is a convenience tool for those who would like to work with GTFS data but
dont want to manually recreate it.


# Usage

This project can be used in two ways, dependending on how you wish to use it.

- as a Clojure(script) library

This is very useful if you want to work with this data in your code. There are several
convenience functions exposed through the `hiposfer.gtfs.edn` namespace.

```clojure
(ns my.namespace
  (:require [hiposfer.gtfs.edn :as gtfs])) 

;; fetch all gtfs fields that are required
(filter :required gtfs/fields)

;; fetch all gtfs fields that represent a dataset unique attribute
(filter :unique gtfs/fields)

;; fetch all gtfs fields for the "agency.txt" feed
(filter #(= "agency.txt" (:filename %)) gtfs/fields)
```

- as a git submodule

If you are only interested in the data itself, and would like to use it without Clojure, you
can add it as a git submodule. You can use one of the many [EDN reader implementations](https://github.com/edn-format/edn/wiki/Implementations)
to parse the gtfs data to your convenience.

```bash
$ git submodule add https://github.com/hiposfer/gtfs.edn.git

## you will find the gtfs reference spec inside the resources dir
$ my-custom-script parse gtfs.edn/resources/reference.edn
```


---
Distributed under LGPL v3
