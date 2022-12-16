package TP2.instruction;

import TP2.llvm.type.Type;

public class DeclareGlobalVarInstruction extends Instruction {

    private final String content;
    private final int length;
    private final String name;
    private final Type type;

    public DeclareGlobalVarInstruction(String name, String content, int length, Type type) {
        super(0);
        this.name = name;
        this.content = content;
        this.length = length;
        this.type = type;
    }

    @Override
    public String toString() {
        return "@" + name + " = global " + "[" + length + " x " + type + "] c\"" + content + "\"\n";
    }
}
