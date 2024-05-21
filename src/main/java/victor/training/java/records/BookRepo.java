package victor.training.java.records;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java.records.BookApi.GetBookResponse;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
  @Query("select new BookApi$GetBookResponse(b.id, b.title)\n" +
         "from Book b\n" +
         "where b.id = :id")
  GetBookResponse getBookById(Long id);

  //region complex native SQL
  @Query(nativeQuery = true, value =
      "select t.id\n" +
      "from TEACHER t\n" +
      "where (?1 is null or upper(t.name) like upper(('%'||?1||'%')))\n" +
      "and (?2 is null or t.grade=?2)\n" +
      "and (cast(?3 as integer)=0 or exists\n" +
      "     select 1\n" +
      "     from TEACHING_ACTIVITY ta\n" +
      "     inner join TEACHING_ACTIVITY_TEACHER tat on ta.id=tat.activities_id\n" +
      "     inner join TEACHER tt on tat.teachers_id=tt.id\n" +
      "     where ta.discr='COURSE'\n" +
      "     and tt.id=t.id))\n")
  List<Long> complexQuery(String namePart, Integer grade, boolean teachingCourses);
  //endregion
}
