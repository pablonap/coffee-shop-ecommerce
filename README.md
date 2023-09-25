# COFFEE SHOP E-COMMERCE REST API

## Endpoints

| Controller      | Method | Endpoint                                   |
|-----------------|--------|--------------------------------------------|
| CartController  | POST   | /api/v1/carts/create                      |
|                 | PUT    | /api/v1/carts/{cartId}/add-product        |
|                 | PUT    | /api/v1/carts/{cartId}/product/{productId}/quantity |


| Controller      | Method | Endpoint                   |
|-----------------|--------|----------------------------|
| OrderController | POST   | /api/v1/orders/create      |

## Instructions for Docker usage

For development:

1. Add a route into the docker-compose-dev.yaml in the volumes section: [SOME_LOCAL_PATH]/db:/var/lib/mysql  
For example:  
volumes:  
      `- /home/my-username/Desktop/project/coffee-shop-ecommerce/db:/var/lib/mysql`

2. Then run: docker-compose -f docker-compose-dev.yaml up

3. In your IDE, add the following environment variables:  
DB_PASSWORD=root;DB_PORT=3307;DB_USERNAME=root;SECRET_KEY=5a06b6d8350f9ea8fe6397270995b0a4e46464e75ebf59acc7c5c79fd7194eac

4. Run the application from your IDE.

For testing:
1. Add a route into the docker-compose.yaml in the volumes section: [SOME_LOCAL_PATH]/db:/var/lib/mysql
For example:  
volumes:  
      `- /home/my-username/Desktop/project/coffee-shop-ecommerce/db:/var/lib/mysql`

2. In the root folder, run: mvn clean; mvn install -DskipTests

3. Then run: docker-compose -f docker-compose.yaml up


## How to use it

In the application, two users are pre-loaded: *lprodan* with the password *1234* and the role ADMIN, and *pablonap* with the password *1234* and the role USER.  
The endpoints of the OrderController and CartController can be accessed by users with the USER role.  
An example sequence to generate an order is as follows:  

1. Authenticate via a POST request with the username "pablonap" and password "1234" to obtain the token:  
`
curl --location 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data '{
    "username":"pablonap",
    "password":"1234"
}'
`

2. Create a Cart by adding the first product via a POST request as follows:  
`
curl --location 'http://localhost:8080/api/v1/carts/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsb25hcCIsImlhdCI6MTY5NTU3MjIxMiwiZXhwIjoxNjk1NjU4NjEyfQ.ZJIXYkaFVvumc1yNg3uZxzLswiyJLyFNbfnOaqzbbD0' \
--data '{
    "productId": 1,
    "quantity": 3
}'
`

3. Add another product to the Cart via a PUT request as follows:  
`
curl --location --request PUT 'http://localhost:8080/api/v1/carts/1/add-product' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsb25hcCIsImlhdCI6MTY5NTU2ODA0MiwiZXhwIjoxNjk1NjU0NDQyfQ.O6ONfNXi5DVjsEBkQHCT7SNgKme883Et_2EqCDorAPw' \
--data '{
    "productId": 5,
    "quantity": 2
}'
`

4. Modify the quantity of a previously added product using a PUT request:  
`
curl --location --request PUT 'http://localhost:8080/api/v1/carts/2/product/5/quantity' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsb25hcCIsImlhdCI6MTY5NTUzNTIxNiwiZXhwIjoxNjk1NjIxNjE2fQ.2552Ii1p6RABqIZ2uCzQCNEEz9ZcAxcmCLED_rsdEI8' \
--data '{
    "quantity": 3
}'
`

5. Generate an Order using a POST request as follows:  
`
curl --location 'http://localhost:8080/api/v1/orders/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsb25hcCIsImlhdCI6MTY5NTU2ODA0MiwiZXhwIjoxNjk1NjU0NDQyfQ.O6ONfNXi5DVjsEBkQHCT7SNgKme883Et_2EqCDorAPw' \
--data '{
    "cartId": 1,
    "shippingAddress": "velazquez 1234"
}'
`

• When the Cart is created, it's set to ON_PROCESS state and when an order is generated, both the Cart and the Order switch to FINISHED state. You cannot generate a new Order with a Cart whose state is FINISHED.