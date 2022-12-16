package TP2.llvm.type;

public class Tab<E extends Type> extends Type {

    public final E type;
    private final int size;

    public Tab(E type, int size) {
        this.type = type;
        this.size = size;
    }

    public E getInnerType() {
        return type;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return type + "*";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Tab<?>))
            return false;
        Tab<?> o = (Tab<?>) obj;
        return this.size == o.size && this.type.equals(o.type);
    }
}
