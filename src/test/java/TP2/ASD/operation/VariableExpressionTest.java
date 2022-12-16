package TP2.ASD.operation;

import TP2.ASD.RetExpression;
import TP2.ASD.expression.operation.IntegerExpression;
import TP2.ASD.expression.operation.VariableExpression;
import TP2.ASD.type.Int;
import TP2.ASD.type.Tab;
import TP2.*;
import TP2.instruction.Instruction;
import TP2.instruction.operation.LoadVariable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VariableExpressionTest {

    private String name1;
    private String name2;
    private IntegerExpression tabContent2;
    private VariableExpression varExpression1;
    private VariableExpression varExpression2;

    @BeforeEach
    void init() {
        this.name1 = "var1";
        this.name2 = "var2";
        this.tabContent2 = new IntegerExpression(5);
        this.varExpression1 = new VariableExpression(name1);
        this.varExpression2 = new VariableExpression(name2, tabContent2);
    }

    @Test
    void testPp() {
        Assertions.assertEquals("%" + name1, varExpression1.pp());
        Assertions.assertEquals("%" + name2 + "[" + 5 + "]", varExpression2.pp());
    }

    @Test
    void testToIR() throws TypeException {
        SymbolTable table = new SymbolTable();
        String tmpVar = Utils.newtmp();
        RetExpression ret = new RetExpression(new Llvm.IR(), new Int(), tmpVar);
        table.add(new SymbolTable.VariableSymbol(new Int(), "%" + name1));
        Instruction variable1Load = new LoadVariable(0, tmpVar, new Llvm.Int(), "%" + name1);
        ret.ir.appendCode(variable1Load);
        Utils.resetCounter();
        Assertions.assertEquals(ret, varExpression1.toIR(table, 0));

        Utils.resetCounter();
        SymbolTable table2 = new SymbolTable();
        String tmpVar2 = Utils.newtmp();
        RetExpression ret2 = new RetExpression(new Llvm.IR(), new Int(), tmpVar2);
        table2.add(new SymbolTable.VariableSymbol(new Tab<>(new Int(), 5), "%" + name2));
        RetExpression index = tabContent2.toIR(table2, 0);
        ret2.ir.append(index.ir);
        Instruction variable2Load = new LoadVariable(0, tmpVar2, new Llvm.Tab<>(new Llvm.Int(), 5), "%" + name2, index.result);
        ret2.ir.appendCode(variable2Load);
        Utils.resetCounter();
        Assertions.assertEquals(ret2, varExpression2.toIR(table2, 0));

    }
}
