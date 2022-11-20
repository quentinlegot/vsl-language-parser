package TP2;

public class EndBlock extends Instruction {

    String value;

    public EndBlock(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "}";
    }
}
