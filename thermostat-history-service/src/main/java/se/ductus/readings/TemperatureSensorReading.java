package se.ductus.readings;

import java.util.Date;

public class TemperatureSensorReading {
    private String temperatureSensorId;
    private float celsius;
    private boolean heating;
    private Date readAt;

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

    public boolean isHeating() {
        return heating;
    }

    public void setHeating(boolean heating) {
        this.heating = heating;
    }

    public Date getReadAt() {
        return readAt;
    }

    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }
}
