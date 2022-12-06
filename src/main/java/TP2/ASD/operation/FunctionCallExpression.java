package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Void;
import TP2.*;
import TP2.function.FunctionCallInstruction;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionCallExpression extends Expression {

    private final String ident;
    private final LinkedList<Expression> parameters;

    public FunctionCallExpression(String ident, LinkedList<Expression> par) {
        this.ident = ident;
        this.parameters = par;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder(ident + "(");
        if(parameters != null) {
            for(int i = 0; i < parameters.size(); i++) {
                Expression par = parameters.get(i);
                str.append(par.pp());
                if(i != parameters.size() - 1) {
                    str.append(", ");
                }
            }
        }
        str.append(")");
        return str.toString();
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        SymbolTable.Symbol symbol = table.lookup("@" + ident);
        if(symbol instanceof SymbolTable.FunctionSymbol) {
            SymbolTable.FunctionSymbol functionSymbol = (SymbolTable.FunctionSymbol) symbol;
            // check arguments
            List<Pair<Llvm.Type, String>> parametersList = new ArrayList<>();
            String tmpVar = "";
            if(!functionSymbol.getReturnType().equals(new Void())) {
                tmpVar = Utils.newtmp();
            }
            RetExpression ret = new RetExpression(new Llvm.IR(), functionSymbol.getReturnType(), tmpVar);
            if(functionSymbol.getArguments().size() == (parameters != null ? parameters.size() : 0)) {
                if(parameters != null) {
                    for(int i = 0; i < functionSymbol.getArguments().size(); i++) {
                        SymbolTable.VariableSymbol arg = functionSymbol.getArguments().get(i);
                        RetExpression retPar = parameters.get(i).toIR(table, indent);
                        ret.ir.append(retPar.ir);
                        parametersList.add(new Pair<>(retPar.type.toLlvmType(), retPar.result));
                        if(!arg.getType().equals(retPar.type)) {
                            throw new TypeException("parameter " + i + " type (" + retPar.type + ") of function call " + ident + " doesn't correspond to expected type (" + arg.getType() + ")");
                        }
                    }
                }
                Instruction callIns = new FunctionCallInstruction(indent, "@" + ident, parametersList, tmpVar, functionSymbol.getReturnType().toLlvmType());
                ret.ir.appendCode(callIns);
                return ret;
            } else {
                throw new TypeException("Missing or too many argument for function call " + ident + ", expected: " + functionSymbol.getArguments().size() + ", actual: " + (parameters != null ? parameters.size() : 0));
            }
        } else {
            throw new TypeException("You call " + ident + " as function but " + ident + " isn't a function");
        }
    }
}
