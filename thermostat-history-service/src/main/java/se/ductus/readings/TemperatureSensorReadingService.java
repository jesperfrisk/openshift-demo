package se.ductus.readings;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TemperatureSensorReadingService {

    int MAX_SIZE = 100;
    List<TemperatureSensorReading> temperatureSensorReadings;

    public TemperatureSensorReadingService() {
        temperatureSensorReadings = new ArrayList<>();
    }

    public List<TemperatureSensorReading> getTemperatureSensorReadings() {
        return temperatureSensorReadings;
    }

    public TemperatureSensorReading createTemperatureSensorReading(TemperatureSensorReading temperatureSensorReading) {
        if (temperatureSensorReadings.size() > MAX_SIZE) {
            temperatureSensorReadings.remove(0);
        }
        temperatureSensorReadings.add(temperatureSensorReading);
        return temperatureSensorReading;
    }

}
