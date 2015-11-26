(ns jank.type-test
  (:require [clojure.test :refer :all]
            [jank.bootstrap :refer :all :refer-macros :all]))

(def error #"type error:")

(defn test-file [file]
  (println "testing" file)
  (if (should-fail? file)
    (is (thrown-with-msg? AssertionError
                          error
                          (valid-type? file)))
    (is (valid-type? file))))

(deftest bindings
  (doseq [file ["fail_function_with_incompatible_type.jank"
                "fail_identifier_with_incompatible_type.jank"
                "fail_incompatible_value.jank"
                "fail_multiple_definition_different_type.jank"
                "fail_multiple_definition_same_type.jank"
                "fail_unknown_type.jank"
                "fail_unknown_value_identifier.jank"
                "pass_deduce_type.jank"
                "pass_proper_types.jank"]]
    (test-file (str "test/type/binding/" file))))

(deftest first-class-lambdas
  (doseq [file ["pass_as_param.jank"
                "fail_incorrect_return_type.jank"
                "fail_incorrect_param_type.jank"
                "pass_simple.jank"
                "pass_return_lambda.jank"
                "pass_with_params.jank"
                "pass_higher_order_lambda.jank"]]
    (test-file (str "test/type/lambda/first-class/" file))))

(deftest lambda-bindings
  (doseq [file ["fail_incorrect_type.jank"
                "fail_invalid_param_type.jank"
                "fail_invalid_return_type.jank"
                "pass_with_type.jank"
                "pass_simple.jank"
                "pass_call.jank"]]
    (test-file (str "test/type/lambda/bind/" file))))

(deftest if-definitions
  (doseq [file ["fail_integer_condition.jank"
                "pass_boolean_condition.jank"
                "pass_with_else.jank"]]
    (test-file (str "test/type/if/define/" file))))

(deftest if-expressions
  (doseq [file ["fail_different_types.jank"
                "fail_invalid_param_type.jank"
                "fail_without_else.jank"
                ; TODO: Fix these
                ;"pass_matching_types.jank"
                ;"pass_if_as_condition.jank"
                ]]
    (test-file (str "test/type/if/expression/" file))))

(deftest function-calls
  (doseq [file ["fail_invalid_function.jank"
                "fail_invalid_param_type.jank"
                "fail_too_few_params.jank"
                "fail_too_many_params.jank"
                "pass_chain.jank"
                "pass_empty.jank"
                "pass_function_call_param.jank"
                "pass_print.jank"
                "pass_print_primitive.jank"
                ; TODO: Fix
                ;"pass_recursion.jank"
                ]]
    (test-file (str "test/type/function/call/" file))))

(deftest nested-functions
  (doseq [file ["fail_multiple_inner_definition.jank"
                "pass_capture_params.jank"
                "pass_define.jank"
                "pass_overload_inner.jank"
                "pass_overload_outer_call_outer.jank"
                "pass_overload_outer.jank"
                "pass_overload_self.jank"
                ; TODO: Fix
                ;"pass_redefine_outer.jank"
                ;"pass_redefine_self.jank"
                ]]
    (test-file (str "test/type/function/nest/" file))))
