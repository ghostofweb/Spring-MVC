# Spring MVC Complete Guide with Database Integration

## Table of Contents
1. [Introduction to Spring MVC](#introduction-to-spring-mvc)
2. [Database Integration with JDBC](#database-integration-with-jdbc)
3. [JPA and Hibernate](#jpa-and-hibernate)
4. [Entity Models and Annotations](#entity-models-and-annotations)
5. [Lombok Basics](#lombok-basics)
6. [N-Tier Architecture](#n-tier-architecture)
7. [Repository Layer](#repository-layer)
8. [Service Layer](#service-layer)
9. [Controller Layer](#controller-layer)
10. [Complete Example Implementation](#complete-example-implementation)

## Introduction to Spring MVC

Spring MVC (Model-View-Controller) is a web framework built on the Servlet API and has been included in the Spring Framework from the beginning. It provides a powerful and flexible way to build web applications following the MVC architectural pattern.

### Key Components:
- **Model**: Represents data and business logic
- **View**: Handles the presentation layer
- **Controller**: Manages user input and coordinates between Model and View

### Core Features:
- Annotation-based configuration
- Flexible view resolution
- Data binding and validation
- RESTful web services support
- Integration with various view technologies

## Database Integration with JDBC

JDBC (Java Database Connectivity) is the fundamental API for database access in Java applications. Spring provides excellent JDBC support through its JdbcTemplate and other utilities.

### Spring JDBC Benefits:
- Simplified exception handling
- Resource management (connection, statement, result set)
- Transaction management
- Reduced boilerplate code

### Basic JDBC Configuration:

```java
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/runnerapp");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

## JPA and Hibernate

JPA (Java Persistence API) is a specification for managing relational data in Java applications. Hibernate is the most popular JPA implementation.

### Key Advantages:
- Object-Relational Mapping (ORM)
- Automatic SQL generation
- Caching mechanisms
- Lazy loading
- Transaction management

### JPA Configuration:

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/runnerapp
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## Entity Models and Annotations

Entity models represent database tables as Java classes. JPA provides various annotations to map these classes to database structures.

### Core JPA Annotations:

#### @Entity
Marks a class as a JPA entity that should be mapped to a database table.

#### @Table
Specifies the table name and schema information.

#### @Id
Designates the primary key field.

#### @GeneratedValue
Configures automatic primary key generation strategies:
- `GenerationType.IDENTITY`: Uses database auto-increment
- `GenerationType.SEQUENCE`: Uses database sequences
- `GenerationType.AUTO`: JPA provider chooses the strategy
- `GenerationType.TABLE`: Uses a separate table for key generation

#### Hibernate-Specific Annotations:

##### @CreationTimestamp
Automatically sets the timestamp when the entity is first persisted.

##### @UpdateTimestamp
Automatically updates the timestamp whenever the entity is modified.

### Example Entity Implementation:

```java
package com.runnerapp.web.models;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clubs")
public class Club {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String photoUrl;
    private String content;
    
    @CreationTimestamp
    private LocalDateTime createdOn;
    
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
```

## Lombok Basics

Lombok is a Java library that automatically generates boilerplate code during compilation, reducing the amount of repetitive code you need to write.

### Key Lombok Annotations:

#### @Data
Generates:
- Getters for all fields
- Setters for all non-final fields
- toString() method
- equals() and hashCode() methods
- A constructor for required fields

#### @NoArgsConstructor
Generates a no-argument constructor.

#### @AllArgsConstructor
Generates a constructor with parameters for all fields.

#### @Builder
Implements the Builder pattern for object creation.

### Example Usage:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
}

// Usage with Builder pattern
User user = User.builder()
    .username("john_doe")
    .email("john@example.com")
    .password("securePassword")
    .build();
```

### Other Useful Lombok Annotations:

- `@Getter` / `@Setter`: Generate getters/setters for specific fields
- `@ToString`: Generate toString() method
- `@EqualsAndHashCode`: Generate equals() and hashCode() methods
- `@RequiredArgsConstructor`: Constructor for required fields only

## N-Tier Architecture

N-Tier architecture separates application logic into distinct layers, each with specific responsibilities. This promotes maintainability, testability, and scalability.

### Common Layers:

1. **Presentation Layer (Controllers)**
   - Handles HTTP requests and responses
   - Input validation
   - Request routing

2. **Business/Service Layer**
   - Contains business logic
   - Orchestrates data access
   - Implements business rules

3. **Data Access Layer (Repository)**
   - Handles database operations
   - Data persistence logic
   - Query implementation

4. **Database Layer**
   - Actual data storage
   - Database schema and constraints

### Benefits:
- **Separation of Concerns**: Each layer has a specific responsibility
- **Maintainability**: Changes in one layer don't affect others
- **Testability**: Each layer can be tested independently
- **Scalability**: Layers can be scaled independently
- **Reusability**: Business logic can be reused across different presentation layers

## Repository Layer

The Repository layer provides an abstraction over data access logic. Spring Data JPA simplifies repository implementation significantly.

### Spring Data JPA Repository Hierarchy:

1. **Repository<T, ID>**: Base interface
2. **CrudRepository<T, ID>**: Basic CRUD operations
3. **PagingAndSortingRepository<T, ID>**: Adds pagination and sorting
4. **JpaRepository<T, ID>**: JPA-specific operations

### Example Repository Implementation:

```java
package com.runnerapp.web.repository;

import com.runnerapp.web.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    
    // Query methods using method naming convention
    Optional<Club> findByTitle(String title);
    List<Club> findByTitleContaining(String title);
    List<Club> findByTitleContainingIgnoreCase(String title);
    
    // Custom JPQL queries
    @Query("SELECT c FROM Club c WHERE c.title LIKE %:title%")
    List<Club> findClubsByTitleContaining(@Param("title") String title);
    
    // Native SQL queries
    @Query(value = "SELECT * FROM clubs WHERE title LIKE %:title%", nativeQuery = true)
    List<Club> findClubsByTitleNative(@Param("title") String title);
    
    // Count queries
    long countByTitleContaining(String title);
    
    // Delete queries
    void deleteByTitle(String title);
}
```

### Query Method Keywords:

- `findBy`: Find entities matching criteria
- `countBy`: Count entities matching criteria
- `deleteBy`: Delete entities matching criteria
- `existsBy`: Check if entities exist matching criteria

### Query Method Expressions:

- `And`, `Or`: Logical operators
- `Between`: Range queries
- `LessThan`, `GreaterThan`: Comparison operators
- `Like`, `NotLike`: String matching
- `IsNull`, `IsNotNull`: Null checks
- `In`, `NotIn`: Collection membership
- `OrderBy`: Sorting

## Service Layer

The Service layer contains business logic and orchestrates interactions between controllers and repositories.

### Service Layer Responsibilities:
- Implement business rules
- Coordinate multiple repository operations
- Handle transactions
- Perform data validation
- Transform data between layers

### Example Service Implementation:

```java
package com.runnerapp.web.service;

import com.runnerapp.web.models.Club;
import com.runnerapp.web.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClubService {
    
    private final ClubRepository clubRepository;
    
    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }
    
    public List<Club> findAllClubs() {
        return clubRepository.findAll();
    }
    
    public Optional<Club> findClubById(Long id) {
        return clubRepository.findById(id);
    }
    
    public Club saveClub(Club club) {
        // Business logic validation
        if (club.getTitle() == null || club.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Club title cannot be empty");
        }
        
        // Check for duplicate titles
        Optional<Club> existingClub = clubRepository.findByTitle(club.getTitle());
        if (existingClub.isPresent() && !existingClub.get().getId().equals(club.getId())) {
            throw new IllegalArgumentException("Club with this title already exists");
        }
        
        return clubRepository.save(club);
    }
    
    public Club updateClub(Long id, Club clubDetails) {
        Club club = clubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));
        
        club.setTitle(clubDetails.getTitle());
        club.setPhotoUrl(clubDetails.getPhotoUrl());
        club.setContent(clubDetails.getContent());
        
        return clubRepository.save(club);
    }
    
    public void deleteClub(Long id) {
        Club club = clubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));
        
        clubRepository.delete(club);
    }
    
    public List<Club> searchClubs(String title) {
        return clubRepository.findByTitleContainingIgnoreCase(title);
    }
    
    @Transactional(readOnly = true)
    public long getClubCount() {
        return clubRepository.count();
    }
}
```

### Service Layer Best Practices:

1. **Use Constructor Injection**: Prefer constructor injection over field injection
2. **Transaction Management**: Use `@Transactional` appropriately
3. **Exception Handling**: Throw meaningful exceptions
4. **Validation**: Implement business rule validation
5. **Single Responsibility**: Each service should have a focused responsibility

## Controller Layer

The Controller layer handles HTTP requests and responses, serving as the entry point for web requests.

### Controller Responsibilities:
- Handle HTTP requests
- Validate input data
- Call appropriate service methods
- Return appropriate responses
- Handle exceptions

### Example Controller Implementation:

```java
package com.runnerapp.web.controller;

import com.runnerapp.web.models.Club;
import com.runnerapp.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clubs")
@CrossOrigin(origins = "http://localhost:3000")
public class ClubController {
    
    private final ClubService clubService;
    
    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }
    
    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.findAllClubs();
        return ResponseEntity.ok(clubs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        Optional<Club> club = clubService.findClubById(id);
        return club.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Club> createClub(@Valid @RequestBody Club club) {
        try {
            Club savedClub = clubService.saveClub(club);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, 
                                          @Valid @RequestBody Club clubDetails) {
        try {
            Club updatedClub = clubService.updateClub(id, clubDetails);
            return ResponseEntity.ok(updatedClub);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        try {
            clubService.deleteClub(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Club>> searchClubs(@RequestParam String title) {
        List<Club> clubs = clubService.searchClubs(title);
        return ResponseEntity.ok(clubs);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getClubCount() {
        long count = clubService.getClubCount();
        return ResponseEntity.ok(count);
    }
}
```

### Controller Annotations:

- `@RestController`: Combines `@Controller` and `@ResponseBody`
- `@RequestMapping`: Maps HTTP requests to handler methods
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: HTTP method-specific mappings
- `@PathVariable`: Extracts values from URI template variables
- `@RequestParam`: Extracts query parameters
- `@RequestBody`: Binds HTTP request body to method parameter
- `@Valid`: Triggers validation on the bound object

## Complete Example Implementation

### Project Structure:
```
src/main/java/com/runnerapp/web/
├── RunnerAppApplication.java
├── config/
│   └── DatabaseConfig.java
├── models/
│   ├── Club.java
│   └── User.java
├── repository/
│   ├── ClubRepository.java
│   └── UserRepository.java
├── service/
│   ├── ClubService.java
│   └── UserService.java
└── controller/
    ├── ClubController.java
    └── UserController.java
```

### Additional Entity Example:

```java
package com.runnerapp.web.models;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    @CreationTimestamp
    private LocalDateTime createdOn;
    
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    
    public enum Role {
        USER, ADMIN, MODERATOR
    }
}
```

### Main Application Class:

```java
package com.runnerapp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunnerAppApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RunnerAppApplication.class, args);
    }
}
```

### Dependencies (pom.xml):

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

## Best Practices and Tips

### Entity Design:
- Use appropriate data types
- Implement proper relationships (`@OneToMany`, `@ManyToOne`, etc.)
- Use validation annotations (`@NotNull`, `@Size`, etc.)
- Consider using DTOs for API responses

### Repository Layer:
- Keep repositories focused on data access
- Use method naming conventions for simple queries
- Write custom queries for complex operations
- Consider performance implications of queries

### Service Layer:
- Implement proper transaction boundaries
- Handle exceptions appropriately
- Keep business logic separate from data access
- Use dependency injection properly

### Controller Layer:
- Validate input data
- Handle exceptions gracefully
- Return appropriate HTTP status codes
- Use proper HTTP methods for different operations

### General:
- Follow Spring Boot conventions
- Use configuration properties for environment-specific settings
- Implement proper logging
- Write unit and integration tests
- Consider security aspects (authentication, authorization)

This comprehensive guide covers the essential aspects of building a Spring MVC application with database integration, following n-tier architecture principles and modern development practices.