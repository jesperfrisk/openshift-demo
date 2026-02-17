package se.ductus.temperature.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import se.ductus.temperature.domain.Temperature;
import se.ductus.temperature.domain.TemperatureProvider;

@ApplicationScoped
public class TemperatureService {
    @Inject
    @Named("InMemory")
    TemperatureProvider temperatureProvider;

    public Temperature getCurrentTemperature() {
        return temperatureProvider.getCurrentTemperature();
    }

    public void setHeating(boolean heating) {
        temperatureProvider.setHeating(heating);
    }
}
