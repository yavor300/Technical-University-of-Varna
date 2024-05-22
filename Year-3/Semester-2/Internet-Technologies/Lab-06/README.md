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

curl --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=1DB34393B4480B1EA7C16C5E34D21157' \
--data-raw '{
"name": "John Doe",
"email": "john.doe@example.com",
"username": "johnny_doe",
"password": "securePassword123",
"roles": ["ADMIN", "USER"]
}
'

curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=CE811E6BFAF00011342C2314F6B60272' \
--data '{
"username": "johnny_doe",
"password": "securePassword123"
}
'

curl --location --request POST 'http://localhost:8080/api/v1/auth/logout'