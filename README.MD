[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sebastian-toepfer_domain-driven-desgin&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sebastian-toepfer_domain-driven-desgin)

# Why this Project

With the introduction of microservices and the way they are developed today, many systems struggle with a clean domain driven design approach.

Most frameworks for creating microservices require some kind of accessors - in Java we usually do this with getters/setters. This is usually not the best way to describe your domain.

Most architectures use data transfer objects (dto) to define the representation model. A dto is a data object without any logic. Some architectures extend the idea and add mapping logic to the dto.

The dtos then normally have aspects, as they can be serialized in a medium such as xml or json. Aspects in Java are usually defined by annotations. The use of annotations is a kind of gray area. In a clean architecture, writes Uncle Bob, the domain should be independent of the infrastructure. Access to the infrastructure should be regulated by clearly defined interfaces (adapters). Uncle Bob defines annotations as an aspect and therefore they can also be used on POJOs. This also means that we can apply them to our domain objects.

Let's look at all this using an example:

## A very Simple Domain Model
```java
public final class Todo {

    private final long id;
    private String title;
    private String description;
    private String done;

    public Todo(
        final String title,
        final String description
    ) {
        this(0, title, description, false);
    }

    public Todo(
        final long id,
        final String title,
        final String description,
        final boolean done
    ) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = Objects.requireNonNull(title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = Objects.requireNonNull(description);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

}

public interface Todos {
    Optional<Todo> findById(long id);
    Stream<Todo> findAll();
    Todo add(Todo todo);
}
```
boilerplate code (getter/setter/ctor) can be omitted with Lombok or created by the IDE

## Representation Model (wih jackson and bean validation)
```java
public class TodoDTO {

    @JsonPropery("id")
    private final Long id;
    @NotEmpty
    @JsonPropery("title")
    private final String title;
    @NotEmpty
    @JsonPropery("description")
    private final String description;    
    @JsonPropery("done")
    private final booelan done;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ToDoDTO(
        @JsonProperty("id") final Long id,
        @JsonProperty("title") final String title,
        @JsonPropery("desciption") final String description,
        @JsonPropery("done") final booelan done        
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public Todo asEntity() {
        return new Todo(id, title, description, done);
    }
}
```

# Rest Boundary
without a framework, so more pseudo code!
## Create a new TODO
```java
@POST
@Produces("application/json")
@Consumes("application/json")
public Response create(
    final ToDoDTO newToDo
    @Context UriInfo uriInfo
) {
    final Todo todo = newToDo.asEntity();
    todos.add(todo);
    return Response
        .created(uriInfo.getAbsolutePathBuilder().path(todo.getId()).build())
        .build();
}
```
okay is not the worst code we can produce.
## Get a TODO
```java
@GET
@Path("{id}")
@Produces("application/json")
public Response getById(
    @PathParam("id") final long id
) {
    return todos.findById(id)
        .map(entity -> new TodoDTO(
            entiy.getId(),
            entiy.getTitle(),
            entity.getDescription(),
            entity.isDone()))
        .map(dto -> Response
            .ok()
            .type(MediaType.APPLICATION_JSON)
            .encoding("UTF-8")
            .entity(dto))
        .orElseGet(() -> Response.status(404))
        .build();
}
```
looks okay, but should a todo really have a getTitle?
What if we need xml?

## Get a all TODOs
```java
@GET
@Produces("application/json")
public Response create(
    final long id
) {
    final List<TodoDTO> dtos = todos.findAll()
        .map(entity -> new TodoDTO(
            entiy.getId(),
            entiy.getTitle(),
            entity.getDescription(),
            entity.isDone()))
        .toList();

    return Response.ok()
        .ok()
        .type(MediaType.APPLICATION_JSON)
        .encoding("UTF-8")
        .entity(dtos);
}
```
looks okay, but should a todo really have a getTitle?
What if we need xml?
How to stream the data?

# How to use this project?

## Lets start with the domain

add maven dependency
```xml
<dependency>
  <groupId>io.github.sebastian-toepfer.ddd</groupId>
  <artifactId>common</artifactId>
  <version>0.5.0</version>
</dependency>
```

Implement Printable.
```java
public final class Todo implements Printable {

    private final long id;
    private String title;
    private String description;
    private String done;

    public Todo(
        final String title,
        final String description
    ) {
        this(0, title, description, false);
    }

    public Todo(
        final long id,
        final String title,
        final String description,
        final boolean done
    ) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.done = done;
    }

    public Todo markAsDone() {
        this.done = true;
        return this;
    }

    public long identifier() {
        return id;
    }

    public <T> T printOn(final Media<T> media) {
        return media.withValue("id", id)
            .withValue("title", title)
            .withValue("description", description)
            .withValue("done", done);
    }

}

public interface Todos {
    Optional<Todo> findById(long id);
    Stream<Todo> findAll();
    Todo add(Todo todo);
}
```

