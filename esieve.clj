;; This source code is released under MIT License
;; Copyright (c) 2016 Peter Cerman (https://github.com/pcerman)

;;----------------------------------------------------------------------
;; [Pairing heap](https://en.wikipedia.org/wiki/Pairing_heap)

(defn ph:merge [h1 h2]
  (cond (empty? h1) h2
        (empty? h2) h1
        :else       (let [k1 ((first h1) 0)
                          k2 ((first h2) 0)]
                      (if (< k1 k2)
                        (list* (first h1) h2 (rest h1))
                        (list* (first h2) h1 (rest h2))))))

(defn ph:insert [ph k v]
  (ph:merge (list (vector k v)) ph))

(defn ph:first [ph]
  (if (empty? ph) nil
      (first ph)))

(defn ph:merge-pairs [ls]
  (loop [ls ls ph nil]
    (cond (empty? ls) ph
          (empty? (rest ls)) (ph:merge ph (first ls))
          :else (recur (rest (rest ls))
                       (ph:merge (ph:merge (first ls) (second ls))
                                 ph)))))

(defn ph:remove-min [ph]
  (if (empty? ph) nil
      (ph:merge-pairs (rest ph))))

;;----------------------------------------------------------------------
;; **Infinite lazy sequence of the prime numbers**
;; This is implementation of the Sieve of Eratosthenes by using
;; functional programming principles.

(defn esieve []
  (letfn [(sieve-seq [pn wh mp]
            (let [kv (ph:first mp)
                  kn (if kv (kv 0) (inc pn))]
              (cond (< kn pn) (recur pn wh (ph:insert (ph:remove-min mp)
                                                      (+ kn (kv 1))
                                                      (kv 1)))
                    (= kn pn) (recur (+ pn (first wh))
                                     (next wh)
                                     (ph:insert (ph:remove-min mp)
                                                (+ kn (kv 1))
                                                (kv 1)))
                    :else (cons pn
                                (lazy-seq
                                  (sieve-seq (+ pn (first wh))
                                             (next wh)
                                             (ph:insert mp (* pn pn)
                                                           (+ pn pn))))))))]
    (let [wheel2357 (cycle [2 4 2 4 6 2 6 4 2 4 6 6 2 6 4 2
                            6 4 6 8 4 2 4 2 4 8 6 4 6 2 4 6
                            2 6 6 4 2 4 6 2 6 4 2 4 2 10 2 10])]
      (concat
        (lazy-seq '(2 3 5 7))
        (sieve-seq 11 wheel2357 nil)))))

(def primes (esieve))

;;----------------------------------------------------------------------
;; **Example:**
;; Function esieve-example returns lazy-seq of first 25 primes:
;; (2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97)

(defn esieve-example []
  (take 25 primes))
