package TP2.ASD.type;

import TP2.Llvm;

public class Tab<E extends Type> extends Type {

    private final E type;
    private final int size;

    public Tab(E type, int size) {
        this.type = type;
        this.size = size;
    }

    @Override
    public String pp() {
        return type.pp();
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Tab<>(type.toLlvmType(), size);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Tab)) return false;
        Tab<?> o = (Tab<?>) obj;
        return o.type.equals(this.type) &&
                o.size == size;
    }
}
