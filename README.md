# springboot-microservicios-shop-app
Ejemplo de aplicación de una tienda con una arquitectura de microservicios empleando spring-boot y spring-cloud.

### api-gateway
Microservicio de API Gateway (Spring Cloud API Gateway, Load Balanced).

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


### discovery-server
Microservicio de registro y búsqueda de microservicios (Spring Cloud Eureka Server) donde registramos los microservicios de api-gateway, inventory-service, orders-service y products-service.

Url de la consola de administración: http://localhost:8761

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

### Auth Server KeyCloak
Url de la consola de administración de keycloak http://localhost:8181

Usuarios/Roles creados:
- Usuario: admin_user, Contraseña: Test123, Rol: ADMIN
- Usuario: basic_user, Contraseña: Test123, Rol: USER

Datos para obtener un token a través de Oauth2 (previanmente se debe acceder a un endpoint desde un navegador para una vez autenticado con un usuario al acceder al endpoint se generen las cookies necesarias para para llamar al endpoint que genera el token el cual se emplea para hacer las llamas a los endpoints).
- authorization_endpoint: http://localhost:8181/realms/shop-app-realm/protocol/openid-connect/auth
  - response_type: code
  - client_id: shop-app_client
  - client_secret: <CLIENT_SECRET>
  - scope: openid
  - redirect_uri: http://localhost:8080/login/oauth2/code/keycloak

- token_endpoint: http://localhost:8181/realms/shop-app-realm/protocol/openid-connect/token
  - code: <CODE_DEVUELTO_URL_AUTH>
  - client_id: shop-app_client
  - client_secret: <CLIENT_SECRET>
  - grant_type: password
  - username: admin_user o basic_user
  - password: Test123
