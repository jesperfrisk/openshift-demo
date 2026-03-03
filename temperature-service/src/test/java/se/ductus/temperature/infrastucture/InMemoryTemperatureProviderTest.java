package se.ductus.temperature.infrastucture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.ductus.temperature.domain.Temperature;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTemperatureProviderTest {

    private InMemoryTemperatureProvider inMemoryTemperatureProvider;

    @BeforeEach
    public  void setup() {
        inMemoryTemperatureProvider = new InMemoryTemperatureProvider();
    }

    @Test
    void alterTemperatureHeatingOff() {
        Temperature temperature = inMemoryTemperatureProvider.getCurrentTemperature();
        float initCelsius = temperature.getCelsius();

        inMemoryTemperatureProvider.setHeating(false);
        inMemoryTemperatureProvider.alterTemperature();

        assertTrue(initCelsius > temperature.getCelsius());
    }

    @Test
    void alterTemperatureHeatingOn() {
        Temperature temperature = inMemoryTemperatureProvider.getCurrentTemperature();
        float initCelsius = temperature.getCelsius();

        inMemoryTemperatureProvider.setHeating(true);
        inMemoryTemperatureProvider.alterTemperature();

        assertTrue(initCelsius < temperature.getCelsius());
    }

}