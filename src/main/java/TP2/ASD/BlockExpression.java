package TP2.ASD;

import TP2.ASD.type.Type;
import TP2.ASD.type.Void;
import TP2.Instruction;
import TP2.Return;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

public class BlockExpression extends Expression {

    private final List<Expression> instructions;
    /**
     * isFunctionBlock is true when the block represent the entire content of a function, otherwise false
     * If true, will add a return instruction if not wrote in vsl+, otherwise won't display return instruction at all
     */
    private boolean isFunctionBlock;
    private Type type = new Void();

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

    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        boolean alreadyHasReturn = false;
        SymbolTable blockTable = new SymbolTable(table);
        RetExpression startRet = instructions.get(0).toIR(blockTable, indent);
        for (int i = 1; i < instructions.size(); i++) {
            // TODO check if return expression exist
            RetExpression ret = instructions.get(i).toIR(blockTable, indent);
            startRet.ir.append(ret.ir);
        }
        // TODO only add a return instruction when type isn't void
        if(!alreadyHasReturn && isFunctionBlock) {
            Instruction retExpr = new Return(indent, type.toLlvmType(), (type instanceof Void) ? "" : "0"); // default value is 0 if no return has been added in vsl code
            startRet.ir.appendCode(retExpr);
        }

        return new RetExpression(startRet.ir, startRet.type, startRet.result);
    }

    public void setFunctionBlock(boolean functionBlock) {
        isFunctionBlock = functionBlock;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
