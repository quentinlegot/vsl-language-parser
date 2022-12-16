package TP2.ASD.expression.condition;

import TP2.ASD.expression.Expression;
import TP2.ASD.RetExpression;
import TP2.*;
import TP2.instruction.condition.ConditionalJumpInstruction;
import TP2.instruction.condition.IcmpInstruction;
import TP2.instruction.condition.LabelInstruction;
import TP2.instruction.condition.UnconditionalJump;
import TP2.instruction.Instruction;

public class WhileExpression extends Expression {

    private final Expression expression;
    private final Expression block;

    public WhileExpression(Expression expression, Expression block) {
        this.expression = expression;
        this.block = block;
    }

    @Override
    public String pp() {
        return "WHILE " + expression.pp() + "\nDO\n" + block.pp() + "DONE\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        Llvm.IR IR = new Llvm.IR();
        RetExpression ret = expression.toIR(table, indent);
        String condResult = Utils.newtmp();
        String startLab = Utils.newlab("while");
        String contentWhileLab = Utils.newlab("do");
        String endLab = Utils.newlab("done");
        Instruction jumpToStartIns = new UnconditionalJump(indent, startLab);
        IR.appendCode(jumpToStartIns);
        Instruction startLabIns = new LabelInstruction(startLab);
        IR.appendCode(startLabIns);
        IR.append(ret.ir);
        Instruction icmp = new IcmpInstruction(indent, ret.type.toLlvmType(), ret.result, condResult);
        IR.appendCode(icmp);
        Instruction br = new ConditionalJumpInstruction(indent, condResult, contentWhileLab, endLab);
        IR.appendCode(br);
        Instruction contentLabIns = new LabelInstruction(contentWhileLab);
        IR.appendCode(contentLabIns);
        IR.append(block.toIR(table, indent).ir);
        Instruction jump = new UnconditionalJump(indent, startLab);
        IR.appendCode(jump);
        Instruction endLabIns = new LabelInstruction(endLab);
        IR.appendCode(endLabIns);
        return new RetExpression(IR, ret.type, ret.result);
    }
}
