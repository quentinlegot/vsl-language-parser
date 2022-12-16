package TP2.ASD.type;

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
    public TP2.llvm.type.Type toLlvmType() {
        return new TP2.llvm.type.Tab<>(type.toLlvmType(), size);
    }

    @Override
    public int getSize() {
        return size;
    }

    public E getInnerType() {
        return type;
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
