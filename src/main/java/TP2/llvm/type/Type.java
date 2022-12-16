package TP2.llvm.type;

/**
 * The abstract type representing the LLVM types
 */
public abstract class Type {
    public abstract String toString();

    public int getSize() {
            return 1; // default value
        }
}
