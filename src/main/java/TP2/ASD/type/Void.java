package TP2.ASD.type;

import TP2.Llvm;

public class Void extends Type {
    @Override
    public String pp() {
        return "void";
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Void();
    }
}
