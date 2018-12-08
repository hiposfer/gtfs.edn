(ns hiposfer.gtfs.edn
  "functionality that creates a mapping between GTFS feed files and clojure
   keywords"
  (:require [clojure.string :as str]
            #?(:clj [clojure.edn :as edn])
            #?(:clj [clojure.java.io :as io])))

(defn- some*
  "an alternative version of Clojure's some which uses reduce instead of
  recur. Returns the value that caused (pred? value) to be true;
  as opposed to Clojure"
  [pred? coll]
  (reduce (fn [_ value] (when (pred? value) (reduced value)))
          nil
          coll))

#?(:clj (defn reference
          []
          (edn/read-string (clojure.core/slurp (io/resource "reference.edn")))))

(defn- identifiers
  [reference]
  (for [feed  (:feeds reference)
        field (:fields feed)
        :when (:unique field)]
    field))

(defn- singularly
  "removes the s at the end of a name"
  [text]
  (cond
    (str/ends-with? text "ies") (str (subs text 0 (- (count text) 3)) "y")
    (str/ends-with? text "s") (subs text 0 (dec (count text)))
    :else text))

(defn- reference?
  "checks if text references a field name based on its content. A reference
  is a field name that ends with the same name as a unique field"
  [identifiers field-name]
  (some (fn [unique-name] (when (str/ends-with? field-name unique-name) unique-name))
        (map :field-name identifiers)))

(defn- gtfs-mapping
  "returns a namespaced keyword that will represent this field in datascript"
  [dataset-unique ns-name field]
  (let [field-name (:field-name field)]
    (cond
      ;; "agency_id" -> :agency/id
      (:unique field)
      (keyword ns-name (last (str/split field-name #"_")))

      ;; "route_short_name" -> :route/short_name
      (str/starts-with? field-name ns-name)
      (keyword ns-name (subs field-name (inc (count ns-name))))

      ;; "trip_route_id" -> :trip/route
      (reference? dataset-unique field-name)
      (keyword ns-name (subs field-name 0 (- (count field-name) (count "_id"))))

      ;; "stop_time_pickup_type" -> :stop_time/pickup_type
      :else (keyword ns-name (:field-name field)))))

(defn- feed-namespace
  [feed]
  (if-let [id (some* :unique (:fields feed))]
    (first (str/split (:field-name id) #"_"))
    (singularly (first (str/split (:filename feed) #"\.")))))
;;(feed-namespace (nth (:feeds gtfs-spec) 9))

(defn fields
  "a sequence of gtfs field data with a :keyword entry.

  Useful to have a direct mapping between GTFS fields and Clojure fully
  qualified keywords"
  [reference]
  (let [dataset-unique (identifiers reference)]
    (for [feed  (:feeds reference)
          :let [ns-name (feed-namespace feed)]
          field (:fields feed)]
      (let [k (gtfs-mapping dataset-unique ns-name field)]
        (assoc field :keyword k :filename (:filename feed))))))

(defn get-mapping
  "given a gtfs feed and field name returns its field data from the gtfs/field

  A single fully qualified keyword is also accepted as argument"
  ([reference filename field-name]
   (reduce (fn [_ v] (when (and (= filename (:filename v))
                                (= field-name (:field-name v)))
                       (reduced v)))
           nil
           (fields reference)))
  ([reference k]
   (reduce (fn [_ v] (when (= k (:keyword v)) (reduced v)))
           nil
           (fields reference))))
;;(get-mapping "agency.txt" "agency_id")
;;(get-mapping "trips.txt" "route_id")
;;(get-mapping "calendar.txt" "service_id")
;;(get-mapping "calendar_dates.txt" "service_id")
;;(get-mapping :calendar/id)
;;(get-mapping :calendar_date/service)
