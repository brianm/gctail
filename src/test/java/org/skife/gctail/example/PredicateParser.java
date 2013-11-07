package org.skife.gctail.example;

import com.google.common.collect.Sets;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SkipNode;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Collection;
import java.util.TreeSet;

@BuildParseTree
public class PredicateParser extends BaseParser<Object>
{

    public static PredicateParser create()
    {
        return Parboiled.createParser(PredicateParser.class);
    }

    public Collection<Expression> parsePredicate(String predicate) throws CompilationException
    {
        ParsingResult pr = new ReportingParseRunner<ParsingResult>(Predicate()).run(predicate);
        if (pr.hasErrors()) {
            throw new CompilationException(pr);
        }

        TreeSet<Expression> builder = Sets.newTreeSet();
        for (Object expr : pr.valueStack) {
            builder.add((Expression) expr);
        }
        return builder;
    }

    @SuppressWarnings("InfiniteRecursion")
    public Rule Predicate()
    {
        return Sequence(Expression(),
                        OptionalWhitespace(),
                        MoreAndExpressions(),
                        EOI);
    }

    @SkipNode
    public Rule MoreAndExpressions()
    {
        return ZeroOrMore(AndExpression());
    }

    @SkipNode
    public Rule AndExpression()
    {
        return Sequence(And(),
                        OptionalWhitespace(),
                        Expression());
    }

    public Rule Expression()
    {
        return Sequence(Atom(),   // pushes atom
                        OptionalWhitespace(),
                        Operator(), // pushes operator
                        OptionalWhitespace(),
                        Atom(), // pushes atom
                        push(new Expression((Atom) pop(),
                                            (Operator) pop(),
                                            (Atom) pop())));
    }


    @SuppressNode
    public Rule OptionalWhitespace()
    {
        return ZeroOrMore(AnyOf(" \r\t"));
    }

    @SkipNode
    public Rule Atom()
    {
        return FirstOf(VariableName(),
                       Literal());
    }

    @SuppressSubnodes
    public Rule VariableName()
    {
        return Sequence(OneOrMore(FirstOf(CharRange('a', 'z'),
                                          CharRange('A', 'Z'),
                                          Ch('.'),
                                          Ch('_'),
                                          Ch('-'))),
                        push(new VariableName(match())));
    }

    @SkipNode
    public Rule Literal()
    {
        return QuotedString();
    }

    @SuppressNode
    public Rule And()
    {
        return FirstOf(String("&&"),
                       Ch('\n'));
    }

    /**
     * @todo handle escaping the ' a la \'
     */
    @SuppressSubnodes
    public Rule QuotedString()
    {
        return Sequence(Ch('\''),
                        ZeroOrMore(FirstOf(String("\\'"), // escaped '
                                           NoneOf("'"))),
                        // remove any escaped ' chars
                        push(new StringLiteral(match().replaceAll("\\\\'", "'"))),
                        Ch('\''));
    }

    @SkipNode
    public Rule Operator()
    {
        return FirstOf(Equal(),
                       NotEqual());
    }

    @SuppressSubnodes
    public Rule Equal()
    {
        return Sequence(FirstOf(String("=="), String("=")),
                        push(new Equals()));
    }

    @SuppressSubnodes
    public Rule NotEqual()
    {
        return Sequence(FirstOf(String("!="), String("<>")),
                        push(new NotEquals()));
    }
}
