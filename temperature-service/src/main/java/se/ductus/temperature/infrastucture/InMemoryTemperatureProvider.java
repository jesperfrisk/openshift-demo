package se.ductus.temperature.infrastucture;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import se.ductus.temperature.domain.Temperature;
import se.ductus.temperature.domain.TemperatureProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

@Singleton
@Named("InMemory")
public class InMemoryTemperatureProvider implements TemperatureProvider {

    private final float MAX_TEMPERATURE = 35f;
    private final float MIN_TEMPERATURE = -40;

    private final Temperature temperature;
    private boolean heating;

    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    public InMemoryTemperatureProvider() {
        this.temperature = new Temperature();
        this.temperature.setCelsius((float) (-30.0f + Math.random() * (30.0f - -30.0f)));

        Runnable task = () -> {
            float newTemp;

            if (heating) {
                newTemp = this.temperature.getCelsius() + 0.8f;
            }
            else {
                newTemp = this.temperature.getCelsius() - 0.4f;
            }

            if (newTemp >= MAX_TEMPERATURE) {
                newTemp = MAX_TEMPERATURE;
            }
            if (newTemp <= MIN_TEMPERATURE) {
                newTemp = MIN_TEMPERATURE;
            }

            this.temperature.setCelsius(newTemp);

        };

        scheduler.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public Temperature getCurrentTemperature() {
        return this.temperature;
    }

    @Override
    public void setHeating(boolean heating) {
        this.heating = heating;
    }


}
