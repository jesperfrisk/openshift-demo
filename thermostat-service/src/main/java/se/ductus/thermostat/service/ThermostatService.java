package se.ductus.thermostat.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import se.ductus.temperaturesensor.model.Heating;
import se.ductus.temperaturesensor.model.Temperature;
import se.ductus.temperaturesensor.service.TemperatureService;
import se.ductus.thermostat.model.TemperatureSetpoint;
import se.ductus.thermostat.persistence.TemperatureSetpointRepository;

import java.util.ArrayList;
import java.util.List;
import io.quarkus.scheduler.Scheduled;

@Singleton
public class ThermostatService {

    @Inject
    TemperatureSetpointRepository temperatureSetpointRepository;

    @Inject
    @RestClient
    TemperatureService temperatureService;

    @Inject
    @ConfigProperty(name = "se.ductus.themostat.temperature-sensors")
    List<String> temperatureSensors;

    void onStart(@Observes StartupEvent ev) {
        for (String temperatureSensor : temperatureSensors) {
            try {
                this.getSetpoint(temperatureSensor);
            }
            catch (NotFoundException ignored) {
                this.updateSetpoint(new TemperatureSetpoint(temperatureSensor, 0));
            }
        }
    }

    @Scheduled(every = "1s")
    void controlTemperature() {
        List<TemperatureSetpoint> temperatureSetpoints = new ArrayList<>();
        for (String temperatureSensor : temperatureSensors) {
            try {
                TemperatureSetpoint temperatureSetpoint= this.getSetpoint(temperatureSensor);
                temperatureSetpoints.add(temperatureSetpoint);
            }
            catch (NotFoundException ignored) {

            }
        }
        for (TemperatureSetpoint temperatureSetpoint : temperatureSetpoints) {
            Temperature temperature = temperatureService.getTemperature(temperatureSetpoint.temperatureSensorId);
            if (temperature.celsius < temperatureSetpoint.celsius) {
                temperatureService.setHeating(temperatureSetpoint.temperatureSensorId, new Heating(true));
            }
            if (temperature.celsius > temperatureSetpoint.celsius) {
                temperatureService.setHeating(temperatureSetpoint.temperatureSensorId, new Heating(false));
            }

        }

    }

    public void updateSetpoint(TemperatureSetpoint temperatureSetpoint) {
        if (!temperatureSensors.contains(temperatureSetpoint.temperatureSensorId)) {
            throw new NotFoundException();
        }
        temperatureSetpointRepository.updateTemperatureSetpoint(temperatureSetpoint);
    }

    public TemperatureSetpoint getSetpoint(String temperatureSensorId) throws NotFoundException {
        return temperatureSetpointRepository.getTemperatureSetpoint(temperatureSensorId);
    }

    public List<TemperatureSetpoint> getSetpoints() {
        return temperatureSetpointRepository.getTemperatureSetpoints();
    }
}
