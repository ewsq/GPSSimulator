package faultInjection.fault_library.byte_manipulation;

public class ToggleBits implements BytePerturbationFunction {
    private double perturbedByte;

    public ToggleBits(Double var) {perturbedByte = ~Double.doubleToRawLongBits(var);}

    public ToggleBits(String var) {this(new Double(var));}

    @Override
    public double asDouble() {
        return this.perturbedByte;
    }

    @Override
    public String asString() {
        return Double.toString(perturbedByte);
    }

    @Override
    public long asLong() {
        return (long) perturbedByte;
    }
}
