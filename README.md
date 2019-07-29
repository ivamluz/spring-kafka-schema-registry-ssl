# Spring Boot + Kafka + Schema Registry + SSL

The purpose of this application is to show how to solve the problem of multiple keystores using Spring Boot + Kafka + Schema Registry + SSL.

## Problem description

1. When
     * We have Spring Boot exposing SSL end-points with a first distinct certificate;
     * We have communication with Kafka via SSL with a second distinct certificate;
     * We have the communication with Schema Registry with the same certificate used for communication with Kafka or a separate third party certificate;

2. Scenarios

    | Protocol | Spring Boot | Kafka | Schema Registry | Result |
    |:--------:|:-----------:|:-----:|:---------------:|:------:|
    | SSL      | Yes         | Not   | Not             | **Ok** |
    | SSL      | Yes         | Yes   | Not             | **Ok** |
    | SSL      | Yes         | Yes   | Yes             | Fail   |
    | SSL      | Not         | Yes   | Yes             | **Ok** |
    | SSL      | Not         | Not   | Yes             | **Ok** |
    | SSL      | Not         | Not   | Not             | **Ok** |

Just where the failure happens is the scenario we need in operation, where the application uses one certificate to securely expose endpoints, and uses other certificates to communicate with Schema Resgistry and Kafka.

```text
+-------------------+            +-----------------------+
|                   |<---json--->| Schema Registry + SSL |
|                   |            +-----------------------+
| Spring Boot + SSL |
|                   |            +-----------------------+
|                   |<--binary-->|      Kafka + SSL      |  
+-------------------+            +-----------------------+
```

The problem identified is the `kafka-avro-serializer` component uses the JVM variables, `javax.net.ssl.trustStore`, `javax.net.ssl.keyStore`, `javax.net.ssl.trustStorePassword` and `javax.net.ssl.keyStorePassword`, and these variables apply to the whole application, ie if we use a certificate to export the application api it will be used for the `kafka-avro-serializer` component.

It is intended that the application use a certificate to expose the api and use a second certificate to communicate with the Schema Registry.

These multi-certificate feature has already been identified, you can see the discussion of the problem [here] (https://github.com/confluentinc/schema-registry/pull/957), as this problem extends from last year without approval, I created the solution presented here.

* [Registering Schemas](documentation/register-schemas.md)
* [Consuming API](documentation/consume-api.md)
* [Generating Certificates](documentation/generate-certificates.md)
