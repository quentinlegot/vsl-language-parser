package TP2;

public class Block extends Instruction {

    private final String ident;
    private final int number;
    Llvm.Type type;
    String value;

    public Block(Llvm.Type type, String ident, String value, int number) {
        this.type = type;
        this.ident = ident;
        this.value = value;
        this.number = number;
    }

    @Override
    public String toString() {
        return null;
    }
}
