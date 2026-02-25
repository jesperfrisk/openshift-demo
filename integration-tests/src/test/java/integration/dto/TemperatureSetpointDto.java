package integration.dto;

public class TemperatureSetpointDto {
    private String temperatureSensorId;
    private float celsius;

    public TemperatureSetpointDto() {

    }

    public String getTemperatureSensorId() {
        return temperatureSensorId;
    }

    public void setTemperatureSensorId(String temperatureSensorId) {
        this.temperatureSensorId = temperatureSensorId;
    }

    public float getCelsius() {
        return celsius;
    }

    public void setCelsius(float celsius) {
        this.celsius = celsius;
    }
}