## Representation Model
we start without one. no DTO's are required.

## Rest Boundary
without a framework, so more pseudo code!
### Create a new TODO
```java
@POST
@Produces("application/json")
@Consumes("application/json")
public Response create(
    final JsonObject newTodo
    @Context UriInfo uriInfo
) {
    final Todo todo = new Todo(
        newTodo.getString("title"),
        newTodo.getString("description")
    );
    todos.add(todo);
    return Response
        .created(uriInfo.getAbsolutePathBuilder().path(todo.identifier()).build())
        .build();
}
```
okay without dto more code at this place.

### Read methods

To get Json-Support add maven dependency
```xml
<dependency>
  <groupId>io.github.sebastian-toepfer.ddd</groupId>
  <artifactId>media-json-api</artifactId>
  <version>0.5.0</version>
</dependency>
```

#### Get a Todo

```java
@GET
@Path("{id}")
@Produces("application/json")
public Response getById(
    @PathParam("id") final long id
) {
    return todos.findById(id)
        .map(entity -> entity.printOn(new JsonObjectMedia()))
        .map(json -> Response
            .ok()
            .type(MediaType.APPLICATION_JSON)
            .encoding("UTF-8")
            .entity(json))
        .orElseGet(() -> Response.status(404))
        .build();
}
```
looks similar to the way with dto.
But we can now simple change the representation.
How? Change the media.

##### Let do some i18n.
add dependency
```xml
<dependency>
  <groupId>io.github.sebastian-toepfer.ddd</groupId>
  <artifactId>media-core</artifactId>
  <version>0.5.0</version>
</dependency>
```

```java
@GET
@Path("{id}")
@Produces("application/json")
public Response getById(
    @PathParam("id") final long id
) {
    return todos.findById(id)
        .map(entity -> entity.printOn(
            new TranslateNameDecorator<>(
                new JsonObjectMedia(),
                //change depending on the desired language ... 
                //we translate everything into german!
                new Translator() {
                    @Override
                    public Optional<String> translate(final String translate) {
                        return Optional.ofNullable(
                            Map.of(
                                "name", "name",
                                "description", "beschreibung",
                                "done", "erledigt"
                            )
                            .get(translate)
                        );
                    }
                }
            )
        )
        .map(json -> Response
            .ok()
            .type(MediaType.APPLICATION_JSON)
            .encoding("UTF-8")
            .entity(json))
        .orElseGet(() -> Response.status(404))
        .build();
}
```
the core also had a translator for camelcase or snakecase. we must not hope that jackson had the right settings or put the jsonproperty everywhere.

#### Get a all TODOs
```java
@GET
@Produces("application/json")
public Response getAll() {
    final JsonArray json = todos.findAll()
        .map(entity -> entity.printOn(new JsonObjectMedia()))
        .collect(toJsonArray());

    return Response.ok()
        .ok()
        .type(MediaType.APPLICATION_JSON)
        .encoding("UTF-8")
        .entity(json);
}
```
looks similar to the way with dto.
But we can now simple change the representation.
How? Change the media.

How to stream the data? Since 0.5.0 change the media.

#### Stream some data
```java
@GET
@Produces("application/json")
public Response getAll() {
    return Response
            .ok()
            .type(MediaType.APPLICATION_JSON)
            .encoding("UTF-8")
            .entity(
                (StreamingOutput) out -> {
                    final var media = new JsonArrayStreamMediaPrintableAdapter(out);
                    todos.findAll().forEach(media::print);
                    media.end();
                    media.close();
            })
            .build();
}
```
we can also translate the properties to german!
use
```java
new JsonArrayStreamMediaPrintableAdapter(
    out,
    media ->
        new TerminableDecorator(
            new TranslateNameDecorator<>(
                media,
                new Translator() {
                    @Override
                    @Override
                    public Optional<String> translate(final String translate) {
                        return Optional.ofNullable(
                            Map.of(
                                "name", "name",
                                "description", "beschreibung",
                                "done", "erledigt"
                            )
                            .get(translate)
                        );
                    }
                }
            )
        )
),
```
instead of
```java
final var media = new JsonArrayStreamMediaPrintableAdapter(out);
```
