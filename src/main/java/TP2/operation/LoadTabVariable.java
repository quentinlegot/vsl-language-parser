package TP2.operation;

import TP2.Llvm;
import TP2.Utils;

public class LoadTabVariable extends LoadVariable {

    private final String tabContent;
    private final int sizeTab;

    public LoadTabVariable(int indent, String toTmp, Llvm.Type type, String name, String tabContent, int sizeTab) {
        super(indent, toTmp, type, name);
        this.tabContent = tabContent;
        this.sizeTab = sizeTab;
    }

    @Override
    public String toString() {
        String tabType = "[" + sizeTab + " x " + type + "]";
        String str = Utils.indent(indent) + toTmp + " = getelementptr " + tabType + ", " + tabType + "* " + name + ", i64 0, i32 " + tabContent + "\n";
        return str + super.toString();
    }
}
