package org.skife.gctail.example;

import org.parboiled.support.ParsingResult;

public class CompilationException extends Exception
{
    public CompilationException(String msg, Throwable e)
    {
        super(msg, e);
    }

    public CompilationException(ParsingResult pr)
    {
        super("parse errors present");
    }
}
