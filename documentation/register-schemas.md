# Schemas

1. Registrar schema

   ```shell
   curl -s -k -d "@people-schema.json" -H "Content-Type: application/vnd.schemaregistry.v1+json" -X POST https://schema-registry-node01.ciandt.ep:8082/subjects/people-value/versions/ | jq
   ```

   A saída deve ser parecida com isso:

   ```json
   {
    "id": 101
   }
   ```

2. Verificar os subjects

   ```shell
   curl -s -k https://schema-registry-node01.ciandt.ep:8082/subjects/ | jq
   ```

   A saída deve ser parecida com isso:

   ```json
   [
     "people-value"
   ]
   ```

3. Verificar as versions

   ```shell
   curl -s -k https://schema-registry-node01.ciandt.ep:8082/subjects/people-value/versions | jq
   ```

   A saída deve ser parecida com isso:

   ```json
   [
     1
   ]
   ```
