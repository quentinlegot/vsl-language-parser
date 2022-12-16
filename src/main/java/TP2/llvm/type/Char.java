package TP2.llvm.type;

public class Char extends Type {

    @Override
    public String toString() {
        return "i8";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Char;
    }
}
