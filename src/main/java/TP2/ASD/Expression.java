package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

/**
 * Abstract class representing the expressions
 */
public abstract class Expression {
    public abstract String pp();
    public abstract RetExpression toIR() throws TypeException;



  }