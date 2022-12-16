package TP2.ASD.operation;

import TP2.ASD.expression.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.expression.operation.AddExpression;
import TP2.ASD.expression.operation.IntegerExpression;
import TP2.instruction.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.instruction.operation.Add;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddExpressionTest {

    private Expression left, right;
    private AddExpression expression;

    @BeforeEach
    public void init() {
        Utils.resetCounter();
        left = new IntegerExpression(4);
        right = new IntegerExpression(6);
        expression = new AddExpression(left, right);
    }

    @Test
    void testPp() {
        assertEquals("(4 + 6)", expression.pp());
    }

    @Test
    void testToIR() throws TypeException {
        SymbolTable table = new SymbolTable();
        RetExpression leftRet = left.toIR(table, 0);
        RetExpression rightRet = right.toIR(table, 0);
        leftRet.ir.append(rightRet.ir);
        String result = "%tmp1"; // We don't use Utils.newTmp(); because it'll increase the counter and test will fail each time
        Instruction ins = new Add(0, leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);
        leftRet.ir.appendCode(ins);
        RetExpression finalRet = new RetExpression(leftRet.ir, leftRet.type, result);
        assertEquals(finalRet, expression.toIR(table, 0));
    }

}
