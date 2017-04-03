(ns redis-demo.core-test
  (:require [clojure.test :refer :all]
            [redis-demo.core :refer :all]
            [taoensso.carmine :as car :refer (wcar)]))

(def server1-conn  {:pool {} :spec {:host "127.0.0.1" :port 6379}})
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(deftest set
  (wcar* (car/set :foo 1))
  (wcar* (car/set :comp {:user "gabriel" :nick "gabrielgio"}))
  (testing "SET"
    (is (= (wcar* (car/get :foo)) "1"))
    (is (not= (wcar* (car/get :foo)) 1))
    (is (= (:user (wcar* (car/get :comp))) "gabriel"))
    (is (= (:nick (wcar* (car/get :comp))) "gabrielgio"))))

(deftest lpush
  (wcar* (car/lpush :bar {:user "gabriel" :nick "gabs"}))
  (testing "LPUSH"
    (is (= (:user (wcar* (car/lpop :bar))) "gabriel"))))
