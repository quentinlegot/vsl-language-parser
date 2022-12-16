package TP2.ASD.function;

import TP2.ASD.expression.BlockExpression;
import TP2.ASD.expression.Expression;
import TP2.ASD.expression.ReturnExpression;
import TP2.ASD.expression.SimpleDeclareExpression;
import TP2.ASD.expression.function.FunctionExpression;
import TP2.ASD.expression.io.ReadExpression;
import TP2.ASD.expression.operation.VariableExpression;
import TP2.ASD.type.Int;
import TP2.ASD.type.Type;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionExpressionTest {

    private String fName;
    private LinkedList<Pair<Type, String>> parameters;
    private BlockExpression content;
    private FunctionExpression func1;

    @BeforeEach
    void init() {
        this.fName = "fact";
        Pair<Type, String> par1 = new Pair<>(new Int(), "par1");
        Pair<Type, String> par2 = new Pair<>(new Int(), "par2");
        this.parameters = new LinkedList<>();
        parameters.add(par1);
        parameters.add(par2);
        List<Expression> blockContent = new ArrayList<>();
        blockContent.add(new SimpleDeclareExpression(new Int(), "var1", new LinkedList<>(), null));
        LinkedList<Pair<String, Expression>> readArr = new LinkedList<>();
        readArr.add(new Pair<>("var1", null));
        blockContent.add(new ReadExpression(readArr));
        blockContent.add(new ReturnExpression(new VariableExpression("var1")));
        this.content = new BlockExpression(blockContent);
        this.func1 = new FunctionExpression(new Int(), fName, parameters, content);
    }

    @Test
    void testPp() {
        Assertions.assertEquals("FUNC INT fact(INT par1, INT par2)\n{\n"+
                "INT %var1\n" +
                "READ %var1\n" +
                "RETURN %var1\n" +
                "}\n", func1.pp());
    }

    // TODO test IR

}
