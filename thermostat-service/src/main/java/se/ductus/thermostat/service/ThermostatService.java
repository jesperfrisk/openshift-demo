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
import java.util.Date;
import java.util.List;
import io.quarkus.scheduler.Scheduled;
import se.ductus.thermostathistory.model.TemperatureSensorReading;
import se.ductus.thermostathistory.service.ThermostatHistoryService;

@Singleton
public class ThermostatService {

    @Inject
    TemperatureSetpointRepository temperatureSetpointRepository;

    @Inject
    @RestClient
    TemperatureService temperatureService;

    @Inject
    @RestClient
    ThermostatHistoryService thermostatHistoryService;

    @Inject
    @ConfigProperty(name = "se.ductus.thermostat.temperature-sensors")
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

            Heating heating = new Heating(false);
            if (temperature.celsius < temperatureSetpoint.celsius) {
                heating.heating = true;
            }
            temperatureService.setHeating(temperatureSetpoint.temperatureSensorId, heating);

            TemperatureSensorReading temperatureSensorReading = new TemperatureSensorReading();
            temperatureSensorReading.setTemperatureSensorId(temperatureSetpoint.temperatureSensorId);
            temperatureSensorReading.setCelsius(temperature.celsius);
            temperatureSensorReading.setHeating(heating.heating);
            temperatureSensorReading.setReadAt(new Date());
            thermostatHistoryService.createTemperatureSensorReading(temperatureSensorReading);

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
