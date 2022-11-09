# assertj-jaxrs

Lightweight library that provides AssertJ custom assertions to JAX-RS responses. 

Example:
```java
try (Response response = target.path("/resource").request().get()) {
   assertThat(response).hasStatusCode(Status.OK)
      .hasMediaType(MediaType.APPLICATION_JSON_TYPE)
      .entityAs(ExampleRepresentation.class)
      .satisfies(e -> {
         assertThat(e.getName()).isEqualTo("myname");
         assertThat(e.getValue()).isEqualTo(10);
      });
}
```
For more examples se integration tests:
* [Status code](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/StatusCodeIT.java)
* [Headers](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/HeaderIT.java)
* [Cookies](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/CookieIT.java)
* [Entity](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/EntityIT.java)
* [MediaType](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/MediaTypeIT.java)
* [Date](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/DateIT.java)
* [Link](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/LinkIT.java)
* [Location](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/LocationIT.java)
* [Language](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/LanguageIT.java)

Add the following dependency :
```xml
<dependency>
    <groupId>com.github.attiand</groupId>
    <artifactId>assertj-jaxrs-core</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```
You also need a jaxrs client implementation, for example: 

```xml
<dependency>
    <groupId>org.jboss.resteasy</groupId>
    <artifactId>resteasy-client</artifactId>
    <scope>test</scope>
</dependency>
```

# Json Pointer

Simple support for asserting JSON Pointer values if you don't have the entity type available:
```java
try (Response response = target.path("/resource").request().get()) {
   assertThat(response)
      .hasStatusCode(Status.OK)
      .entityAs(JSON)
      .satisfies(e -> {
         assertThat(e).pathValue("/name").asString().isEqualTo("name");
         assertThat(e).pathValue("/value").asInteger().isEqualTo(10);
    });
}
```
Add the following dependency:
```xml
<dependency>
    <groupId>com.github.attiand</groupId>
    <artifactId>assertj-jaxrs-json</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```
You also need a `javax.json-api` implementation, for example: 
```xml
<dependency>
    <groupId>org.eclipse</groupId>
    <artifactId>yasson</artifactId>
    <scope>test</scope>
</dependency>
```

# XML Xpath
```java
try (Response response = target.path("/resource").request().get()) {
   assertThat(response)
      .hasStatusCode(Status.OK)
      .entityAs(XML)
      .satisfies(e -> {
         assertThat(e).xpath("/user/name").asString().isEqualTo("name");
         assertThat(e).xpath("/user/value").asInteger().isEqualTo(10);         
    });
}

```
Add the following dependency:
```xml
<dependency>
    <groupId>com.github.attiand</groupId>
    <artifactId>assertj-jaxrs-xml</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```

# JUnit Jupiter extension
There is a JUnit 5 extension that provides a jax-rs `Client` parameter resolver that holds a single instance until all tests are executed.
```java
@ExtendWith(AssertjJaxrsExtension.class)
class MyTest {
   @Test
   void shouldAssertStatusCodeAsInteger(Client client) {
      WebTarget target = client.target("http://localhost:8081");
   
      try (Response response = target.path("/resource").request().get()) {
         assertThat(response).hasStatusCode(200);
      }
   }
}
```
Add the following dependency:
```xml
<dependency>
    <groupId>com.github.attiand</groupId>
    <artifactId>assertj-jaxrs-junit-jupiter</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```
## Quarkus

Note that the `@QuarkusTest` extension does not work well with a `WebTarget` parameter resolver. You can however run Quarkus tests like this.
```java
@QuarkusTest
class GreetingResourceTest {
   @TestHTTPResource
   String uri;

   private static final Client client = ClientBuilder.newClient();

   @AfterAll
   static void afterAll() {
      client.close();
   }

   @Test
   void testHelloEndpointJaxrs() {
      WebTarget target = client.target(uri);

      try (Response response = target.path("/hello-resteasy").request().get()) {
         assertThat(response)
            .hasStatusCode(200)
            .entityAsText()
            .isEqualTo("Hello RESTEasy");
      }
   }    
}

```
# Revision history

`1.0.0` - First version

`2.0.0` - Added XPath, Json braking change path -> pathValue

`2.0.1` - Added maven wrapper, javadoc builds on java 17

`3.0.0` - Use jaxrs 3.0 (jakarta name space)
