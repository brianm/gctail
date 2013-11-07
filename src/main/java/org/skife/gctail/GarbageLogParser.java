package org.skife.gctail;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.SuppressNode;

public class GarbageLogParser extends BaseParser<Object>
{

    @SuppressWarnings("InfiniteRecursion")
    public Rule Predicate()
    {
        return Sequence(OptionalWhitespace(), EOI);
    }

    @SuppressNode
    public Rule OptionalWhitespace()
    {
        return Optional(OneOrMore(AnyOf(" \t")));
    }
}
