package se.ductus.temperaturesensor.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import se.ductus.temperaturesensor.model.Heating;
import se.ductus.temperaturesensor.model.Temperature;

@Path("temperature-sensor/{temperatureSensorId}")
@RegisterRestClient(configKey = "temperature-sensor")
public interface TemperatureService {

    @GET
    @Path("/temperature")
    Temperature getTemperature(@PathParam("temperatureSensorId") String temperatureSensorId);

    @PUT
    @Path("/heating")
    void setHeating(@PathParam("temperatureSensorId") String temperatureSensorId, Heating heating);
}
