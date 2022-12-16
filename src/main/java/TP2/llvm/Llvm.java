package TP2.llvm;

import TP2.instruction.Instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This file contains a simple LLVM IR representation
 * and methods to generate its string representation
 */
public class Llvm {

    /**
     * Returns a new empty list of instruction, handy
     */
    public static List<Instruction> empty() {
        return new ArrayList<>();
    }

    public static class IR {
        /**
         *  IR instructions to be placed before the code (global definitions)
         */
        List<Instruction> header;
        /**
         *  IR composing the main code
         */
        List<Instruction> code;

        public IR() {
            this.header = Llvm.empty();
            this.code = Llvm.empty();
        }

        /**
         * Creates an IR based on two lists of instructions
         * @param header the instructions to be placed before the code
         * @param code the main code
         */
        public IR(List<Instruction> header, List<Instruction> code) {
            this.header = header;
            this.code = code;
        }

        /**
         * Append two IR.
         * Append the header of the second to the first, and idem for the code
         * @param other the other IR
         * @return
         */
        public IR append(IR other) {
            header.addAll(other.header);
            code.addAll(other.code);
            return this;
        }

        /**
         * Append an instruction to the IR's code
         * @param inst the instruction to append
         * @return
         */
        public IR appendCode(Instruction inst) {
            code.add(inst);
            return this;
        }

        /**
         * Append an instruction to the IR's header
         * @param inst the instruction to append
         * @return
         */
        public IR appendHeader(Instruction inst) {
            header.add(inst);
            return this;
        }

        public String toString() {
            // This header describe to LLVM the target
            // and declare the external function printf
            StringBuilder r = new StringBuilder("; Target\n" +
                    "target triple = \"x86_64-unknown-linux-gnu\"\n" +
                    "; External declaration of the printf function\n" +
                    "declare i32 @printf(i8* noalias nocapture, ...)\n" +
                    "declare i32 @scanf(i8* noalias nocapture, ...)\n" +
                    "\n; Actual code begins\n\n");

            for(Instruction inst: header)
                r.append(inst);

            r.append("\n\n");

            for(Instruction inst: code)
                r.append(inst);

            return r.toString();
        }

        /**
         * As toString is used to display final result(should not) instead of debug method,
         * I create a "debug" method to replace it
         * @return
         */
        public String debug() {
            return "header=" + Arrays.toString(header.toArray()) + "],code=[" + Arrays.toString(code.toArray()) + "];";
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this)
                return true;
            if(obj == null)
                return false;
            if(!(obj instanceof IR))
                return false;
            IR o = (IR) obj;
            return this.code.equals(o.code) && this.header.equals(o.header);
        }
    }


}
