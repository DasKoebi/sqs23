package inf.isoToName.books;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Book {
    @Id
    @GeneratedValue
    @Schema(description = "ID of Book", exmaple = "10");
    private long id;

    @NotBlank
    @Schema(description = "Name of the Book", exmaple = "Harry Potter")
    @Column(unique = false, nullable = false)
    private String name;

    @NotBlank
    @Schema(description = "ISBN-10 of the Book", example = "1234567890")
    @Column(unique = true, nullable = false)
    private int isbn_10;

    @NotBlank
    @Schema(description = "ISBN-13 of the Book", example = "1234567890123")
    @Column(unique = true, nullable = false)
    private int isbn_13;

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

    public int getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(int isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public int getIsbn_13() {
        return isbn_13;
    }

    public void setIsbn_13(int isbn_13) {
        this.isbn_13 = isbn_13;
    }

}
