package TP2;

/**
 * The abstract type representing the LLVM instructions
 */
public abstract class Instruction {

    protected final int indent;

    protected Instruction(int indent) {
        this.indent = indent;
    }

    public abstract String toString();
}