#pragma once

#include <sstream>

#include <jank/analyze/expression.hpp>
#include <jank/analyze/processor.hpp>

namespace jank::runtime
{ struct context; }

namespace jank::codegen
{
  /* There's only one codegen context per thread, so no synchronization is needed for its members. */
  struct context
  {
    context() = delete;
    context(runtime::context &rt_ctx, analyze::context &an_ctx);
    context(context const &) = delete;
    context(context &&) = default;

    void gen(analyze::expression const &, std::ostream &os);
    void gen(analyze::expr::def<analyze::expression> const &, std::ostream &os);
    void gen(analyze::expr::var_deref<analyze::expression> const &, std::ostream &os);
    void gen(analyze::expr::call<analyze::expression> const &, std::ostream &os);
    void gen(analyze::expr::primitive_literal<analyze::expression> const &, std::ostream &os);
    void gen(analyze::expr::vector<analyze::expression> const &, std::ostream &os);
    void gen(analyze::expr::local_reference<analyze::expression> const &, std::ostream &os);
    void gen(analyze::expr::function<analyze::expression> const &, std::ostream &os);

    /* TODO: C++20: Return std::string_view. */
    std::string header_str() const;
    std::string body_str() const;
    std::string footer_str() const;

    runtime::context &rt_ctx;
    analyze::context &an_ctx;
  };
}
