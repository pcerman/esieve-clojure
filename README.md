# esieve-clojure

This is experimental implementation of the _Sieve of Eratosthenes_ in functional
style in [clojure](https://clojure.org) programming language.
I was inspired by the paper [1].

## License

This code is released under MIT License.

Copyright (c) 2016 Peter Cerman (https://github.com/pcerman)

## Example of use
```clojure
(load-file "esieve.clj")

;; Take 100 first prime numbers.
(take 100 primes)

;; alternatively, but prime numbers are computed again and advantage of lazy
;; sequence is not exploited.
(take 100 (esieve))
```

### Referencies

1. O'Neill, Melissa E., "The Genuine Sieve of Eratosthenes",
   Journal of Functional Programming,
   Published online by Cambridge University Press 09 Oct 2008
   doi:10.1017/S0956796808007004.
