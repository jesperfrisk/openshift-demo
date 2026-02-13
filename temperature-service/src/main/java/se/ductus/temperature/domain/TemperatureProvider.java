package se.ductus.temperature.domain;

public interface TemperatureProvider {
    Temperature getCurrentTemperature();
    void setHeating(boolean heating);
}
