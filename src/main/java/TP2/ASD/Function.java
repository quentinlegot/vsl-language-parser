package TP2.ASD;

import TP2.Llvm;

import java.util.List;

public class Function {

    private final String returnType;
    private final String name;
    private final List<String> parameters;
    private final Block block;

    public Function(String returnType, String name, List<String> parameters, Block block) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.block = block;
    }

    public String pp() {
        StringBuilder str = new StringBuilder("FUNC ").append(returnType).append(" ").append(name).append('(');
        for(int i = 0; i < parameters.size(); i++) {
            str.append(parameters.get(i));
            if(i < parameters.size() - 1) {
                str.append(", ");
            }
        }
        str.append(") {\n").append(block.pp()).append("}\n");
        return str.toString();
    }

    public Function.RetExpression toIR() {
        RetExpression blockRet = block.toIR();
        if(this.returnType.equals("INT")) {
            blockRet.type = new Int();
        } // else remain null
        blockRet.type = new Int();
        return blockRet;
    }

    public static class RetExpression {

        public Llvm.IR ir;
        public Type type;
        public String result;

        public RetExpression(Llvm.IR ir, Type type, String result) {
            this.ir = ir;
            this.type = type;
            this.result = result;
        }

    }
}
