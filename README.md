# demo-crud

A minimal Spring Boot REST API that does basic CRUD operations **entirely in memory** —
no database yet. This is meant as a clean starting point before you layer in
Docker, a real database, networking, and volumes.

## Stack

- Java 21 (bytecode target — your installed JDK 25 runs this without any issue)
- Spring Boot 3.3.4
- Maven
- In-memory storage (`ConcurrentHashMap`) — data resets every restart

## Project structure

```
demo-crud
├── pom.xml
├── README.md
└── src
    └── main
        ├── java/com/example/democrud
        │   ├── DemoCrudApplication.java      # entry point
        │   ├── model/Item.java               # the "resource" we CRUD
        │   ├── service/ItemService.java       # in-memory storage + logic
        │   ├── controller/ItemController.java # REST endpoints
        │   └── exception/                     # 404 + validation error handling
        └── resources
            └── application.properties         # port 8099 config
```

## Opening in IntelliJ

1. Open IntelliJ IDEA → **File → Open** → select the `demo-crud` folder.
2. IntelliJ will detect the `pom.xml` and import it as a Maven project
   automatically (wait for indexing to finish).
3. Make sure IntelliJ is using JDK 21+ as the Project SDK
   (**File → Project Structure → Project SDK**). Since you have JDK 25
   installed, that's fine to select too — the `pom.xml` just targets
   bytecode level 21 for compatibility with the current Spring Boot release.
4. Run `DemoCrudApplication.java` (right-click → Run), or use the Maven
   panel and run `spring-boot:run`.

## Running from the terminal

```bash
cd demo-crud
mvn spring-boot:run
```

(This assumes Maven is installed — `brew install maven` on macOS if you
don't have it yet. IntelliJ bundles its own Maven, so running from IntelliJ
directly works even without installing Maven separately.)

The app starts on **http://localhost:8099**.

## API Endpoints

Base path: `/api/items`

| Method | Path              | Description          |
|--------|-------------------|-----------------------|
| GET    | `/api/items`      | List all items        |
| GET    | `/api/items/{id}` | Get one item by id     |
| POST   | `/api/items`      | Create a new item      |
| PUT    | `/api/items/{id}` | Update an existing item |
| DELETE | `/api/items/{id}` | Delete an item         |

The app seeds two sample items on startup so `GET /api/items` isn't empty.

### Example requests (curl)

**List all items**
```bash
curl http://localhost:8099/api/items
```

**Get one item**
```bash
curl http://localhost:8099/api/items/1
```

**Create an item**
```bash
curl -X POST http://localhost:8099/api/items \
  -H "Content-Type: application/json" \
  -d '{"name": "Monitor", "description": "27-inch 4K monitor", "price": 349.99}'
```

**Update an item**
```bash
curl -X PUT http://localhost:8099/api/items/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Keyboard Pro", "description": "Updated description", "price": 89.99}'
```

**Delete an item**
```bash
curl -X DELETE http://localhost:8099/api/items/1
```

### Validation & error handling

- `name` is required — an empty/blank name returns `400` with field errors.
- `price` must be zero or positive.
- Requesting a non-existent id returns a clean `404` JSON body instead of a
  stack trace, e.g.:

```json
{
  "timestamp": "2026-08-26T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Item with id 99 was not found"
}
```

## Notes on the Java version

`java -version` on your Mac shows JDK 25. Spring Boot 3.3.x is tested up
through Java 21/22 at time of writing, so the `pom.xml` compiles to Java 21
bytecode via `<java.version>21</java.version>` for maximum compatibility.
Your JDK 25 runtime can still execute Java 21 bytecode without any problem —
newer JDKs are backward compatible. You can bump this later once Spring Boot
officially certifies newer Java versions.

## Where this goes next (your learning plan)

This app is deliberately dependency-light so each concept can be added one
at a time:

1. **Dockerize this app as-is** — write a `Dockerfile`, build an image with
   OrbStack, run the container, map port 8099 → 8099.
2. **Add a real database** (e.g. Postgres) — swap `ItemService`'s in-memory
   map for Spring Data JPA + a repository.
3. **Docker Compose / networking** — run the app container and a database
   container together, learn container-to-container networking via a
   custom bridge network and service names as hostnames.
4. **Storage** — add a named volume for the database container so data
   survives container restarts.

Happy to help with the `Dockerfile` and `docker-compose.yml` for this exact
app whenever you're ready to move to that step.
