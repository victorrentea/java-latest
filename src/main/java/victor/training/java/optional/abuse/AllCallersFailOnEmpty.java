package victor.training.java.optional.abuse;


import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AllCallersFailOnEmpty {
  //@Id
  // Spring pretend
  interface JpaRepository<T,PK>  {
    Optional<T> findById(PK id);
  }
  private interface BaseRepo<T,PK> extends JpaRepository<T, PK> {
    @SuppressWarnings("unchecked")
    default T findOneById(PK id) {
      // in java 8 interfaces can have methods with implementation
      // if all your callers fail when you return Optional.empty()
      //  consider throwing an exception for your callers' convenience.
      Optional<T> opt = findById(id);
      return opt.orElseThrow(() -> createException(id));
    }

    private NoSuchElementException createException(PK id) {
      Class<T> persistentClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      // runtime
      return new NoSuchElementException(persistentClass.getSimpleName() + " with id " + id + " not found ");
//      return new IOException();
    }
  }

  //@Entity // pretend
  private static class Tenant {
    Long id;
  }
  // original
//  private interface TenantRepo extends JpaRepository<Tenant, Long> {
  // new: TenantRepo --> BaseRepo --> JpaRepository
  private interface TenantRepo extends BaseRepo<Tenant, Long> {
  }


  private TenantRepo tenantRepo; // talks to DB

  public void flow1(long tenantId) {
//    Tenant tenant = tenantRepo.findById(tenantId).orElseThrow(); // .get() throws if Optional is empty
    Tenant tenant = tenantRepo.findOneById(tenantId); // .get() throws if Optional is empty
    // SELECT * FROM Tenant WHERE id = ?
    System.out.println("Stuff1 with tenant: " + tenant);
  }

  public void flow2(long tenantId) {
//    Tenant tenant = tenantRepo.findById(tenantId).orElseThrow(); // + 30 more places in a typical project
    Tenant tenant = tenantRepo.findOneById(tenantId); // + 30 more places in a typical project
    System.out.println("Stuff2 with tenant: " + tenant);
  }
}
