# Digital Media Platform - Ad Validation service

This project represents the microservice responsible for validating Ad Material attached to request when submitting a booking for new publications.

The service listens to the Kafka topic for a message indicating a new ad material has been uploaded. 
Then it fetches the metadata for the given material and rules based on the product it belongs to.

After applying the rules, it composes the validation result which is sent back to Kafka.

## Tech stack

* Kotlin 
* Spring Boot framework
* Kafka
* Faker (for mocking external rest services)

## CI/CD

Project utilizes GitHub actions running the build in 2 steps:
* Gradle build
* Docker build

The final artifact of the build is the docker image published to this repository registry. 
Latest artifacts can be found [here](https://github.com/petarmitrovic/ad-validation/pkgs/container/ad-validation) and pulled using following command:
```
docker pull ghcr.io/petarmitrovic/ad-validation:sha-c50be094eca8d95bfe8806a94909a18a82985280
```
## Building the project

The following command will perform ktling checks, compile the code, run tests and build executable boot jar.
```
./gradlew clean build
```

## Running the service

In order to run the service locally, make sure you have build the project first. 
The docker-compose.yaml file defines the docker stack containing the service but also supporting containers:
- **ad-validation** this service
- **kafka** for receiving and sending messages 
- **zookeeper** for managing kafka
- **kafka-ui** for interacting with kafka
- **faker** for mocking REST calls to external APIs

```
$ docker-compose up -d
```

### Demo
Make sure the docker stack is up and running: 
```
$ docker-compose ps
NAME                            IMAGE                             COMMAND                  SERVICE         CREATED         STATUS                          PORTS
ad-validation-ad-validation-1   ad-validation-ad-validation       "java -jar /ad-valid…"   ad-validation   5 minutes ago   Restarting (0) 56 seconds ago   
ad-validation-faker-1           dotronglong/faker:stable          "java -Dserver.port=…"   faker           5 minutes ago   Up 5 minutes                    0.0.0.0:3030->3030/tcp
ad-validation-kafka-1           confluentinc/cp-kafka:6.1.0       "/etc/confluent/dock…"   kafka           5 minutes ago   Up 5 minutes                    9092/tcp, 0.0.0.0:29092->29092/tcp
ad-validation-kafka-ui-1        provectuslabs/kafka-ui:latest     "/bin/sh -c 'java --…"   kafka-ui        5 minutes ago   Up 5 minutes                    0.0.0.0:8080->8080/tcp
ad-validation-zookeeper-1       confluentinc/cp-zookeeper:6.1.0   "/etc/confluent/dock…"   zookeeper       5 minutes ago   Up 5 minutes                    2181/tcp, 2888/tcp, 3888/tcp
```

Open up the kafka-ui in browser (http://localhost:8080).
If you're doing this for the first time, click on "Configure new cluster" button and enter the following for bootstrap server config:
* Host: kafka
* Port: 9092

On the left navigation bar select "Topics" > "Messages" > "Produce message", select "validation_requests" topic, 
and enter the following JSON as message:
```
{
    "productUUID": "1d391f13-3aa7-4e3d-85e9-47dfe4b38371",
    "materialUUID": "2fd04780-40e0-417c-ad1b-b4beb72841ae"
}
```

Then go back and select "validation_results" topic > "Messages" you should see a validation messages like this:
```
{
	"successes": [
		"Found size (47748); Expected within range 0..100000)"
	],
	"failures": [
		"Found AdMaterialMetadata(format=jpeg, size=47748, resolution=72, width=600, height=429, duration=0); Expected height: 100..200, width: 600..800; resolution: 200..300"
	]
}
```

You can repeat the process for another material:
```
{
    "productUUID": "1d391f13-3aa7-4e3d-85e9-47dfe4b38371",
    "materialUUID": "c649a59c-74c5-432e-8671-f2facdf47d30"
}
```

Alternatively, instead of sending the message via Kafka topic, you can submit it via rest API:
```
curl -H "Content-Type: application/json" \
  -d '{"productUUID": "1d391f13-3aa7-4e3d-85e9-47dfe4b38371","materialUUID": "c649a59c-74c5-432e-8671-f2facdf47d30"}' \
  -X POST http://localhost:8090/validations
```