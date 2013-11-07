package org.skife.gctail.example;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class PredicateParserTest
{

    @Test
    public void testBinaryPredicate() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult<Object> out = new ReportingParseRunner<Object>(p.Predicate()).run("event.sleepState = 'tired' " +
                                                                                  "&& ian != 'wombat squirrel 7'");

        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
//        System.out.println(ParseTreeUtils.printNodeTree(out));
    }

    @Test
    public void testTrinaryPredicate() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult<Object> out = new ReportingParseRunner<Object>(p.Predicate()).run("event.sleepState == 'tired' " +
                                                                                  "&& ian != 'wombat squirrel 7'" +
                                                                                  "&& event.foo == 'bar'");

        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
//        System.out.println(ParseTreeUtils.printNodeTree(out));
    }

    @Test
    public void testEscapedString() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult out = new ReportingParseRunner<ParsingResult>(p.Predicate()).run("event.sleepState == 'ya\\'ll'");
        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
//        System.out.println(ParseTreeUtils.printNodeTree(out));
        assertThat(((Expression)out.resultValue).getLiteralValue()).isEqualTo(new StringLiteral("ya'll"));
    }

    @Test
    public void testNewlineForAnd() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult<Object> out = new ReportingParseRunner<Object>(p.Predicate()).run("event.sleepState == 'tired'\n" +
                                                                                  "ian != 'wombat squirrel 7'");

        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
//        System.out.println(ParseTreeUtils.printNodeTree(out));

    }

    @Test
    public void testExpression() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult<Object> out = new ReportingParseRunner(p.Expression()).run("brian == 'tired'");

        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
//        System.out.println(ParseTreeUtils.printNodeTree(out));
    }

    @Test
    public void testGetResult() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult<Object> out = new BasicParseRunner<Object>(p.Predicate()).run("brian == 'tired'\n" +
                                                                              "ian == 'hungry'");
        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();

        assertThat(out.valueStack.size()).isEqualTo(2);

    }

    @Test
    public void testLiteral() throws Exception
    {
        PredicateParser p = Parboiled.createParser(PredicateParser.class);
        ParsingResult<Object> out = new ReportingParseRunner<Object>(p.Literal()).run("'hello'");

        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
    }

    @Test
    public void testParsePredicate() throws Exception
    {
        Collection<Expression> rs = PredicateParser.create().parsePredicate("a != 'b' && b == 'c'");
        assertThat(rs.size()).isEqualTo(2);
    }

}
