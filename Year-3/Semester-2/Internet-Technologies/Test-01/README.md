# Animals API with Tomcat and Java Servlets

Base path is /api/v1/animals.

Below are examples of GET, POST, PUT and DELETE requests.

The payload and response should be handled in XML format.

curl --location 'http://localhost:8080/api/v1/animals/add' \
--header 'Content-Type: application/xml' \
--data '<animal>
<type>DOG</type>
<birthYear>2002</birthYear>
<name>Yavor</name>
</animal>'

curl --location 'http://localhost:8080/api/v1/animals/add' \
--header 'Content-Type: application/xml' \
--data '<animal>
<type>DOG</type>
<birthYear>2002</birthYear>
</animal>'

curl --location 'http://localhost:8080/api/v1/animals/view/other'

curl --location 'http://localhost:8080/api/v1/animals/view/dog'

curl --location 'http://localhost:8080/api/v1/animals/view/cat'

curl --location --request PUT 'http://localhost:8080/api/v1/animals/edit' \
--header 'Content-Type: application/xml' \
--data '<animal>
<id>1</id>
<name>Yavor21</name>
</animal>'

curl --location --request DELETE 'http://localhost:8080/api/v1/animals/delete/8'
