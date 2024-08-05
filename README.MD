#1 Swagger Implementation
* Requirement
  * Java 17
  * Spring Boot V3

#2. Getting Started
   For the integration between spring-boot and swagger-ui, add the library to the list of your project dependencies (No additional configuration is needed)
```
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.6.0</version>
   </dependency>
```

This will automatically deploy swagger-ui to a spring-boot application:

Documentation will be available in HTML format, using the official swagger-ui jars

The Swagger UI page will then be available at http://server:port/context-path/swagger-ui.html and the OpenAPI description will be available at the following url for json format: http://server:port/context-path/v3/api-docs

* server: The server name or IP 
* port: The server port 
* context-path: The context path of the application

Documentation can be available in yaml format as well, on the following path : /v3/api-docs.yaml

# Example
* http://localhost:9011/swagger-ui/index.html
* http://localhost:9011/v3/api-docs


# Minio installation and configuration
## Quickstart: MinIO for Windows
### Procedure
* Install the MinIO Server 
  * Download the MinIO executable from the following URL
  ```
  https://dl.min.io/server/minio/release/windows-amd64/minio.exe
  ```
* open cmd terminal then run
```
.\minio.exe server C:\minio --console-address :9001
```

* after then showing
```
API: http://192.0.2.10:9000  http://127.0.0.1:9000
RootUser: minioadmin
RootPass: minioadmin

Console: http://192.0.2.10:9001 http://127.0.0.1:9001
RootUser: minioadmin
RootPass: minioadmin

Command-line: https://min.io/docs/minio/linux/reference/minio-mc.html
   $ mc alias set myminio http://192.0.2.10:9000 minioadmin minioadmin

Documentation: https://min.io/docs/minio/linux/index.html

WARNING: Detected default credentials 'minioadmin:minioadmin', we recommend that you change these values with 'MINIO_ROOT_USER' and 'MINIO_ROOT_PASSWORD' environment variables.
```

# Connect your Browser to the MinIO Server

* Access the MinIO Console by going to a browser (such as Microsoft Edge) and going to http://127.0.0.1:9001 or one of the Console addresses specified in the minio server command’s output. For example, Console: http://192.0.2.10:9001 http://127.0.0.1:9001 in the example output indicates two possible addresses to use for connecting to the Console.

* While port 9000 is used for connecting to the API, MinIO automatically redirects browser access to the MinIO Console.

* Log in to the Console with the RootUser and RootPass user credentials displayed in the output. These default to minioadmin | minioadmin.

