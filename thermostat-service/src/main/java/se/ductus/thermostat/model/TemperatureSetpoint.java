package se.ductus.thermostat.model;

public class TemperatureSetpoint {
    public String temperatureSensorId;
    public float celsius;

    public TemperatureSetpoint(String temperatureSensorId, float celsius) {
        this.temperatureSensorId = temperatureSensorId;
        this.celsius = celsius;
    }
}
