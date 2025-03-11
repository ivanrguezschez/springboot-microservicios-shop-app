# springboot-microservicios-shop-app
Ejemplo de aplicación de una tienda con una arquitectura de microservicios empleando spring-boot y spring-cloud.

### api-gateway
Microservicio de API Gateway.

Endpoints de inventory-service:
* GET http://localhost:8080/api/inventory/{sku}
* POST http://localhost:8080/api/inventory/in-stock
    * [
          {
              "id": 1,
              "sku": "00001",
              "price": 4321,
              "quantity": 2
          }
      ]
 
Endpoints de orders-service:
* GET http://localhost:8080/api/order
* POST http://localhost:8080/api/order
    * {
          "orderItems": [
              {
                  "sku": "00002",
                  "price": 234,
                  "quantity": 1
              }
          ]
      }

Endpoints de products-service:
* GET http://localhost:8080/api/product
* POST http://localhost:8080/api/product
    * {
          "sku": "000001",
          "name": "PC gamer 1",
          "description": "Best PC 1",
          "price": 5430,
          "status": true
      }


### inventory-service
Microservicio de inventario (inventory).

Endpoints:
* GET http://localhost:8083/api/inventory/{sku}
* POST http://localhost:8083/api/inventory/in-stock
  * [
        {
            "id": 1,
            "sku": "00001",
            "price": 4321,
            "quantity": 2
        }
    ]

### orders-service
Microservicio de pedidos (orders).

Endpoints:
* GET http://localhost:8082/api/order
* POST http://localhost:8082/api/order
    * {
        "orderItems": [
            {
                "sku": "00002",
                "price": 234,
                "quantity": 1
            }
        ]
      }

### products-service
Microservicio de productos (products).

Endpoints:
* GET http://localhost:8081/api/product
* POST http://localhost:8081/api/product
    * {
        "sku": "000001",
        "name": "PC gamer 1",
        "description": "Best PC 1",
        "price": 5430,
        "status": true
      }
