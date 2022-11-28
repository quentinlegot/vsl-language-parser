package TP2;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class ReadInstruction extends AbstractIOInstruction {

    public ReadInstruction(int indent, Utils.LLVMStringConstant content, String varName, Pair<Llvm.Type, String> var) {
        super(indent, content, varName, List.of(var), "scanf");
    }
}
