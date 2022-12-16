package TP2.ASD.expression;

import TP2.ASD.RetExpression;
import TP2.ASD.type.Type;
import TP2.ASD.type.Void;
import TP2.*;
import TP2.instruction.Instruction;
import TP2.instruction.Return;
import TP2.llvm.Llvm;

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
        RetExpression ret = new RetExpression(new Llvm.IR(), type, "");
        for (Expression instruction : instructions) {
            RetExpression retIns = instruction.toIR(blockTable, indent);
            if (instruction instanceof ReturnExpression) {
                if(!ret.type.equals(retIns.type)) {
                    throw new TypeException("Incompatible return type, expected " + ret.type + ", but got " + retIns.type);
                }
                alreadyHasReturn = true;
            }

            ret.ir.append(retIns.ir);
        }
        if(!alreadyHasReturn && isFunctionBlock) {
            Instruction retExpr = new Return(indent, type.toLlvmType(), (type instanceof Void) ? "" : "0"); // default value is 0 if no return has been added in vsl code
            ret.ir.appendCode(retExpr);
        }
        return new RetExpression(ret.ir, ret.type, ret.result);
    }

    public void setFunctionBlock(boolean functionBlock) {
        isFunctionBlock = functionBlock;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
