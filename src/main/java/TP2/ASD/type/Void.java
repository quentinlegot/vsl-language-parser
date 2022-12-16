package TP2.ASD.type;

public class Void extends Type {
    @Override
    public String pp() {
        return "void";
    }

    @Override
    public TP2.llvm.type.Type toLlvmType() {
        return new TP2.llvm.type.Void();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Void;
    }
}
