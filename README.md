# Spring Boot + Kafka + Schema Registry + SSL

O objetivo dessa aplicação é mostrar como resolver o problema de múltiplas keystores usando Spring Boot + Kafka + Scheam Registry + SSL.

## Descrição do problema

1. Quando

    * Temos o Spring Boot expondo end-points em SSL com um primeiro certificado distinto;
    * Temos a comunicação com o Kafka via SSL com um segundo certificado distinto;
    * Temos a comunicação com o Schema Registry com o mesmo certificado usado para a comunicação com o Kafka ou um terceiro certificado distinto;

2. Cenários

    | Protocol | Spring Boot | Kafka | Schema Registry | Resultado |
    |:--------:|:-----------:|:-----:|:---------------:|:---------:|
    | SSL      | Sim         | Não   | Não             | **Ok**    |
    | SSL      | Sim         | Sim   | Não             | **Ok**    |
    | SSL      | Sim         | Sim   | Sim             | Falha     |
    | SSL      | Não         | Sim   | Sim             | **Ok**    |
    | SSL      | Não         | Não   | Sim             | **Ok**    |
    | SSL      | Não         | Não   | Não             | **Ok**    |

Justamente onde acontece a falha é o cenário que precisamos em funcionamento, onde a aplicação usa um certificado, para expor end-points de modo seguro, e usa outro(s) certificados para estabelecer comunicação com o Schema Resgistry e Kafka.

```text

+-------------------+            +-----------------------+
|                   |<---json--->| Schema Registry + SSL |
|                   |            +-----------------------+
| Spring Boot + SSL |
|                   |            +-----------------------+
|                   |<--binary-->|      Kafka + SSL      |  
+-------------------+            +-----------------------+
```

O problema identificado é o componente `kafka-avro-serializer` utliza as variáveis de JVM, `javax.net.ssl.trustStore`, `javax.net.ssl.keyStore`, `javax.net.ssl.trustStorePassword` e `javax.net.ssl.keyStorePassword`, e essas variáveis valem para toda a aplicação, ou seja se usamos um certificado para export a api da aplicação a mesma será usada para o componente `kafka-avro-serializer`.

O desejado é que a aplicação use um certificado para expor a api e use um segundo certificado para a comunicação com o Schema Registry.

Essas feature de múltiplos certificados já foi identificado, você pode ver a discussão do problema [aqui](https://github.com/confluentinc/schema-registry/pull/957), como esse problema se estende desde o ano passado ainda sem aprovação, criei a solução apresentada aqui.

* [Registrando Schemas](documentation/register-schemas.md)
* [Consumindo a API](documentation/consume-api.md)
* [Gerando Certificados](documentation/generate-certificates.md)
