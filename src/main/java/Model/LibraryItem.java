package Model;

import jakarta.persistence.*;

import java.util.Date;

@NamedQueries({
        @NamedQuery(

                name = LibraryItem.findByCategory, query = "SELECT i FROM LibraryItem i WHERE i.category.id = :categoryId")
})

@Entity
@Table(name = "library_item")
public class LibraryItem {

    public static final String findByCategory = "LibraryItem.findByCategory";

    @Id
    @Column(name = "idLibraryItem", nullable = false, precision = 0)
    private int id;

    @Basic
    @Column(name = "Title")
    private String title;
    @Basic
    @Column(name = "Author")
    private String author;

    @Basic
    @Column(name = "Pages")
    private int pages;

    @Basic
    @Column(name = "Typle")
    private String type;

    @Basic
    @Column(name = "isBorrowable")
    private boolean isBorrowable;

    @Basic
    @Column(name = "BorrowDate")
    private Date borrowDate;

    @Basic
    @Column(name = "Borrower")
    private String borrower;

    @Basic
    @Column(name = "RunTimeMinutes")
    private int runTimeMinutes;

    @ManyToOne
    @JoinColumn(name = "Category_id")
    private Category category;

    private String acronym;


    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBorrowable() {
        return isBorrowable;
    }

    public void setBorrowable(boolean borrowable) {
        isBorrowable = borrowable;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getRunTimeMinutes() {
        return runTimeMinutes;
    }

    public void setRunTimeMinutes(int runTimeMinutes) {
        this.runTimeMinutes = runTimeMinutes;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String generateAcronym() {
        StringBuilder acronym = new StringBuilder();
        String[] words = this.title.split(" ");
        for (String word : words) {
            acronym.append(word.charAt(0));
        }
        return acronym.toString();
    }
}
