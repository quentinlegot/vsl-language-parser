package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Void;
import TP2.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.condition.LabelInstruction;

public class IfThenElseExpression extends Expression {

    private final IfConditionExpression ifPart;
    private final ElseConditionExpression elsePart;

    public IfThenElseExpression(IfConditionExpression ifPart, /* Nullable */ElseConditionExpression elsePart) {
        this.ifPart = ifPart;
        this.elsePart = elsePart;
    }
    @Override
    public String pp() {
        return ifPart.pp() + (elsePart != null ? elsePart.pp() : "") + "FI\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        String thenLab = Utils.newlab("then");
        String elseLab = Utils.newlab("else");
        String endLab = Utils.newlab("fi");
        ifPart.setThenLab(thenLab);
        if(elsePart != null)
            ifPart.setElseLab(elseLab);
        ifPart.setEndLab(endLab);
        RetExpression retIf = ifPart.toIR(table, indent);
        if(elsePart != null) {
            elsePart.setElseLab(elseLab);
            elsePart.setEndLab(endLab);
            RetExpression retElse = elsePart.toIR(table, indent);
            retIf.ir.append(retElse.ir);
        }
        Instruction endIns = new LabelInstruction(endLab);
        retIf.ir.appendCode(endIns);
        return new RetExpression(retIf.ir, new Void(), retIf.result);
    }
}
