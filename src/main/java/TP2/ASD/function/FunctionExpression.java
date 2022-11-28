package TP2.ASD.function;

import TP2.ASD.BlockExpression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Type;
import TP2.Instruction;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.function.EndFunctionInstruction;
import TP2.function.PrototypeInstruction;
import org.antlr.v4.runtime.misc.Pair;

import java.util.LinkedList;

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
        SymbolTable nTable = new SymbolTable(table);
        if(parameters != null) {
            for(Pair<Type, String> parameter : parameters) {
                nTable.add(new SymbolTable.VariableSymbol(parameter.a, "%" + parameter.b));
            }
        }
        Instruction funcIns = new PrototypeInstruction(indent, type.toLlvmType(), "@" + ident, parameters);
        // TODO alloca des paramètres
        ret.ir.appendCode(funcIns);
        block.setFunctionBlock(true);
        block.setType(type);
        ret.ir.append(block.toIR(nTable, indent + 1).ir);
        Instruction endFuncIns = new EndFunctionInstruction();
        ret.ir.appendCode(endFuncIns);
        return ret;
    }
}
