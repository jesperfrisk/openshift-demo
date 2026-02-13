package se.ductus.temperature.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import se.ductus.temperature.api.dto.HeatingRequest;
import se.ductus.temperature.api.dto.TemperatureResponse;
import se.ductus.temperature.api.mapper.TemperatureMapper;
import se.ductus.temperature.application.TemperatureService;
import se.ductus.temperature.domain.Temperature;

@Path("/temperature-sensor")
@Produces(MediaType.APPLICATION_JSON)
public class TemperatureResource {

    @Inject
    TemperatureService temperatureService;

    @Path("/temperature")
    @GET
    public TemperatureResponse temperature() {
        Temperature temperature = temperatureService.getCurrentTemperature();
        return TemperatureMapper.temperatureToTemperatureResponse(temperature);
    }

    @Path("/heating")
    @PUT
    public void setHeating(HeatingRequest heatingRequest) {
        temperatureService.setHeating(heatingRequest.heating);
    }
}
