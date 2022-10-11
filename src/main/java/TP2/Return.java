package TP2;

/**
 * Class representing the return instruction
 */
public class Return extends Instruction {
    Llvm.Type type;
    String value;

    /**
     * The return instruction
     * @param type type of the return value
     * @param value value to be returned
     */
    public Return(Llvm.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return "ret " + type + " " + value + "\n";
    }
}
