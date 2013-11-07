package org.skife.gctail.example;

import com.google.common.base.Preconditions;

public class Expression implements Comparable<Expression>
{
    private final VariableName left;
    private final Operator op;
    private final StringLiteral right;

    public Expression(Atom left, Operator op, Atom right)
    {
        VariableName attr = null;
        StringLiteral lit = null;
        try { left.match(); }
        catch (StringLiteral sl) {
            lit = sl;
        }
        catch (VariableName vn) {
            attr = vn;
        }
        catch (Atom other) {
            throw new UnsupportedOperationException("Unexpected Atom type: " + other.getClass().getName());
        }

        try { right.match(); }
        catch (StringLiteral sl) {
            lit = sl;
        }
        catch (VariableName vn) {
            attr = vn;
        }
        catch (Atom other) {
            throw new UnsupportedOperationException("Unexpected Atom type: " + other.getClass().getName());
        }

        Preconditions.checkState(attr != null, "Must have variable name in expression");
        Preconditions.checkState(lit != null, "Must have a string literal in expression");

        this.left = attr;
        this.op = op;
        this.right = lit;
    }

    public VariableName getVariableName()
    {
        return left;
    }

    public StringLiteral getLiteralValue()
    {
        return right;
    }

    @Override
    public int compareTo(Expression o)
    {
        int var_name_comp = this.getVariableName().getName().compareTo(o.getVariableName().getName());
        if (var_name_comp != 0) {
            return var_name_comp;
        }

        int op_comp = this.op.getValue() - o.op.getValue();
        if (op_comp != 0) {
            return op_comp;
        }

        // val comp is last thing we have
        return this.getLiteralValue().getValue().compareTo(o.getLiteralValue().getValue());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expression that = (Expression) o;

        if (!left.equals(that.left)) return false;
        if (!op.equals(that.op)) return false;
        if (!right.equals(that.right)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = left.hashCode();
        result = 31 * result + op.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
