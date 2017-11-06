# API Capability Building Workshop - API Logging, Monitoring and Tracing

## Setup

#### How to run the services

Install plugin in IntelliJ IDEA: `lombok`

```
./gradlew :user-service:bootRun
./gradlew :logistics-service:bootRun
./gradlew :product-service:bootRun
./gradlew :order-service:bootRun
```

Debug mode: --debug-jvm

#### How to start continuous build
```
./gradlew build --continuous
```

#### Project structure and flow

```
├── README.md
├── order-service
├── user-service
├── product-service
└── logistics-service


http://localhost:8080/order-service/orders/1234567890
-> orderId -> Order User -> userId      -> User Service     
                         -> productId   -> Product Service
                         -> logisticsId -> Logistics Service

```

## tracing

You can access the two endpoints once you start the services:
- [http://localhost:8080/order-service/orders/1234567890](http://localhost:8080/order-service/orders/1234567890)
- [http://localhost:8080/order-service/orders/1234567890/sync](http://localhost:8080/order-service/orders/1234567890/sync)

### steps
### configure zipkin server:
 1. add dependency for zipkin-server
    ```
    compile('io.zipkin.java:zipkin-server')
    runtime('io.zipkin.java:zipkin-autoconfigure-ui')
    ```
    
 2. add `@EnableZipkinServer` annotation for ZipkinServerApplication
 2. run `./gradlew :zipkin-server:bootRun`
 3. check [zipkin dashboard](http://localhost:9411)
 
### configure trace client(for both order-service, user-service, product-service and logistics-service):
 1. add dependency for each service
    ```
    compile('org.springframework.cloud:spring-cloud-starter-sleuth')
    compile('org.springframework.cloud:spring-cloud-starter-zipkin')
    ``` 
 2. run command ./gradlew cI idea, then start all services using ./gradlew :xxx-service:bootRun
 3. add log for http request(slf4j)
 4. start application check log
 5. check [zipkin dashboard](http://localhost:9411)
 6. add sampler properties sleuth.sampler.percentage=0.2
 7. check [zipkin dashboard](http://localhost:9411)
 
### Optional: persistent the trace information
 1. add dependency: compile('io.zipkin.java:zipkin-autoconfigure-storage-mysql:2.2.1')
    add dependency: compile('mysql:mysql-connector-java:5.1.13')
    add dependency: compile('org.springframework.boot:spring-boot-starter-jdbc')
 2. we choose to use mysql as dbms, create related database;
 3. add spring datasource configuration.(schema/username/password/url/driver-class-name...)
 4. check the database
 
## log monitoring

### steps
### configure log for order-service
 1. add log output file
 2. start application, check if log file is generated
 3. add log appender to generate log(create a file named logback.xml under resource directory)
 ``` logback.xml
 <configuration>
     <appender name="FILE" class="ch.qos.logback.core.FileAppender">
         <file>build/log/application.log</file>
         <encoder>
             <pattern>%-4relative [%thread] %-5level %logger{35} - %msg%n</pattern>
         </encoder>
     </appender>
     <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
         <encoder>
             <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
         </encoder>
     </appender>
 
     <root level="info">
         <appender-ref ref="FILE" />
         <appender-ref ref="STDOUT" />
     </root>
 </configuration>
 ```
### use interceptor for logging
 1. create LoggingInterceptor class implement the HandlerInterceptor interface
 2. override the prehandler behavior
 3. create a log configuration class extends from WebMvcConfigureAdapter
 4. override the addInterceptors method, register the LoggingInterceptor to the InterceptorRegistry 
 5. restart the server and check the log.
 
### configure splunk forwarder
 1. Configure Forwarder connection to Index Server: ./SplunkForwarder/bin/splunk add forward-server localhost:9997
 2. restart the forwarder: ./SplunkForwarder/bin/splunk restart
 3. go to splunk dashboard(default port:8000), enable the 9997 port of the indexer(setting->forwarding & receiving->receive data add new)
 4. check if the forwarder and server's connection is good: ./SplunkForwarder/bin/splunk list forward-server
 5. search index=_internal in plunk, check the log
 6. add monitor for our api log: ./SplunkForwarder/bin/splunk add monitor /path/to/app/logs/ -index ${index} -sourcetype ${sourcetype}
 7. restart the forwarder
 8. add index for server in splunk dashboard(settings->indexes->new index)
 9. call our api
 10. search our index in splunk, check the log.
