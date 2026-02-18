#  TEST
Ductus demo project for: Quarkus and OpenShift

101-Tutorial for setting up a Quarkus application running either docker (locally) or on an OpenShift cluster through OpenShift local (CRC). Meant as a demo project to familiarize oneself with the mentioned tools.

We suggest that you fork this repo and learn it in your own environment, if you think that this demo should be updated in some way with new information during your demo-work, please clone this repo and create a PR or simply add an issue on github [Link]. 

**Branches:**
 
 * **main**: Represents the final state of the demo, showing one possible implementation, serving as a reference solution (which of course can be improved).
 * **demo**: The branch we created for you to start in.


## Requirements
* Git installed on your machine
* A fork of this repository
* Java 17, you can for example use a JDK from [Adoptium - Temurin](https://adoptium.net/temurin/releases)
* Docker, you can install it from Microsoft Store or from [Docker's Install Page](https://docs.docker.com/engine/install/)
* RedHat developer account (more on this later)
* Hardware: 32GB RAM and at least 80GB storage available for CRC (you can do it with less resources but more on this later)

## Systems

The project is constructed of two quarkus apps and a mysql database. See [Quarkus docs - get started](https://quarkus.io/get-started/) for more info.

### Temperature Sensor

The temperature sensor systyem has a mocked temperature value and provides one endpoint for fetching the current temperature and another for setting "heat", thus increasing the temperature. If "heat" is off the temperature drops.

### Thermostat

The thermostat system controls one or multiple temperature sensors. This system provides an endpoint for setting temperature setpoints for each sensor, it also provides endpoints for fetching these setpoints. The thermostat system will control each temperature sensor that it has a setpoint for, by featching that sensors current temperature and then turning on "heat" if the temperature is to low and turning off "heat" if its to high.

Each setpoint is stored in an MySql Database. Furthermore, this system uses the Flyway database migration tool in order to set up the database. See [Using Flyway](quarkus.io/guides/flyway) 

## Setup
Before diving into deploying the application and database via OpenShift, you should first learn how to deploy containers locally (e.g. via Docker). When moving to OpenShift, which basically is a commercialized Kubernetes system that depends on containers, having some understanding of containerization is beneficial. If you are already familiar with running applications in docker etc, you can skip to [Setting Up Via OpenShift](#Setting-Up-Via-OpenShift) that continues with how to deploy the app via kubernetes manifests to the OpenShift cluster.

## Setting up via Docker (Pre-OpenShift)

Each quarkus app have a Dockerfile in its root that defines how the image will be built. You can build and run each app individually, [building Docker images](https://docs.docker.com/get-started/docker-concepts/building-images/build-tag-and-publish-an-image/)

The esiest way to run the project in docker is to use the docker-compose file which creates the database, one thermostat and two temperature sensor systems. Run this in the root of the project where the `docker-compose.yaml` exists:
```
docker compose up -d --build
```


## Setting Up Via OpenShift
There are two options to use openshift without committing to a enterprise plan, first you can use RedHad Developer Sandbox and its 30-day trial. But we recommend that you run OpenShift local (CRC), this comes with the a limitation of only being able to have one cluster of pods running but its enough for this demo. 

1. 
    If you have not create a RedHat developer account you should do so now [RedHat - Register](https://sso.redhat.com).

2.
    Follow the [Install OpenShift Local Guide](https://crc.dev/docs/installing/), this will ask you to go to the [Download Page](https://console.redhat.com/openshift/create/local) where you can find the installation files along with your **pull secret** that is needed later to link your local VM to your account. 
    
    **IMPORTANT**, set your VMs resource config before you run `crc setup`, since insufficient RAM or storage can lead to unstable pod deploys. If you need to change the VMs disk size you will need to delete the current one and init a new one so its better to set a generous config from start if you have the resources, it is really easy to delete from ur system later on with `crc delete`. Run the default config command mentioned in the [Using CRC Docs](https://crc.dev/docs/using/) or the commands below for the our suggested config: 

    ```
    crc config set memory 16384
    crc config set cpus 6
    crc config set disk-size 80
    ```

3. 
    When installed you can follow the [Using CRC Docs](https://crc.dev/docs/using/) on how to use the system that manages ur local cluster. 

    When you get to starting the cluster with `crc start` you will need the pull secret from earlier (go to [Download Page](https://console.redhat.com/openshift/create/local) and retrieve it if you did not save it earlier). You will know that the cluster is running if you see an output like below:

    ```
    INFO Waiting until the user's pull secret is written to the instance disk...
    INFO Adding crc-admin and crc-developer contexts to kubeconfig...
    Started the OpenShift cluster.

    The server is accessible via web console at:
    https://console-openshift-console.apps-crc.testing

    Log in as administrator:
    Username: kubeadmin
    Password: SOME-GENERATED-PASSWORD

    Log in as user:
    Username: developer
    Password: developer

    Use the 'oc' command line interface:
    PS> & crc oc-env | Invoke-Expression
    PS> oc login -u developer https://api.crc.testing:PORT

    OR Open the web-console with:
    PS> crc console
    ```

4. 
    You first get placed in a project called default, but its recommended that you set up another namesspace with `oc new-project <name>`. Also it is good practice to remember to check what OpenShift project you are targeting before building or deploying any changes with `oc project`.

Now you should have an environment where you can deploy pods with containers for the APP and DB in your local cluster.


### Start OpenShift:
Start your cluster and open the web-console to monitor the cluster easily. An overview can be found through clicking: `Home -> Projects -> <your project name> -> Workloads`. This shows only the pods and workloads created within your project, and makes it easier to monitor everything.

The creators of this repository have set up the cluster using Kubernetes/OpenShift manifests, read more on [OpenShift - Guide](https://openshift.guide/openshift-guide-screen.pdf). In particular a deployment manifests are used to deploy the systems. However, FK use a GitOps-based approach like **Argo CD** for continuous delivery (CD), enabling automated and declarative updates of the application and database. However, this hasn't been introduced by the creators of the project and will not be covered here. It might still be worth adding if you have the time for it.

* First we check that we are inside the correct OpenShift project: `oc project` -> should return "project name", else switch to it.

* Start the database pod: `oc apply -f thermostat-mysql.yaml`, check in the OpenShift console if the pod has started and that you can access the terminal, you should be able to access the mysql db from its cli with the credentials set in the `thermostat-mysql.yaml` manifest you just applied.

* Build the images for the apps, from the repository, using the buildconfig yaml manifests: `oc apply -f build-temperature-sensor.yaml` and `oc apply -f build-thermostat.yaml`. If you check the build manifests you can set the repo url and the target branch that the image is built from.

* Deploy the apps by using the deployment manifests: `oc apply -f deployment-temperature-sensor-1`, `oc apply -f deployment-temperature-sensor-2` and `oc apply -f deployment-thermostat`. This will start two temperature sensors and one themrostat system.

* When you can see that the pod for the app have a 'Running' status in the OpenShift web-console, you should now be able to run the `oc get routes` to extract the url where you can reach your application endpoints.


## Continue Contributing to the Demo

This demo was crated created to practice using the tools to implement Java applications with Quarkus running in an OpenShift cluster. However this is still not fully realistic due to there being only a few dummy endpoints. No realistic testing pipelines and only one OpenShift cluster with a few running pods. But hopefully, following these steps, you have familiarized yourself with the tools.

As stated earlier, contribute to the project if you think there are some changes that should be made. Or that something should be added to make this more realistic, or generally improve any of the steps. Maybe you think that there should be a more solid deployment pipeline running ArgoCD or maybe you'd like to practice Ghurking tests? Then please create a PR, or post an issue about it for future developments to the demo.


## References
This is a list of the docs that the creators have used during the creation of the demo:
* [Quarkus - Get Started](https://quarkus.io/get-started/)
* [Quarkus - Guide](https://quarkus.io/guides)
* [Quarkus - Qickstart Git](https://github.com/quarkusio/quarkus-quickstarts/tree/main)
* [OpenShift - Guide](https://openshift.guide)
* [Kubernetes - Docs](https://kubernetes.io/docs/home/), really nice when you get to know manifests.
* [Redhat - Containers](https://catalog.redhat.com/en/search?searchType=containers), nice to find images for rhel with different versions.

## Creators to reach out to if you have any questions
Daniel Thungren - daniel.thungren@ductus.se \
Jacob Möller    - jacob.moller@ductus.se \
Jesper Frisk    - jesper.frisk@ductus.se