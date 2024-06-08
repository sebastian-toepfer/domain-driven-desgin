![Sonar Quality Gate](https://img.shields.io/sonar/quality_gate/sebastian-toepfer_domain-driven-desgin?server=https%3A%2F%2Fsonarcloud.io)
![Sonar Tests](https://img.shields.io/sonar/tests/sebastian-toepfer_domain-driven-desgin?server=https%3A%2F%2Fsonarcloud.io)
![Sonar Violations](https://img.shields.io/sonar/violations/sebastian-toepfer_domain-driven-desgin?server=https%3A%2F%2Fsonarcloud.io)

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/sebastian-toepfer/domain-driven-desgin/build.yml)

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.sebastian-toepfer.ddd/domain-driven-desgin)
![GitHub Release](https://img.shields.io/github/v/release/sebastian-toepfer/domain-driven-desgin)
![GitHub commits since latest release](https://img.shields.io/github/commits-since/sebastian-toepfer/domain-driven-desgin/latest)

[![Reproducible Builds](https://img.shields.io/badge/Reproducible_Builds-ok-success?labelColor=1e5b96)](https://github.com/jvm-repo-rebuild/reproducible-central/blob/master/content/io/github/sebastian-toepfer/ddd/domain-driven-desgin/README.md)

# Why this Project

With the introduction of microservices and the way they are developed today, many systems struggle with a clean domain driven design approach.

Most frameworks for creating microservices require some kind of accessors - in Java we usually do this with getters/setters. This is usually not the best way to describe your domain.

Most architectures use data transfer objects (dto) to define the representation model. A dto is a data object without any logic. Some architectures extend the idea and add mapping logic to the dto.

The dtos then normally have aspects, as they can be serialized in a medium such as xml or json. Aspects in Java are usually defined by annotations. The use of annotations is a kind of gray area. In a clean architecture, writes Uncle Bob, the domain should be independent of the infrastructure. Access to the infrastructure should be regulated by clearly defined interfaces (adapters). Uncle Bob defines annotations as an aspect and therefore they can also be used on POJOs. This also means that we can apply them to our domain objects.

Let's look at all this using an example:

## A very Simple Domain Model
```java
public final class Todo {

    private long id;
    private String title;
    private String description;
    private boolean done;

    public Todo(
        final String title,
        final String description
    ) {
        this(Math.abs(ThreadLocalRandom.current().nextInt()), title, description, false);
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
    public TodoDTO(
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

public class TodoIdentity {

    private final Todo todo;

    public TodoIdentity(final Todo todo) {
        this.todo = Objects.requireNonNull(todo);
    }

    @JsonProperry("id")
    public long getId() {
        return todo.getId();
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
        .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(todo.getId())).build())
        .entity(new TodoIdentity(todo))
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
public Response getAll() {
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

Note on the version:
Release version means that the function was already available at the time of writing.
Snapshot version means that the function had not yet been released at the time of writing. Check the releases to see if the version is now available.

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

    private long id;
    private String title;
    private String description;
    private boolean done;

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

To get Json-Support add maven dependency
```xml
<dependency>
  <groupId>io.github.sebastian-toepfer.ddd</groupId>
  <artifactId>media-json-api</artifactId>
  <version>0.6.0</version>
</dependency>
```

to get filter ...
```xml
<dependency>
  <groupId>io.github.sebastian-toepfer.ddd</groupId>
  <artifactId>media-core</artifactId>
  <version>0.6.0</version>
</dependency>
```

```java
@POST
@Produces("application/json")
@Consumes("application/json")
public Response create(
    final JsonObject newTodo
    @Context UriInfo uriInfo
) {
    final Todo todo = todos.add(new Todo(newTodo.getString("title"), newTodo.getString("description")));
    return Response
        .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(todo.identifier())).build())
        .encoding(StandardCharsets.UTF_8.name())
        .entity(
            todo
                .printOn(new NameFilteredDecorator<>(new JsonObjectMedia(), List.of("id")::contains))
                .decoratedMedia()
        )
        .build();
}
```
okay without dto more code at this place.

### Read methods

#### Get a Todo

```java
@GET
@Path("{id}")
@Produces("application/json")
public Response getById(
    @PathParam("id") final long id
) {
    return todos
            .findById(id)
            .map(entity -> entity.printOn(new JsonObjectMedia()))
            .map(Response.ok().type(MediaType.APPLICATION_JSON)::entity)
            .orElseGet(() -> Response.status(HttpURLConnection.HTTP_NOT_FOUND))
            .build();
}
```
looks similar to the way with dto.
But we can now simple change the representation.
How? Change the media.

##### Let do some i18n.

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
        .map(Response.ok().type(MediaType.APPLICATION_JSON)::entity)
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
    final Stream<Todo> todos = this.todos.findAll();
        return Response
            .ok()
            .type(MediaType.APPLICATION_JSON)
            .entity(
                (StreamingOutput) out -> {
                    try (var media = new JsonArrayStreamMediaPrintableAdapter(out)) {
                        todos.forEach(media::print);
                    } catch (Exception ex) {
                        out.close();
                    }
                }
            )
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
