## Records 17
- RecordsIntro.java 
    - ✅ Value Objects, 🪦 @Value (Lombok)
- Records.java 
    - ✅ DTOs
    - Optional<> field!
    - ❌ @Entity, but ✅ @Embeddable (JPA)
    - ✅ @Document (Mongo)
    - ❌ @Service & co (Spring)
- MicroTypes.java
    - ✅ vs Tuple (RX)
  
## Immutable Collections 11,17
- ImmutableCollections.java 

## Text Blocks 17,25
- BookRepo.java: ✅ @Query
- IntegrationTest.java: ✅ JSON 
    + "%s".formatted(a)
    + STR."\{a}" 💖

## Switch (enum) idiom 17
- switch(enum)
    - string -> enum
    - BUG! 0
    - polymorphism
    - enum + abstract
    - enum + Function
    - return switch
    Ideal: variable calculations per a type/code

## Switch Pattern Matching 21
switch(sealed classes)
    - shapes
    - expr
    Ideal: behavior operating on a hierarchy of objects that can't go IN that hierarchy (OOP).

## Virtual Threads 21
- WebFlux .fetchUser.map(getprefs).ifEmptyDefault(...).flatMap(getBeer()).doOnNext
- CompletableFuture1+2.combine => scope => cf + newVirtualThreads
Ideal: systems serving many requests/second and/or talking to slow systems.
! synchronized
! CPU-bound flows