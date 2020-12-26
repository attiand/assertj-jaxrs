# assertj-jaxrs (Work In Progress)

Lightweight library that provides AssertJ custom assertions to JAX-RS responses. 

Example, check status code and header:
```java

try (Response response = target.path("/resource").request().options()) {
   assertThat(response)
      .hasStatusCode(Status.OK)
      .containHeader(HttpHeaders.ALLOW)
      .hasNoEntity();
}
```
Example, check the entity:
```java

try (Response response = target.path("/resource").request().get()) {
   assertThat(response)
      .hasStatusCode(Status.OK)
      .entityAs(ExampleRepresentation.class)
      .satisfies(r -> {
         assertThat(r.getName()).isEqualTo("name");
         assertThat(r.getValue()).isEqualTo(10);
      });
   }
}
```
For more examples se integration tests:
* [Status code](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/StatusCodeIT.java)
* [Headers](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/HeaderIT.java)
* [Cookies](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/CookieIT.java)
* [Entity](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/EntityIT.java)
* [MediaType](assertj-jaxrs-core/src/test/java/com/github/attiand/assertj/jaxrs/MediaTypeIT.java)

Add the following dependency :
```xml
<dependency>
    <groupId>com.github.attiand</groupId>
    <artifactId>assertj-jaxrs-core</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```

# Json Pointer

Simple support for asserting JSON Pointer values if you don't have the entity type available:
```java
try (Response response = target.path("/resource").request().get()) {
   ResponseAssert.assertThat(response)
      .hasStatusCode(Status.OK)
      .entityAsJson()
      .satisfies(o -> {
         JsonObjectAssert.assertThat(o).path("/name").asString().isEqualTo("name");
         JsonObjectAssert.assertThat(o).path("/value").asInteger().isEqualTo(10);
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
    <version>1.0.8</version>
    <scope>test</scope>
</dependency>
```
# JUnit Jupiter extension
There is a JUnit 5 extension that provides a `WebTarget` parameter resolver.
```java
@ExtendWith(AssertjJaxrsExtension.class)
@AssertjJaxrsSettings(baseUri = "http://localhost:8088")
class MyTest {
   @Test
   void shouldAssertStatusCodeAsInteger(WebTarget target) {
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
    <artifactId>assertj-jaxts-junit-jupiter</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```
## Quarkus

Note that the `@QuarkusTest` extension does not work well with a `WebTarget` parameter resolver. You can run Quarkus tests like this.
```java
@QuarkusTest
class GreetingResourceTest {
   @TestHTTPResource
   String uri;
    
   @Test
   void testHelloEndpointJaxrs() {
      WebTarget target = TestTargetBuilder.newBuilder().baseURI(uri).build();

      try (Response response = target.path("/hello-resteasy").request().get()) {
         assertThat(response)
            .hasStatusCode(200)
            .entityAsText()
            .isEqualTo("Hello RESTEasy");
      }
   }    
}
```