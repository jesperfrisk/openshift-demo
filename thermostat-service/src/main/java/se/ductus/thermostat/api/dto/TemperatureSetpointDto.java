package se.ductus.thermostat.api.dto;

public class TemperatureSetpointDto {
    public String temperatureSensorId;
    public float celsius;

    public TemperatureSetpointDto(String temperatureSensorId, float celsius) {
        this.temperatureSensorId = temperatureSensorId;
        this.celsius = celsius;
    }
}
