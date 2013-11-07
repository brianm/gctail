package org.skife.gctail.example;

public class NotEquals extends Operator
{
    @Override
    public int getValue()
    {
        return 1;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof NotEquals;
    }

    @Override
    public int hashCode()
    {
        return 7331;
    }

}
