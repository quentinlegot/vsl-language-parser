package TP2.ASD.type;

import TP2.Llvm;

public class Char extends Type {

    @Override
    public String pp() {
        return "char";
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Char();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Char;
    }
}
