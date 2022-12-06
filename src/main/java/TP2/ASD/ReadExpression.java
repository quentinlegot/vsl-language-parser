package TP2.ASD;

import TP2.ASD.type.Void;
import TP2.*;
import org.antlr.v4.runtime.misc.Pair;

public class ReadExpression extends Expression {

    private final String ident;

    public ReadExpression(String ident) {
        this.ident = ident;
    }

    @Override
    public String pp() {
        return "READ %" + ident + "\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        SymbolTable.Symbol symbol = table.lookup("%" + ident);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            RetExpression ret = new RetExpression(new Llvm.IR(), new Void(), "");
            Utils.LLVMStringConstant result = Utils.stringTransform("%d");
            String globalVar = Utils.newglob("fmt");
            Instruction globalIns = new DeclareGlobalVarInstruction(globalVar, result.getStr(), result.getLength(), new Llvm.Char());
            ret.ir.appendHeader(globalIns);
            Instruction ins = new ReadInstruction(indent, result, globalVar, new Pair<>(new Llvm.Ptr<>(((SymbolTable.VariableSymbol) symbol).getType().toLlvmType()), "%" + ident));
            ret.ir.appendCode(ins);
            return ret;
        } else {
            throw new IllegalArgumentException(ident + " doesn't exist or isn't a variable");
        }

    }
}
