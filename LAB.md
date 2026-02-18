# Quarkus and Openshift Lab

## Task 0: Deploy all Systems to OpenShift

The full setup and deployment instructions are provided in the [README](./README.md). Follow these instruction in order to start all systems in an openshift cluster.

## Task 1: Bug in Thermostat System

Your first real task is to find and fix a bug in the Thermostat system. These instructions are made for debugging using IntelliJ, so it is highly recommended to use IntelliJ as IDE.

### Setup Remote JVM Debug

This will allow you to debug your system that is running in your openshift cluster

1. Port forward the debug port to your machine
```
oc -n <your-project> port-forward deploy/thermostat 5005:5005
```
2. Configure IntelliJ Remote JVM Debug
    1. Run → Edit Configurations…
    2. Click + → Remote JVM Debug (or “Remote” depending on IntelliJ version)
    3. Set:
        - Host: localhost
        - Port: 5005
    4. Apply

3. Run the debugger on the profile you just created

### Thermostat System Bug

The current version of the Thermostat system is able to store and retrieve temperature setpoints. However, the temperature on the sensors falls to the MIN value regardless. 

You can confirm that setpoints exists by calling Thermostat endpoints in the swagger provided by the Thermostat system. Find the route which the Thermostat pod provides: 
```
oc get routes
```
Identify the Address for the Thermostat system and access the swagger at `https://<thermostat-address>/q/swagger-ui`

Furthermore, you can confirm that the current temperature of the sensors are dropping to MIN, by calling the Temperrature endpoints at `https://<temperature-sensor-address>/q/swagger-ui`

> **HINT** - *The problem with the Thermostat system is located in the `controlTemperature` method, place a breakpoint here to identify the problem.*

## Task 2: Upgrade the Temperature Sensor System

Currently the Temperature Sensor System is written with Spring. Your next task is to rewrite the current Temperature Sensor System with Quarkus. See [Quarkus docs - get started](https://quarkus.io/get-started/)

### Requirements

1. Implement the [Temperature Sensor api specification](./temperature-sensor-api-spec.yaml)

2. The system must provide a temperature value, (does not have to be an actual temperature, can be a variable that has a mock value).

3. The temperature value must change over some time inteval (the span of the time interval is not importand, nor the size of the change).
    1. When `heat` is on the temperature value must increase.
    2. When `heat` is off the temperature value must decrease.

4. Set a MAX and MIN value for the temperature, so that the temperature is within a realistic value.
