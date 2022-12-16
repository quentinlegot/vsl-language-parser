package TP2.instruction;

import TP2.Utils;
import TP2.llvm.type.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class ReadInstruction extends AbstractIOInstruction {

    public ReadInstruction(int indent, Utils.LLVMStringConstant content, String varName, Pair<Type, String> var) {
        super(indent, content, varName, List.of(var), "scanf");
    }
}
