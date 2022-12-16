package TP2.instruction;

import TP2.Llvm;
import TP2.Utils;
import TP2.instruction.AbstractIOInstruction;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class ReadInstruction extends AbstractIOInstruction {

    public ReadInstruction(int indent, Utils.LLVMStringConstant content, String varName, Pair<Llvm.Type, String> var) {
        super(indent, content, varName, List.of(var), "scanf");
    }
}
