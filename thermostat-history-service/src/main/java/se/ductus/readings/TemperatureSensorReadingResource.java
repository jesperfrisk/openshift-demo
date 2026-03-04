package se.ductus.readings;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/temperature-sensor-reading")
@Produces(MediaType.APPLICATION_JSON)
public class TemperatureSensorReadingResource {

    @Inject
    TemperatureSensorReadingService temperatureSensorReadingService;

    @GET
    public List<TemperatureSensorReading> getTemperatureSensorReadings() {
        return temperatureSensorReadingService.getTemperatureSensorReadings();
    }

    @POST
    public TemperatureSensorReading createTemperatureSensorReading(TemperatureSensorReading temperatureSensorReading) {
        return temperatureSensorReadingService.createTemperatureSensorReading(temperatureSensorReading);
    }
}
