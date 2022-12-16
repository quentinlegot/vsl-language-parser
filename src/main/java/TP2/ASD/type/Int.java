package TP2.ASD.type;

public class Int extends Type {
    public String pp() {
      return "INT";
    }

    @Override public boolean equals(Object obj) {
      return obj instanceof Int;
    }

    public TP2.llvm.type.Type toLlvmType() {
      return new TP2.llvm.type.Int();
    }

  }