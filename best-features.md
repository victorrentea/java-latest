- Records.java
    dtos
    domain value object
    @Document
    not for @Entity but yes for @Embeddable
- MicroTypes.java
    TupleN
    Map<> used as List<Tuple2>
- RecordsInSpring.java
    - not for @Service & friends 
    - Optional<> field
    - authors.clear() -> unmodifiable -> copyOf
- Lists.java 
    - .add .set -> .of
    - .stream.toList()
- Maps.java
    Ideal: Embracing immutability

- RecordsInSpring.java """ 
    - @Query + Dragons
    - @Test
    Ideal: multi-line texts in code

- switch(enum)
    - text -> enum
    - BUGul! 0
    - polymorph
    - enum + abstract
    - enum + Function
    - return switch
    Ideal: variable calculations per a type/code

switch(sealed classes)
    - shapes
    - expr
    Ideal: behavior operating on a hierarchy of objects that can't go IN that hierarchy (OOP).

virtual threads
    - WebFlux .fetchUser.map(getprefs).ifEmptyDefault(...).flatMap(getBeer()).doOnNext
    - CompletableFuture1+2.combine => scope => cf + newVirtualThreads
    Ideal: systems serving many requests/second and/or talking to slow systems.
    ! syncrhonized
    ! CPU🔥