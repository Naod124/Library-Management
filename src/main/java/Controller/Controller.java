package Controller;

import Model.Category;
import Model.Employees;
import Model.LibraryItem;
import Services.LibraryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@ComponentScan({"Services", "Controller", "Reprositories"})
@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@RestController
@RequestMapping("/api")
public class Controller {


    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

    @Autowired
    private LibraryService service;


    //CATEGORIES

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return service.getAllCategories();
    }

    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category) {
        return service.createCategory(category);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody Category categoryDetails) {
        return service.updateCategory(categoryId, categoryDetails);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

    //LIBRARY ITEMS

    @GetMapping("/library-items")
    public List<LibraryItem> getAllLibraryItems(@RequestParam(value = "sortBy", required = false) String sortBy) {
        return service.getAllLibraryItems();
    }

    @PostMapping("/library-items")
    public LibraryItem createLibraryItem(@Valid @RequestBody LibraryItem libraryItem) {
        return service.createLibraryItem(libraryItem);
    }

    @PutMapping("/library-items/{id}")
    public LibraryItem updateLibraryItem(@PathVariable(value = "id") int libraryItemId, @Valid @RequestBody LibraryItem libraryItemDetails) {
        return service.updateLibraryItem(Long.valueOf(libraryItemId), libraryItemDetails);
    }

    @DeleteMapping("/library-items/{id}")
    public ResponseEntity<?> deleteLibraryItem(@PathVariable(value = "id") int libraryItemId) {
        service.deleteLibraryItem(Long.valueOf(libraryItemId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/library-items/{id}/check-out")
    public LibraryItem checkOutLibraryItem(@PathVariable(value = "id") int libraryItemId, @Valid @RequestBody String borrower) {
        return service.checkOutLibraryItem(Long.valueOf(libraryItemId), borrower);
    }

    @PutMapping("/library-items/{id}/check-in")
    public LibraryItem checkInLibraryItem(@PathVariable(value = "id") int libraryItemId) {
        return service.checkInLibraryItem(Long.valueOf(libraryItemId));
    }

    //EMPLOYEES

    @GetMapping("/role/{role}")
    public List<Employees> getEmployeesByRole(@PathVariable String role) {
        return service.getEmployeesByRole(role);
    }

    @PostMapping
    public ResponseEntity<Employees> createEmployee(@Valid @RequestBody Employees employee) {
        Employees newEmployee = service.createEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employees> updateEmployee(@Valid @RequestBody Employees employee) {
        Employees updatedEmployee = service.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }


    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int employeeId) {
        service.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }
}

