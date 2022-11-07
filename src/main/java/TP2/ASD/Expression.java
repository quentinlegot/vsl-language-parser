package TP2.ASD;

import TP2.SymbolTable;
import TP2.TypeException;

/**
 * Abstract class representing the expressions
 */
public abstract class Expression {

    protected SymbolTable table;

    public abstract String pp();
    public abstract RetExpression toIR() throws TypeException;

    public void setTable(SymbolTable table) {
        this.table = table;
    }
}