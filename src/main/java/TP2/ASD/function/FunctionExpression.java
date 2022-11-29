package TP2.ASD.function;

import TP2.ASD.BlockExpression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Type;
import TP2.*;
import TP2.function.EndFunctionInstruction;
import TP2.function.PrototypeInstruction;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionExpression extends AbstractFunctionExpression {
    private final BlockExpression block;

    public FunctionExpression(Type type, String ident, LinkedList<Pair<Type, String>> parameters, BlockExpression block) {
        super(type, ident, parameters);
        this.block = block;
    }

    @Override
    public String pp() {
        return "FUNC " + type.pp() + " " + ident + "(" + super.pp() + ")\n{\n" + block.pp()+ "}\n";
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
        // TODO alloca des paramètres
        ret.ir.appendCode(funcIns);
        for(int i = 0; i < arguments.size(); i++) {
            SymbolTable.VariableSymbol argument = arguments.get(i);
            Instruction declare = new Declare(indent + 1, argument.getType().toLlvmType(), argument.getIdent() + (i + 1), "", 0);
            ret.ir.appendCode(declare);
            Instruction alloca = new Affect(indent + 1, argument.getType().toLlvmType(), argument.getIdent() + (i + 1), argument.getIdent(), null);
            ret.ir.appendCode(alloca);
        }

        block.setFunctionBlock(true);
        block.setType(type);
        ret.ir.append(block.toIR(nTable, indent + 1).ir);
        Instruction endFuncIns = new EndFunctionInstruction();
        ret.ir.appendCode(endFuncIns);
        return ret;
    }
}
