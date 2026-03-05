package se.ductus.tempservice.spring.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.ductus.tempservice.spring.application.dto.HeatingRequestDto;
import se.ductus.tempservice.spring.application.dto.TemperatureResponseDto;
import se.ductus.tempservice.spring.application.mappers.TemperatureMapper;
import se.ductus.tempservice.spring.application.services.TemperatureService;
import se.ductus.tempservice.spring.domain.Temperature;

@RestController
@RequestMapping("/temperature-sensor")
public class TemperatureController {

    @Autowired
    TemperatureService temperatureService;

    @GetMapping("/temperature")
    public TemperatureResponseDto temperature(){
        Temperature temperature = temperatureService.getCurrentTemperature();
        return TemperatureMapper.toDto(temperature);
    }

    @PutMapping("/heating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setHeating(@RequestBody HeatingRequestDto heatingRequestDto){
        temperatureService.setHeating(heatingRequestDto.heating);
    }
}
