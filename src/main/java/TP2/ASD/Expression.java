package TP2.ASD;

import TP2.SymbolTable;
import TP2.TypeException;

/**
 * Abstract class representing the expressions
 */
public abstract class Expression {

    public abstract String pp();
    // TODO add an indent counter in toIR
    public abstract RetExpression toIR(SymbolTable table, int indent) throws TypeException;
}