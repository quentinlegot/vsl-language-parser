package TP2.ASD.expression.io;

import TP2.ASD.expression.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Tab;
import TP2.ASD.type.Type;
import TP2.ASD.type.Void;
import TP2.*;
import TP2.instruction.DeclareGlobalVarInstruction;
import TP2.instruction.Instruction;
import TP2.instruction.ReadInstruction;
import TP2.instruction.getTabPtrInstruction;
import org.antlr.v4.runtime.misc.Pair;

import java.util.LinkedList;

public class ReadExpression extends Expression {

    private final LinkedList<Pair<String, Expression>> idents;

    public ReadExpression(LinkedList<Pair<String, Expression>> idents) {
        this.idents = idents;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder("READ ");
        LinkedList<Pair<String, Expression>> copy = (LinkedList<Pair<String, Expression>>) idents.clone();
        while(!copy.isEmpty()) {
            Pair<String, Expression> current = copy.removeFirst();
            str.append("%").append(current.a);
            if(current.b != null)
                str.append("[").append(current.b.pp()).append("]");
            if(!copy.isEmpty()) {
                str.append(", ");
            }
        }
        return str + "\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression ret = new RetExpression(new Llvm.IR(), new Void(), "");
        for(Pair<String, Expression> ident : idents) {
            SymbolTable.Symbol symbol = table.lookup("%" + ident.a);
            if(symbol instanceof SymbolTable.VariableSymbol) {
                SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
                Utils.LLVMStringConstant result = Utils.stringTransform("%d");
                String globalVar = Utils.newglob("fmt");
                Instruction globalIns = new DeclareGlobalVarInstruction(globalVar, result.getStr(), result.getLength(), new Llvm.Char());
                ret.ir.appendHeader(globalIns);
                String tmp = "%" + ident.a;
                Type insType = variableSymbol.getType();
                if(variableSymbol.getType() instanceof Tab) {
                    RetExpression tab = ident.b.toIR(table, indent);
                    ret.ir.append(tab.ir);
                    tmp = Utils.newtmp();
                    Instruction ptrIns = new getTabPtrInstruction(indent, tmp, (Llvm.Tab<?>) variableSymbol.getType().toLlvmType(), "%" + ident.a, tab.result);
                    ret.ir.appendCode(ptrIns);
                    insType = tab.type;
                }
                Instruction ins = new ReadInstruction(indent, result, globalVar, new Pair<>(new Llvm.Ptr<>(insType.toLlvmType()), tmp));
                ret.ir.appendCode(ins);
            } else {
                throw new IllegalArgumentException(ident + " doesn't exist or isn't a variable");
            }
        }
        return ret;
    }
}
