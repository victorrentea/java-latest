## Immutability
- Lists.java: 
  - List.of over asList
  - .stream...toList() over .collect(toList());
- Maps.java
- Records.java
    Point > @Data > @Value > record
    Use for: domain value object
- MicroTypes.java
    - Tuple<>
    - @ParameterizedTest
- RecordsInSpring.java
    - ✅dtos 
    - Optional<> field
    - authors.clear() -> unmodifiableList -> ImmutableList(guava)
    - ❌not for @Entity but yes for @Embeddable
    - ❌not for @Service & friends 
    - ✅@Document , ✅jooq

## Text Blocks
- RecordsInSpring.java """ 
    - @Query x 2
- IntegrationTest.java """   
    - .format ted
    - STR." -> Java25
    Ideal: multi-line texts in code

## Switch (enum) idiom
- switch(enum) expression
    - text -> enum
    - BUG! 0
    - polymorph
    - enum + abstract
    - enum + Function
    - return switch
    Ideal: variable calculations per a type/code

## Switch Pattern Matching 21
switch(sealed classes)
    - shapes
    - expr
    Ideal: behavior operating on a hierarchy of objects that can't go IN that hierarchy (OOP).

## Virtual Threads
- WebFlux .fetchUser.map(getprefs).ifEmptyDefault(...).flatMap(getBeer()).doOnNext
- CompletableFuture1+2.combine => scope => cf + newVirtualThreads
Ideal: systems serving many requests/second and/or talking to slow systems.
! synchronized
! CPU-bound flows