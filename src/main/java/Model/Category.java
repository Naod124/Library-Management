package Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Basic
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "CategoryName")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<LibraryItem> libraryItems;

    // Getters and setters


    public Category() {
    }

    public Category(long l, String test_category) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public void setLibraryItems(List<LibraryItem> libraryItems) {
        this.libraryItems = libraryItems;
    }
}
