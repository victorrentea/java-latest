## Records (17)
- RecordsIntro.java 
    - ✅ Value Objects, RIP @Value (Lombok)
- Records.java 
    - ✅ DTOs
    - Optional<> field!
    - ❌ @Entity, but ✅ @Embeddable (JPA)
    - ✅ @Document (Mongo)
    - ❌ @Service & co (Spring)
- Records4MicroTypes.java
    - ✅ vs Tuple (RX)
  
## Immutable Collections (11, 17)

## Text Blocks (17, 25)
- ✅ @Query (BookRepo.java)
- ✅ JSON (IntegrationTest.java) 
- "%s".formatted(a)
- STR."\{a}" 💖 (25)

## Switch (enum) expression (17)
- no need for 'default': compiler fails on missing branch💖

## Switch on Sealed (21)
- ✅ Behavior on a hierarchy of objects that can't go INSIDE the classes

## Virtual Threads (21)
- WebFlux .fetchUser.map(getprefs).ifEmptyDefault(...).flatMap(getBeer()).doOnNext
- CompletableFuture1+2.combine => scope => cf + newVirtualThreads
Ideal: systems serving many requests/second and/or talking to slow systems.
! synchronized
! CPU-bound flows

## Structured Concurrency (25)
