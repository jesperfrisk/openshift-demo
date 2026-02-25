Feature: Is temperature within setpoint

    Scenario: An existing temperature setpoint in the thermostat service, should result in control of temperature in that sensor
         Given Temperature setpoint for sensor "temperature-sensor-1"
         When I fetch temperature
         Then Temperature sensor responds with temperature object
         And the given temperature value is within plus/minus 2.5 degrees of setpoint