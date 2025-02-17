curl --location 'http://localhost:8080/api/v1/tasks/create' \
--header 'Content-Type: application/json' \
--data '{
"summary": "sumamry",
"description": "description",
"deadline": "2024-04-05T12:12:12"
}'

curl --location --request PATCH 'http://localhost:8080/api/v1/tasks/edit' \
--header 'Content-Type: application/json' \
--data '{
"number": 1,
"summary": "sumamry",
"description": "description",
"deadline": "2024-04-05T12:12:12"
}'

curl --location --request DELETE 'http://localhost:8080/api/v1/tasks/1'

curl --location 'http://localhost:8080/api/v1/reports/create' \
--header 'Content-Type: application/json' \
--data '{
"taskId": 1,
"content": "content",
"hoursWorked": "15"
}'