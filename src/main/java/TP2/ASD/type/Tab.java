package TP2.ASD.type;

import TP2.Llvm;

public class Tab<E extends Type> extends Type{

    private final E type;

    public Tab(E type) {
        this.type = type;
    }

    @Override
    public String pp() {
        return "[" + type.pp() + "]";
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Tab<>(type.toLlvmType());
    }
}
