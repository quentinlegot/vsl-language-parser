package TP2.ASD.expression.function;

import TP2.ASD.expression.BlockExpression;
import TP2.ASD.expression.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.expression.ReturnExpression;
import TP2.ASD.type.Type;
import TP2.ASD.type.Void;
import TP2.*;
import TP2.instruction.function.EndBlockInstruction;
import TP2.instruction.function.PrototypeInstruction;
import TP2.instruction.function.StartBlockInstruction;
import TP2.instruction.Affect;
import TP2.instruction.Declare;
import TP2.instruction.Instruction;
import TP2.instruction.Return;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionExpression extends AbstractFunctionExpression {
    private final Expression content;

    public FunctionExpression(Type type, String ident, LinkedList<Pair<Type, String>> parameters, Expression content) {
        super(type, ident, parameters);
        this.content = content;
    }

    @Override
    public String pp() {
        return "FUNC " + type.pp() + " " + ident + "(" + super.pp() + ")\n{\n" + content.pp()+ "}\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression ret = new RetExpression(new Llvm.IR(), type, "");
        SymbolTable.Symbol symbol = table.lookup("@" + ident);
        if(symbol != null) {
            if(symbol instanceof SymbolTable.FunctionSymbol) {
                SymbolTable.FunctionSymbol fnSymbol = (SymbolTable.FunctionSymbol) symbol;
                if(fnSymbol.isDefined()) {
                    throw new TypeException(ident + " function has already been defined");
                }
            } else {
                throw new TypeException(ident + " has already been defined as variable");
            }
        }
        table.remove("@" + ident); // remove if already declared by prototype
        SymbolTable nTable = new SymbolTable(table);
        List<SymbolTable.VariableSymbol> arguments = new ArrayList<>();

        if(parameters != null) {
            int counter = 0;
            for(Pair<Type, String> parameter : parameters) {
                counter++;
                SymbolTable.VariableSymbol arg = new SymbolTable.ArgumentVariableSymbol(parameter.a, "%" + parameter.b, "%" + parameter.b + counter);
                if(nTable.add(arg)) {
                    arguments.add(arg);
                }
            }
        }
        table.add(new SymbolTable.FunctionSymbol(type, "@" + ident, arguments, true));
        Instruction funcIns = new PrototypeInstruction(indent, type.toLlvmType(), "@" + ident, parameters);
        ret.ir.appendCode(funcIns);
        Instruction startBracketIns = new StartBlockInstruction(indent);
        ret.ir.appendCode(startBracketIns);
        for(int i = 0; i < arguments.size(); i++) {
            SymbolTable.VariableSymbol argument = arguments.get(i);
            Instruction declare = new Declare(indent + 1, argument.getType().toLlvmType(), argument.getIdent() + (i + 1), "", 0);
            ret.ir.appendCode(declare);
            Instruction alloca = new Affect(indent + 1, argument.getType().toLlvmType(), argument.getIdent() + (i + 1), argument.getIdent(), null);
            ret.ir.appendCode(alloca);
        }

        if(content instanceof BlockExpression) {
            BlockExpression block = (BlockExpression) content;
            block.setFunctionBlock(true);
            block.setType(type);
            ret.ir.append(block.toIR(nTable, indent + 1).ir);
        } else {
            RetExpression retContent = content.toIR(nTable, indent + 1);
            ret.ir.append(retContent.ir);
            if(!(content instanceof ReturnExpression)) {
                ret.ir.appendCode(new Return(indent + 1, type.toLlvmType(), (type instanceof Void ? "" : retContent.result)));
            }
        }
        ret.ir.appendCode(new EndBlockInstruction(indent));
        return ret;
    }
}
