package se.ductus.tempservice.spring.infrastructure;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.ductus.tempservice.spring.domain.Temperature;
import se.ductus.tempservice.spring.domain.TemperatureProvider;
import se.ductus.tempservice.spring.infrastructure.util.TemperatureRandomizer;


@Component("InMemory")
@EnableScheduling
public class InMemoryTemperatureProvider implements TemperatureProvider {

    private final Temperature temperature;
    private boolean heating;

    public InMemoryTemperatureProvider() {
        this.temperature = TemperatureRandomizer.randomizeTemperature();
    }

    // Will execute once every second
    @Scheduled(fixedRate = 1000)
    void alterTemperature() {

        float MAX_TEMPERATURE = 35f;
        float MIN_TEMPERATURE = -40;

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
