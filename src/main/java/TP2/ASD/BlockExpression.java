package TP2.ASD;

import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

public class BlockExpression extends Expression {

    private final List<Expression> instructions;

    public BlockExpression(List<Expression> instructionList) {
        this.instructions = instructionList;
    }

    public String pp() {
        StringBuilder str = new StringBuilder();
        for(Expression instruction : instructions) {
            str.append(instruction.pp()).append("\n");
        }
        return str.toString();
    }

    public RetExpression toIR(SymbolTable table) throws TypeException {
        SymbolTable blockTable = new SymbolTable(table);
        RetExpression startIr = instructions.get(0).toIR(blockTable);
        for (int i = 1; i < instructions.size(); i++) {
            RetExpression ret = instructions.get(i).toIR(blockTable);
            startIr.ir.append(ret.ir);
        }
        // return new Block();
        return null;
    }
}
