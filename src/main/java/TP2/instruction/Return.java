package TP2.instruction;

import TP2.Llvm;
import TP2.Utils;
import TP2.instruction.Instruction;

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
    public Return(int indent, Llvm.Type type, String value) {
        super(indent);
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return Utils.indent(indent) + "ret " + type + " " + value + "\n";
    }
}
