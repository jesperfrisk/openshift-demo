package integration.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import integration.dto.TemperatureDto;
import integration.dto.TemperatureSetpointDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TemperatureSensorSteps {

    private String temperatureSensorId;

    private TemperatureSetpointDto givenTemperatureSetpointDto;

    private HttpResponse<String> temperatureSensorFetchTemperatureResponse;

    private TemperatureDto givenTemperatureDto;

    @Given("Temperature setpoint for sensor {string}")
    public void temperatureSetpoint(String temperatureSensorId) {
        this.temperatureSensorId = temperatureSensorId;

        String defaultUri = System.getenv("DEFAULT_THERMOSTAT_URI");
        String uri = String.format("%s/thermostat/setpoint/%s", defaultUri, temperatureSensorId);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            givenTemperatureSetpointDto = mapper.readValue(response.body(), TemperatureSetpointDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I fetch temperature")
    public void fetchTemperature() {
        HttpClient client = HttpClient.newHttpClient();

        String defaultUri = System.getenv("DEFAULT_TEMPERATURE_SENSOR_URI");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/temperature-sensor/%s/temperature", defaultUri, temperatureSensorId)))
                .GET()
                .build();

        try {
            temperatureSensorFetchTemperatureResponse =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("Temperature sensor responds with temperature object")
    public void temperatureSensorResponse() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            givenTemperatureDto = mapper.readValue(temperatureSensorFetchTemperatureResponse.body(), TemperatureDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("the given temperature value is within plus\\/minus {float} degrees of setpoint")
    public void temperatureWithinSetpoint(float marginOfError) {
        float temperatureDiff = Math.abs(givenTemperatureSetpointDto.getCelsius() - givenTemperatureDto.getCelsius());

        Assert.assertTrue(temperatureDiff <= marginOfError);
    }
}
