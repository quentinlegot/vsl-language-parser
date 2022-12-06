package TP2;

public class getTabPtrInstruction extends Instruction {

    private final String tmpVar;
    private final Llvm.Tab<?> type;
    private final String index;
    private final String name;

    public getTabPtrInstruction(int indent, String tmpVar, Llvm.Tab<?> type, String name, String index) {
        super(indent);
        this.tmpVar = tmpVar;
        this.type = type;
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        String tabType = "[" + type.getSize() + " x " + type.getInnerType()  + "]";
        return Utils.indent(indent) + tmpVar + " = getelementptr " + tabType + ", " + tabType + "* " + name + ", i64 0, i32 " + index + "\n";
    }
}
