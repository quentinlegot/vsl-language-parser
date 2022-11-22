package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.condition.ConditionalJumpInstruction;
import TP2.condition.IcmpInstruction;
import TP2.condition.LabelInstruction;
import TP2.condition.UnconditionalJump;

public class IfConditionExpression extends AbstractCondition {

    private final Expression booleanExpression;
    private String thenLab;
    private /* Nullable */ String elseLab;
    private String endLab;

    public IfConditionExpression(Expression booleanExpression, Expression blockOrInstruction) {
        super(blockOrInstruction);
        this.booleanExpression = booleanExpression;
    }

    @Override
    public String pp() {
        return "IF " + booleanExpression.pp() + " THEN\n" + instruction.pp();
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression bool = booleanExpression.toIR(table, indent);
        String condTmp = Utils.newtmp();
        Instruction icmp = new IcmpInstruction(indent, bool.type.toLlvmType(), bool.result, condTmp);
        bool.ir.appendCode(icmp);
        Instruction br = new ConditionalJumpInstruction(indent, condTmp, thenLab, elseLab != null ? elseLab : endLab);
        bool.ir.appendCode(br);
        Instruction ThenLabel = new LabelInstruction(thenLab);
        bool.ir.appendCode(ThenLabel);
        RetExpression retExpressions = instruction.toIR(table, indent);
        bool.ir.append(retExpressions.ir);
        Instruction jump = new UnconditionalJump(indent, endLab);
        bool.ir.appendCode(jump);
        return new RetExpression(bool.ir, bool.type, bool.result);
    }

    public void setThenLab(String label) {
        this.thenLab = label;
    }

    public void setElseLab(String label) {
        this.elseLab = label;
    }

    public void setEndLab(String label) {
        this.endLab = label;
    }

}
