### Records (17)
- ✅ Value Objects, RIP @Value (Lombok)
- ✅ DTOs
- 👍 Optional<> field!
- ❌ @Entity, but ✅ @Embeddable (JPA)
- ✅ @Document (Mongo)
- ❌ @Service & co (Spring)
- ✅ More semantics than Tuple (RX)
  
### Immutable Collections (11, 17)
- Towards more Functional Programming style 

### Text Blocks (17, 25)
- ✅ @Query (BookRepo.java)
- ✅ JSON (IntegrationTest.java) 
- ⏭️ STR."\{a}" (25)

### Switch (enum) expression (17)
- 💖 No break, no fallthrough
- 💖 Compiler fails on missing enum case => no need to default: throw

### Switch on Sealed (21)
- ✅ Behavior on a hierarchy of objects that can't go INSIDE the classes

### Virtual Threads (21)
- ✅ Cheap to block 
- ✅ Handle large number of requests with a single machine, without Reactive☠️
- ❌ synchronized in your code or old libraries 😱
- 😐 Useless for CPU-bound tasks

### Structured Concurrency (25)
- ⏭️ Fork-join visible for compiler: auto-cancellation, metadata propagation, profiling