(ns jank.interpret.macro
  (:use jank.assert
        jank.debug.log))

(def prelude {{:name "print!"
               :argument-types [:string]} #(println %)})

(defmulti evaluate-item
  (fn [item env]
    (:kind item)))

(defn evaluate
  ([body] (evaluate body prelude))
  ([body env]
   (reduce #(let [item (evaluate-item %2 (:env %1))]
              (assoc %1
                     :cells (conj (:cells %1) item)
                     :env (:env item)))
           {:cells []
            :env env}
           body)))

(defmethod evaluate-item :macro-call
  [item env]
  (pprint "evaluating " (clean-scope item) env)
  ; TODO: if external, the function must be in prelude
  ; TODO: Add arguments to env
  ; TODO: (assoc item [:interpreted :value] ...)
  (let [body (evaluate (get-in item [:definition :body]) env)]
    (-> (assoc-in item [:definition :body] (:cells body))
        (assoc :env (:env body)))))

(defmethod evaluate-item :default
  [item env]
  (interpret-assert false (str "no supported evaluation for '" item "'")))