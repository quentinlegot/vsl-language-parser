package TP2.ASD.operation;

import TP2.ASD.expression.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.expression.operation.AddExpression;
import TP2.ASD.expression.operation.FunctionCallExpression;
import TP2.ASD.expression.operation.IntegerExpression;
import TP2.ASD.expression.operation.MulExpression;
import TP2.ASD.type.Int;
import TP2.*;
import TP2.instruction.function.FunctionCallInstruction;
import TP2.instruction.Instruction;
import TP2.llvm.Llvm;
import TP2.llvm.type.Type;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionCallTest {

    private String fName;
    private LinkedList<Expression> parameters;
    private FunctionCallExpression fCall;

    @BeforeEach
    void init() {
        Utils.resetCounter();
        this.fName = "fact";
        Expression par1 = new AddExpression(new IntegerExpression(5), new IntegerExpression(7));
        Expression par2 = new MulExpression(new AddExpression(new IntegerExpression(7), new IntegerExpression(1)), new IntegerExpression(4));
        this.parameters = new LinkedList<>();
        parameters.add(par1);
        parameters.add(par2);
        this.fCall = new FunctionCallExpression(fName, parameters);
    }

    @Test
    void testPp() {
        Assertions.assertEquals(fName + "((5 + 7), ((7 + 1) * 4))", fCall.pp());
    }

    @Test
    void testToIR() throws TypeException {
        SymbolTable table = new SymbolTable();
        List<SymbolTable.VariableSymbol> args = new ArrayList<>();
        args.add(new SymbolTable.VariableSymbol(new Int(), "par1"));
        args.add(new SymbolTable.VariableSymbol(new Int(), "par2"));
        SymbolTable.FunctionSymbol fSymbol = new SymbolTable.FunctionSymbol(new Int(), "@" + fName, args, true);
        table.add(fSymbol);
        String tmpVar = Utils.newtmp();
        List<Pair<Type, String>> parametersList = new ArrayList<>();
        RetExpression ret = new RetExpression(new Llvm.IR(), new Int(), tmpVar);
        for(Expression parameter : parameters) {
            RetExpression parRet = parameter.toIR(table, 0);
            ret.ir.append(parRet.ir);
            parametersList.add(new Pair<>(parRet.type.toLlvmType(), parRet.result));
        }
        Instruction callIns = new FunctionCallInstruction(0, "@" + fName, parametersList, tmpVar, fSymbol.getReturnType().toLlvmType());
        ret.ir.appendCode(callIns);
        Utils.resetCounter();
        Assertions.assertEquals(ret, fCall.toIR(table, 0));
    }

}
