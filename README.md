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

## User case
 
1. Client can retrieve a single order
2. Client can retrieve all orders (with pagination)
3. Client can delete an order
4. Client can create a new order -> automatically make a http request to payment -> back to order service and payment updated
   * Another API call to shipping service 
5. Shipping Service 

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

###Endpoint:
**/ GET**
* [x] Receive http request from order-service ``/api/payment``

## Shipping service 

This service is responsible for processing the order for shipment. 

###Endpoint:
**/ POST**
* [x] Tell order-service when the order is dispatched. In this case we will need to use this endpoint to "simulate" the process. 

## Docker-compose with profiles - Step-by-step guide

1. Go to root of the project **pg6102_1022**
2. To spin up the Docker: ``docker-compose up -d``
3. Additionally, to start up the other services, use ``--profile [SERVICE]``
   * Available services: **gateway**, **discoveryservice**, **orderservice**, **paymentservice** and **shippingservice**
   * eg ``docker-compose --profile orderservice --profile gateway --profile discoveryservice --profile paymentservice --profile shippingservice up -d``

## Troubleshooting

* Docker - Eureka: For some reason Eureka can not find ny instances. When I run the services locally on Intellij, all of them shows up. I tried to Google it, and many had the same problem. 
# Enterprise-2
