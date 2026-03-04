package se.ductus.thermostathistory.service;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import se.ductus.thermostathistory.model.TemperatureSensorReading;

@Path("temperature-sensor-reading")
@RegisterRestClient(configKey = "temperature-history")
public interface ThermostatHistoryService {
    @POST
    TemperatureSensorReading createTemperatureSensorReading(TemperatureSensorReading temperatureSensorReading);
}
