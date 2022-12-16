package TP2.llvm.type;

public class Int extends Type {
    public String toString() {
        return "i32";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Int;
    }
}
