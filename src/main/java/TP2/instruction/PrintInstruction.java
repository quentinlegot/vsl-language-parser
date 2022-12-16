package TP2.instruction;

import TP2.Llvm;
import TP2.Utils;
import TP2.instruction.AbstractIOInstruction;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class PrintInstruction extends AbstractIOInstruction {

    public PrintInstruction(int indent, Utils.LLVMStringConstant content, String varName, List<Pair<Llvm.Type, String>> parameters) {
        super(indent, content, varName, parameters, "printf");
    }



}
