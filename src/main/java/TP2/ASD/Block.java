package TP2.ASD;

import java.util.List;

public class Block {

    private final List<AsdInstruction> instructions;

    public Block(List<AsdInstruction> instructionList) {
        this.instructions = instructionList;
    }

    public String pp() {
        StringBuilder str = new StringBuilder();
        for(AsdInstruction instruction : instructions) {
            str.append(instruction.pp()).append("\n");
        }
        return str.toString();
    }

    public Function.RetExpression toIR() {


        return null;
    }
}
