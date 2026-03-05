package se.ductus.tempservice.spring.infrastructure.util;

import se.ductus.tempservice.spring.domain.Temperature;

public class TemperatureRandomizer {

    // Private constructor to prevent instantiation of static class
    private TemperatureRandomizer() {
    }

    public static Temperature randomizeTemperature() {
        Temperature temperature = new Temperature();
        float minTemp = -30.0f;
        float maxTemp = 30.0f;
        temperature.setCelsius(minTemp + (float) (Math.random() * (maxTemp - minTemp)));
        return temperature;
    }

}
