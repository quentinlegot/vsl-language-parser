package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Void;
import TP2.Instruction;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.condition.LabelInstruction;
import TP2.condition.UnconditionalJump;

public class ElseConditionExpression extends AbstractCondition {

    private String endLab;
    private String elseLab;

    public ElseConditionExpression(Expression blockOrInstruction) {
        super(blockOrInstruction);
    }
    @Override
    public String pp() {
        return "ELSE" + instruction.pp();
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        RetExpression ret = new RetExpression(new Llvm.IR(), new Void(), "");
        Instruction elseLabel = new LabelInstruction(elseLab);
        ret.ir.appendCode(elseLabel);
        ret.ir.append(instruction.toIR(table).ir);
        Instruction jump = new UnconditionalJump(endLab);
        ret.ir.appendCode(jump);
        return ret;
    }

    public void setElseLab(String label) {
        this.elseLab = label;
    }

    public void setEndLab(String label) {
        this.endLab = label;
    }
}
