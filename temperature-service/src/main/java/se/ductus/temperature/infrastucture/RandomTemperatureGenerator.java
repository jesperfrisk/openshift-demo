package se.ductus.temperature.infrastucture;

import jakarta.enterprise.context.ApplicationScoped;
import se.ductus.temperature.domain.Temperature;
import se.ductus.temperature.domain.TemperatureProvider;

@ApplicationScoped
public class RandomTemperatureGenerator implements TemperatureProvider {
    @Override
    public Temperature getCurrentTemperature() {
        Temperature temperature = new Temperature();
        temperature.setCelsius((float) (-30.0f + Math.random() * (30.0f - -30.0f)));
        return temperature;
    }

    @Override
    public void setHeating(boolean heating) {

    }
}
