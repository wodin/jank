#pragma once

#include <jank/runtime/object.hpp>
#include <jank/runtime/behavior/seq.hpp>

namespace jank::runtime::obj
{
  struct set : object, behavior::seqable, pool_item_base<set>
  {
    set() = default;
    set(set &&) = default;
    set(set const &) = default;
    set(detail::set_type &&d)
      : data{ std::move(d) }
    { }
    set(detail::set_type const &d)
      : data{ d }
    { }

    detail::boolean_type equal(object const &) const override;
    detail::string_type to_string() const override;
    detail::integer_type to_hash() const override;

    set const* as_set() const override;
    behavior::seqable const* as_seqable() const override;

    behavior::sequence_pointer seq() const override;

    detail::set_type data;
  };
}