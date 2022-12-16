package TP2.llvm.type;

public class Void extends Type {

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Void;
    }
}
