package TP2.llvm.type;

public class Ptr<E extends Type> extends Type {

    private final E type;

    public Ptr(E type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + "*";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ptr<?>))
            return false;
        Ptr<?> o = (Ptr<?>) obj;
        return this.type.equals(o.type);
    }
}
