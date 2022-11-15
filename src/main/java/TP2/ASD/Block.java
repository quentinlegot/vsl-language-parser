package TP2.ASD;

import TP2.SymbolTable;

import java.util.List;

public class Block extends Expression {

    private final List<Expression> instructions;

    public Block(List<Expression> instructionList) {
        this.instructions = instructionList;
    }

    public String pp() {
        StringBuilder str = new StringBuilder();
        for(Expression instruction : instructions) {
            str.append(instruction.pp()).append("\n");
        }
        return str.toString();
    }

    public RetExpression toIR(SymbolTable table) {
        return null;
    }
}
