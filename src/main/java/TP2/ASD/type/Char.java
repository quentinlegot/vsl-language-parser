package TP2.ASD.type;

public class Char extends Type {

    @Override
    public String pp() {
        return "char";
    }

    @Override
    public TP2.llvm.type.Type toLlvmType() {
        return new TP2.llvm.type.Char();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Char;
    }
}
