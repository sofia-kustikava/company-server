# Copilot Instructions

## Project Context
- **Current Java version:** 11 (LTS)
- **Future migration plan:** 17 → 21
- **Frameworks:** Spring Boot 2.5.x
- **Mapping library:** MapStruct 1.4.2.Final
- **Build tool:** Maven
- **Code style goals:** stable, readable, maintainable, modern

---

## Java Version Guidelines
- Use **Java 11 features only** for now. Avoid preview features.
- Prefer **final** for local variables and method parameters when possible.
- Use `var` only for local variables with obvious types.
- Avoid deprecated APIs.
- Use `Optional<T>` for return values that can be null.
- Use **java.time** package instead of legacy date/time APIs.
- Avoid reflection unless necessary for framework integration.

**Future migration notes:**
- Java 17:
    - Sealed classes, Records
    - Pattern matching for `instanceof`
    - Text blocks
- Java 21:
    - Record patterns
    - Virtual threads (preview)
    - Pattern matching for `switch`

---

## Dependencies & Libraries
- **Spring Boot:** 2.5.x (current), upgrade to 3.x for Java 17
- **MapStruct:** 1.4.2.Final, upgrade to 1.6.x for Java 17+
- **Lombok:** Use only when necessary (`@Data`, `@Builder`)
- **JUnit:** Prefer JUnit 5 for new code; migrate old JUnit 4 tests
- **ErrorProne:** 2.15+ for Java 11, 2.36+ for Java 17
- **Jackson:** 2.12.x, upgrade for Java 17
- **OpenRewrite recipes:** automate code migration
- **Other tools:** SpotBugs, Maven plugins compatible with target Java version

---

## Coding Style
- Use **final** for local variables and class fields whenever possible.
- Use `Optional<T>` for nullable return values.
- Keep methods small; follow single-responsibility principle.
- Prefer **immutable collections** (`List.of`, `Set.of`, `Map.of`).
- Constructor injection in Spring, avoid field injection.
- Descriptive names for classes, methods, variables.
- Avoid wildcard imports (`import java.util.*`).
- Use Streams API where it improves readability.
- Apply consistent Java naming conventions.

---

## Testing Guidelines
- Prefer **JUnit 5** with AssertJ for fluent assertions.
- Use **Mockito** for mocking dependencies.
- Avoid mixing JUnit 4 and JUnit 5.
- Prefer parameterized tests for repetitive scenarios.
- Use in-memory DBs like H2 for unit tests when testing repositories.

---

## Migration & Automation
- Use **OpenRewrite recipes** to automate migration:
    - Java 8 → 11 → 17 → 21
    - Spring Boot 2.5 → 3.x
    - JUnit 4 → JUnit 5
- Track migration in **incremental commits**.
- Apply **rewrite-maven-plugin** for automated recipes.

---

## Build & Compiler Recommendations
- Use Maven Compiler Plugin:
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.11.0</version>
  <configuration>
    <release>11</release>
    <annotationProcessorPaths>
      <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.20</version>
      </path>
      <path>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.4.2.Final</version>
      </path>
    </annotationProcessorPaths>
  </configuration>
</plugin>
