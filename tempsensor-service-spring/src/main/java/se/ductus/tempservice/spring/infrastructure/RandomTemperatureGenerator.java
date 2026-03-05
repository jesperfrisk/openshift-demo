package se.ductus.tempservice.spring.infrastructure;

import org.springframework.stereotype.Component;
import se.ductus.tempservice.spring.domain.Temperature;
import se.ductus.tempservice.spring.domain.TemperatureProvider;
import se.ductus.tempservice.spring.infrastructure.util.TemperatureRandomizer;

@Component("Random")
public class RandomTemperatureGenerator implements TemperatureProvider {

    @Override
    public Temperature getCurrentTemperature() {
        return TemperatureRandomizer.randomizeTemperature();
    }

    @Override
    public void setHeating(boolean heating) {
    }
}
