package se.ductus.tempservice.spring.application.mappers;

import se.ductus.tempservice.spring.application.dto.TemperatureResponseDto;
import se.ductus.tempservice.spring.domain.Temperature;

public class TemperatureMapper {

    public static TemperatureResponseDto toDto(Temperature temperature){
        TemperatureResponseDto temperatureResponseDto = new TemperatureResponseDto();
        temperatureResponseDto.setCelsius(temperature.getCelsius());
        return temperatureResponseDto;
    }
}
