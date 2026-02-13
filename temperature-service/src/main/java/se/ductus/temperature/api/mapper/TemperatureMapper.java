package se.ductus.temperature.api.mapper;

import se.ductus.temperature.api.dto.TemperatureResponse;
import se.ductus.temperature.domain.Temperature;

public class TemperatureMapper {
    public static TemperatureResponse temperatureToTemperatureResponse(Temperature temperature) {
        TemperatureResponse temperatureResponse = new TemperatureResponse();
        temperatureResponse.setCelsius(temperature.getCelsius());
        return temperatureResponse;
    }
}
