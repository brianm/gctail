package org.skife.gctail;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.skife.gctail.GarbageLogParser;

import static org.assertj.core.api.Assertions.assertThat;

public class GarbageLogParserTest
{
    @Test
    public void testFoo() throws Exception
    {
        GarbageLogParser p = Parboiled.createParser(GarbageLogParser.class);
        ParsingResult<Object> out = new ReportingParseRunner<Object>(
            p.Predicate()).run(" ");

        assertThat(out.parseErrors).isEmpty();
        assertThat(out.matched).isTrue();
    }

}
