package TP2.ASD.type;

/**
 * Warning: this is the type from VSL+, not the LLVM types!
 */
public abstract class Type {
    public abstract String pp();
    public abstract TP2.llvm.type.Type toLlvmType();
    public int getSize() {
        return 1;
    }

}