package inf.iso_to_name.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByisbn10(String isbn10);
    List<Book> findByisbn13(String isbn13);
}
