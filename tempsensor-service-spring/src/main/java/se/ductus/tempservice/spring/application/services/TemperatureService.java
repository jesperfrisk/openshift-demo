package se.ductus.tempservice.spring.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import se.ductus.tempservice.spring.domain.Temperature;
import se.ductus.tempservice.spring.domain.TemperatureProvider;

@Service
public class TemperatureService {
    @Autowired
    @Qualifier("InMemory")
    TemperatureProvider temperatureProvider;

    public Temperature getCurrentTemperature() {
        return temperatureProvider.getCurrentTemperature();
    }

    public void setHeating(boolean heating) {
        temperatureProvider.setHeating(heating);
    }

}
