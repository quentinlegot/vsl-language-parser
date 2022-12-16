package TP2.instruction;

import TP2.Utils;
import TP2.llvm.type.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class PrintInstruction extends AbstractIOInstruction {

    public PrintInstruction(int indent, Utils.LLVMStringConstant content, String varName, List<Pair<Type, String>> parameters) {
        super(indent, content, varName, parameters, "printf");
    }



}
