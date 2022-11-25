# Enterprise programming 2 - Final Exam

### Service:port
* gateway: 8080/
* discovery-service: 8761/
* order-service: 8086/
* payment-service: 8085/
* shipping-service: 8090/

### Postgres - Tables
* customers
* orders

### RabbitMQ - Queues
* order-queue
* shipping-request

## Gateway (Starting point) - http://localhost:8080/

All requests can be made against **port 8080**. The gateway does the routing for the other services.  

## User cases
 
1. Client can retrieve a single order ``GET /api/order/{orderId}``
2. Client can retrieve all orders (with pagination) ``GET /api/order/all?page=0``
3. Client can delete an order ``DELETE /api/order/{orderId}``
4. Client can create a new order -> automatically make a http request to payment -> back to order service and payment updated ``POST /api/order``
5. Another API call to shipping service to tell shipping service to "pack" the order ``POST /api/order/{orderId}``
6. Shipping Service sends a message to order service when the order is ready -> back to order service and dispatch status updated ``POST /api/shipping/{orderId}``

## Order service 

This service manages creating and updating orders, along with communicating with other services.

### Endpoints:
**/ GET**
* [x] Retrieve all orders with pagination (by default 5 orders per page)
  * eg ``/api/order/all?page=0``
* [x] Retrieve a single order
  * eg ``/api/order/{orderId}``
  * If order ID not found, it will throw an **exception**
  * The order is also cached in the service-layer 

**/ POST**
* [x] Create an order ``/api/order``
  * How to post via Postman:
    ````json
    {
        "description":"Light saber",
        "customerName":"Donald Duck",
        "customerAddress":"Snacks Street 123"
    }
    ```` 
  * If order info is missing, it will throw an **exception**
  * Once the order is created in order-service, it will automatically communicate to the payment-service via synchronous http request. When payment-service receive the http request from order-service, it will respond with a "Payment OK"-message back to order-service, and the order service will update the order from ```payment = false``` to ```payment = true```. 
  

* [x] Send order information to shipping service using queue
  * How to post via Postman: ``/api/order/{orderId}``
  

**/ DELETE**
* [x] Delete an order by order ID ``/api/order/{orderId}``
  * It clears the cache too  

## Payment service 

This service is responsible for processing payment. It receives a synchronous http request from order-service, and respond with payment status. 

### Endpoint:
**/ GET**
* [x] Receive http request from order-service ``/api/payment``

## Shipping service 

This service is responsible for processing the order for shipment. 

### Endpoint:
**/ POST**
* [x] Tell order-service when the order is dispatched. In this case we will need to use this endpoint to "simulate" the process. 

## Dockerfiles -> Docker-compose with profiles - Step-by-step guide

1. ``mvn clean package -DskipTests`` to build .jar (for each service)
2. ``docker build -t SERVICENAME:1 .`` to build the Dockerfile
3. Go to root of the project **pg6102_1022**
4. To spin up the services via Docker use docker-compose from root: ``docker-compose up -d``
5. Additionally, to start up the other services, use ``--profile [SERVICE]``
   * Available services: **gateway**, **discoveryservice**, **orderservice**, **paymentservice** and **shippingservice**
   * eg ``docker-compose --profile orderservice --profile gateway --profile discoveryservice --profile paymentservice --profile shippingservice up -d``

## Troubleshooting

* Docker - Eureka: For some reason Eureka can not find ny instances. When I run the services locally on Intellij, all of them shows up. I tried to Google it, and many had the same problem. If running services from Docker, it needs to run via baseUrl (eg port 8086 for order service, instead of port 8080)

## Conclusion
If I had more time I would spend more time to implement circuit breaker and added more tests. I did not have enough time to do all the testing I wanted to do because I spent too much time to fix Eureka via Docker... 
And because we only had 72 hours for this exam, I did not implement too much domain logic on other services except for the 'order-service'. 'Payment' and 'shipping'-service just simulate the processes. 
