package TP2.ASD;

import TP2.ASD.type.Int;
import TP2.*;

import java.util.List;

public class BlockExpression extends Expression {

    private final List<Expression> instructions;

    public BlockExpression(List<Expression> instructionList) {
        this.instructions = instructionList;
    }

    public String pp() {
        StringBuilder str = new StringBuilder();
        for(Expression instruction : instructions) {
            str.append(instruction.pp());
        }
        return str.toString();
    }

    public RetExpression toIR(SymbolTable table) throws TypeException {
        SymbolTable blockTable = new SymbolTable(table);
        RetExpression startRet = new RetExpression(new Llvm.IR(), new Int(), "");
        Instruction startBlock = new StartBlock();
        startRet.ir.appendCode(startBlock);
        startRet.ir.append(instructions.get(0).toIR(blockTable).ir);
        for(int i = 1; i < instructions.size(); i++) {
            RetExpression ret = instructions.get(i).toIR(blockTable);
            startRet.ir.append(ret.ir);
        }
        // TODO only add a return instruction when a return isn't present
        Instruction retExpr = new Return(startRet.type.toLlvmType(), startRet.result);
        startRet.ir.appendCode(retExpr);
        Instruction endBlock = new EndBlock(startRet.result);
        startRet.ir.appendCode(endBlock);
        return new RetExpression(startRet.ir, startRet.type, startRet.result);
    }
}
