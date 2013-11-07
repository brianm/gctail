package org.skife.gctail.example;

public class Equals extends Operator
{
    @Override
    public int getValue()
    {
        return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Equals;
    }

    @Override
    public int hashCode()
    {
        return 1337;
    }
}
