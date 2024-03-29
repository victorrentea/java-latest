## Immutability
- Records.java
    Point > @Data > @Value
    Use for: domain value object
- MicroTypes.java
    TupleN
    Map<> used as List<Tuple2>
- RecordsInSpring.java
    - dtos
    - @Document
    - not for @Entity but yes for @Embeddable
    - not for @Service & friends 
    - Optional<> field
    - authors.clear() -> unmodifiable -> copyOf
- Lists.java: 
  - .add, .set -> List.of
  - ->.stream...toList()
- Maps.java

## Text Blocks
- RecordsInSpring.java """ 
    - @Query x 2
- IntegrationTest.java """   
    - .formatted
    - STR."
    Ideal: multi-line texts in code

## Switch (enum) idiom
- switch(enum)
    - text -> enum
    - BUG! 0
    - polymorph
    - enum + abstract
    - enum + Function
    - return switch
    Ideal: variable calculations per a type/code

## Switch Pattern Matching
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