package TP2;

public class Affect extends Instruction {

    private final String ident;
    Llvm.Type type;
    String value;

    /**
     * The return instruction
     * @param type type of the return value
     * @param value value to be returned
     */
    public Affect(Llvm.Type type, String ident, String value) {
        this.type = type;
        this.ident = ident;
        this.value = value;
    }

    @Override
    public String toString() {
        return "store " + type.toString() + " " +  value + ", " + type.toString() + "* %" + ident + "\n";
    }
}
