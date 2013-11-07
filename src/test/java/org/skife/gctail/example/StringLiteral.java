package org.skife.gctail.example;

public class StringLiteral extends Atom
{
    private final String literal;

    public StringLiteral(String literal)
    {
        this.literal = literal;
    }

    public String getValue() {
        return literal;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringLiteral that = (StringLiteral) o;

        if (!literal.equals(that.literal)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return literal.hashCode();
    }
}
