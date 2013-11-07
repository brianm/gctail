package org.skife.gctail.example;

public abstract class Operator
{
    private boolean value;

    public static Operator valueOf(String match)
    {
        if (match.equals("=") || match.equals("==")) {
            return new Equals();
        }
        else if (match.equals("!=") || match.equals("<>")) {
            return new NotEquals();
        }
        else {
            throw new UnsupportedOperationException("Not Yet Implemented!");
        }
    }

    public abstract int getValue();
}
