package faultInjection.pertubation.perturbation_functions;

import faultInjection.pertubation.perturbation_functions.down_counter.CallbackCountDownBuilder;
import faultInjection.pertubation.perturbation_functions.perturbation_strategies.AbstractPerturbationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerturbationBuilder {
    private List<AbstractPerturbationStrategy> strategies = new ArrayList<>();
    private Random random = new Random();
    private boolean useRandomness;
    private int count = 5;
    private int period = 1000;
    private int delay = 0;

    public PerturbationBuilder addStrategy(AbstractPerturbationStrategy strategy){
        this.strategies.add(strategy);
        return this;
    }

    public PerturbationBuilder presetCountInSeconds(int count){
        this.count = count;
        return this;
    }

    public PerturbationBuilder setTimerPeriod(int period){
        this.period = period;
        return this;
    }

    public PerturbationBuilder setTimerDelay(int delay){
        this.delay = delay;
        return  this;
    }

    public PerturbationBuilder useRandomnessForConfiguration(){
        useRandomness = true;
        return this;
    }

    public void build(){
        if(useRandomness){
            this.count += random.nextInt(4);
            this.period += random.nextInt(1000);
            this.delay += random.nextInt(3000);
        }

        strategies.forEach(strategy -> new CallbackCountDownBuilder().registerEvent(strategy)
                                                                     .presetCountInSeconds(count)
                                                                     .setTimerDelay(delay)
                                                                     .setTimerPeriod(period)
                                                                     .startCountDown());
    }
}
