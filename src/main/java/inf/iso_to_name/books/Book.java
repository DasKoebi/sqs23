package inf.iso_to_name.books;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="name")
    private String name;

    @Column(unique = true, name = "isbn10")
    private String isbn10;
    @Column(unique = true, name = "isbn13")

    private String isbn13;

    public Book() {
        this.name = "";
    }

    public long getId() {
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

}
