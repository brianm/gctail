package org.skife.gctail.example;

import com.google.common.base.Objects;

public class VariableName extends Atom
{
    private final String variable;

    public VariableName(String variable)
    {
        this.variable = variable;
    }

    public String getName() {
        return variable;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariableName that = (VariableName) o;

        if (!variable.equals(that.variable)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return variable.hashCode();
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(VariableName.class)
            .add("name", variable)
            .toString();
    }
}
