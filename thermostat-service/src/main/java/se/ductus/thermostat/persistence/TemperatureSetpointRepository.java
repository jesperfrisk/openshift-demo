package se.ductus.thermostat.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import se.ductus.thermostat.model.TemperatureSetpoint;
import se.ductus.thermostat.persistence.entity.TemperatureSetpointEntity;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TemperatureSetpointRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void updateTemperatureSetpoint(TemperatureSetpoint temperatureSetpoint) {
        TemperatureSetpointEntity foundSetpoint  = em.find(TemperatureSetpointEntity.class, temperatureSetpoint.temperatureSensorId);
        if (foundSetpoint == null) {
            TemperatureSetpointEntity temperatureSetpointEntity = new TemperatureSetpointEntity();
            temperatureSetpointEntity.temperatureSensorId = temperatureSetpoint.temperatureSensorId;
            temperatureSetpointEntity.celsius = temperatureSetpoint.celsius;
            em.persist(temperatureSetpointEntity);
        }
        else {
            foundSetpoint.celsius = temperatureSetpoint.celsius;
        }
    }

    public TemperatureSetpoint getTemperatureSetpoint(String temperatureSensorId) throws NotFoundException {
        TemperatureSetpointEntity temperatureSetpointEntity = em.find(TemperatureSetpointEntity.class, temperatureSensorId);
        if (temperatureSetpointEntity == null) {
            throw new NotFoundException();
        }
        return new TemperatureSetpoint(
                temperatureSetpointEntity.temperatureSensorId,
                temperatureSetpointEntity.celsius
        );
    }

    public List<TemperatureSetpoint> getTemperatureSetpoints() {

        Query query = em.createQuery("SELECT t FROM TemperatureSetpointEntity t");
        List<TemperatureSetpointEntity> temperatureSetpointEntities = query.getResultList();
        List<TemperatureSetpoint> temperatureSetpoints = new ArrayList<>();
        for (TemperatureSetpointEntity temperatureSetpointEntity : temperatureSetpointEntities) {
            temperatureSetpoints.add(
                    new TemperatureSetpoint(
                            temperatureSetpointEntity.temperatureSensorId,
                            temperatureSetpointEntity.celsius
                    )
            );
        }
        return temperatureSetpoints;
    }
}
