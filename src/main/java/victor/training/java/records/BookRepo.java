package victor.training.java.records;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java.records.BookApi.SearchBookResult;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
  @Query("select new BookApi$SearchBookResult(book.id, book.title)\n" +
         "from Book book\n" +
         "where UPPER(book.name) LIKE UPPER('%' || ?1 || '%')")
  List<SearchBookResult> search(String name);

  //region complex native SQL
  @Query(nativeQuery = true, value =
      """
          select t.id
          from TEACHER t
          where (?1 is null or upper(t.name) like upper(('%'||?1||'%')))
          and (?2 is null or t.grade=?2)
          and (cast(?3 as integer)=0 or exists
               select 1
               from TEACHING_ACTIVITY ta
               inner join TEACHING_ACTIVITY_TEACHER tat on ta.id=tat.activities_id
               inner join TEACHER tt on tat.teachers_id=tt.id
               where ta.discr='COURSE'
               and tt.id=t.id))
          """)
  List<Long> complexQuery(String namePart, Integer grade, boolean teachingCourses);
  //endregion
}
