package faultInjection.fault_library.perturbation_strategies;

import annotations.PerturbationFunction;
import faultInjection.fault_library.modes.PerturbationModes;
import gps.data.GPSData;

import java.util.concurrent.atomic.AtomicInteger;

@PerturbationFunction(PerturbationModes.SPOOFED_POSITION)
public final class SpoofedPositionStrategy extends AbstractPerturbationStrategy {

    private static final int ONE_HALF_SEC = 500;
    private double DASH_COORDINATES_FACTOR = 4711.0;
    private AtomicInteger counter = new AtomicInteger(0);
    private int retryCount = 100;

    public SpoofedPositionStrategy() {super(PerturbationModes.SPOOFED_POSITION);}

    public SpoofedPositionStrategy(int dashCoordsFactor) {
        super(PerturbationModes.SPOOFED_POSITION);
        this.DASH_COORDINATES_FACTOR = dashCoordsFactor;
    }

    public void setRetryCount(int count500Millis){
        this.retryCount = count500Millis;
    }

    @Override
    public synchronized void perturb() {
        if (GPSData.isStuck()) {
            // If the coords are stuck, it's senseless to spoof them => poll recursively till retry cnt is reached
            if(this.counter.get() <= retryCount){
                try {Thread.sleep(ONE_HALF_SEC);} catch (InterruptedException ignored) {}
                this.counter.incrementAndGet();
                this.perturb();
            }else{
                System.err.println("GPSData are still stuck ==> Cancel recursion");
            }
        }else
        {
            resetCounter();
            spoofGPSData();
            System.err.println("Spoofed the Coordinates by dashing with Factor: " + DASH_COORDINATES_FACTOR);
        }
    }

    private void spoofGPSData() {
        double lat = Double.parseDouble(GPSData.getLatitude()) + DASH_COORDINATES_FACTOR;
        double lng = Double.parseDouble(GPSData.getLongitude()) + DASH_COORDINATES_FACTOR;
        double alt = Double.parseDouble(GPSData.getAltitude()) + DASH_COORDINATES_FACTOR;

        GPSData.setLongitude(Double.toString(lng));
        GPSData.setLatitude(Double.toString(lat));
        GPSData.setAltitude(Double.toString(alt));
    }

    private void resetCounter() {
        this.counter.set(0);
    }
}
