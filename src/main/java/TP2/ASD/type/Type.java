package TP2.ASD.type;

import TP2.Llvm;

/**
 * Warning: this is the type from VSL+, not the LLVM types!
 */
  public abstract class Type {
    public abstract String pp();
    public abstract Llvm.Type toLlvmType();

    public int getSize() {
      return 1;
    }
  }