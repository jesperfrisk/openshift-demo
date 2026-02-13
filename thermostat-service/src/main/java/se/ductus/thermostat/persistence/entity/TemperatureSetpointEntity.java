package se.ductus.thermostat.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "temperature_setpoint")
public class TemperatureSetpointEntity {

    @Id
    @Column(name = "temperature_sensor_id", length = 40)
    public String temperatureSensorId;

    @Column(name = "celsius", nullable = false)
    public float celsius;
}