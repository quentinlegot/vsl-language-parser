package TP2;

public class Affect extends Instruction {

    private final String ident;
    private final String index;
    Llvm.Type type;
    String value;

    /**
     * The return instruction
     * @param type type of the return value
     * @param value value to be returned
     */
    public Affect(Llvm.Type type, String ident, String value, String index) {
        this.type = type;
        this.ident = ident;
        this.value = value;
        this.index = index;
    }

    @Override
    public String toString() {
        if(type instanceof Llvm.Tab) {
            Llvm.Type innerType = ((Llvm.Tab<?>) type).getInnerType();
            String tmpVar = Utils.newtmp();
            String indexVar;
            Object tabType =  "[" + type.getSize() + " x " + innerType + "]";
            String str = "";
            if(index.startsWith("%")) { // not a IntegerExpression
                String[] lines = index.split("\n");
                indexVar = lines[lines.length - 1].split(" ")[0];
            } else {
                indexVar = index;
            }
            str += tmpVar + " = getelementptr " + tabType + ", " + tabType + "* " + ident + ", i64 0, i32 " + indexVar + "\n";
            str += "store " + innerType.toString() + " " + value + ", " + type.toString() + " " + tmpVar + "\n";
            return str;
        } else {
            return "store " + type.toString() + " " +  value + ", " + type.toString() + "* " + ident + "\n";
        }

    }
}
