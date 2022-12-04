package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.IntegerExpression;
import TP2.ASD.RetExpression;
import TP2.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.operation.Sub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinusExpressionTest {

    private Expression left, right;
    private MinusExpression expression;

    @BeforeEach
    public void init() {
        Utils.resetCounter();
        left = new IntegerExpression(4);
        right = new IntegerExpression(6);
        expression = new MinusExpression(left, right);
    }

    @Test
    void testPp() {
        assertEquals("(4 - 6)", expression.pp());
    }

    @Test
    void testToIR() throws TypeException {
        SymbolTable table = new SymbolTable();
        RetExpression leftRet = left.toIR(table, 0);
        RetExpression rightRet = right.toIR(table, 0);
        leftRet.ir.append(rightRet.ir);
        String result = "%tmp1"; // We don't use Utils.newTmp(); because it'll increase the counter and test will fail each time
        Instruction ins = new Sub(0, leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);
        leftRet.ir.appendCode(ins);
        RetExpression finalRet = new RetExpression(leftRet.ir, leftRet.type, result);
        assertEquals(finalRet, expression.toIR(table, 0));
    }
}
