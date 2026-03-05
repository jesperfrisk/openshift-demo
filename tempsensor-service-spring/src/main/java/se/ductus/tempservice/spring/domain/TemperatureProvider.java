package se.ductus.tempservice.spring.domain;

public interface TemperatureProvider {
    Temperature getCurrentTemperature();
    void setHeating(boolean heating);
}
