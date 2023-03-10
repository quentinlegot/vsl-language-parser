package TP2.instruction.condition;

import TP2.instruction.Instruction;
import TP2.Utils;
import TP2.llvm.type.Type;

public class IcmpInstruction extends Instruction {

    private final Type type;
    private final String op1;
    private final String condTmp;

    public IcmpInstruction(int indent, Type type, String op1, String condTmp) {
        super(indent);
        this.type = type;
        this.op1 = op1;
        this.condTmp = condTmp;
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + condTmp + " = icmp ne " + type + " " + op1 + ", 0\n";
    }
}
