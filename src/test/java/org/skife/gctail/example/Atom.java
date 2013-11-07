package org.skife.gctail.example;

public abstract class Atom extends Throwable
{
    public void match() throws Atom {
        throw this;
    }
}
