package TP2.condition;

import TP2.Instruction;
import TP2.Llvm;

public class IcmpInstruction extends Instruction {

    private final Llvm.Type type;
    private final String op1;
    private final String condTmp;

    public IcmpInstruction(Llvm.Type type, String op1, String condTmp) {
        this.type = type;
        this.op1 = op1;
        this.condTmp = condTmp;
    }

    @Override
    public String toString() {
        return condTmp + " = icmp ne " + type + ", " + op1 + ", 0\n";
    }
}
