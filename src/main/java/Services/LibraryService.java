package Services;

import Model.Category;
import Model.Employees;
import Model.LibraryItem;
import Reprositories.CategoryRepository;
import Reprositories.EmployeesRepository;
import Reprositories.LibraryItemRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LibraryItemRepository libraryItemRepository;

    @Autowired
    private EmployeesRepository employeeRepository;

    public LibraryService() {
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new BadRequestException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        if (existingCategory == null) {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found");
        }
        if (categoryRepository.existsByNameAndIdNot(categoryDetails.getName(), categoryId)) {
            throw new BadRequestException("Category with name " + categoryDetails.getName() + " already exists");
        }
        existingCategory.setName(categoryDetails.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        if (existingCategory == null) {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found");
        }
        if (!existingCategory.getLibraryItems().isEmpty()) {
            throw new BadRequestException("Category with id " + categoryId + " cannot be deleted, it is referenced by library items");
        }
        categoryRepository.delete(existingCategory);
    }

    public List<LibraryItem> getAllLibraryItems() {
        return libraryItemRepository.findAll();
    }

    public List<LibraryItem> getLibraryItemsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found");
        }
        return libraryItemRepository.findByCategory(category);
    }

    public LibraryItem checkInLibraryItem(Long libraryItemId) {
        LibraryItem existingLibraryItem = libraryItemRepository.findById(libraryItemId).orElse(null);
        if (existingLibraryItem == null) {
            throw new ResourceNotFoundException("Library item with id " + libraryItemId + " not found");
        }
        if (!existingLibraryItem.isBorrowable()) {
            throw new BadRequestException("Library item with id " + libraryItemId + " cannot be checked in, it is not borrowable");
        }
        if (existingLibraryItem.getBorrower() == null) {
            throw new BadRequestException("Library item with id " + libraryItemId + " cannot be checked in, it is not currently borrowed");
        }
        existingLibraryItem.setBorrower(null);
        existingLibraryItem.setBorrowDate(null);
        libraryItemRepository.save(existingLibraryItem);
        return existingLibraryItem;
    }

    public LibraryItem createLibraryItem(LibraryItem libraryItem) {
        if (libraryItem.getCategory() == null) {
            throw new BadRequestException("Library item must be assigned to a category");
        }
        if (!categoryRepository.existsById(libraryItem.getCategory().getId())) {
            throw new ResourceNotFoundException("Category with id " + libraryItem.getCategory().getId() + " not found");
        }
        libraryItem.setAcronym(libraryItem.getTitle().toUpperCase().replaceAll("[^A-Z]", ""));
        return libraryItemRepository.save(libraryItem);
    }

    public LibraryItem updateLibraryItem(Long libraryItemId, LibraryItem libraryItemDetails) {
        LibraryItem existingLibraryItem = libraryItemRepository.findById(libraryItemId).orElse(null);
        if (existingLibraryItem == null) {
            throw new ResourceNotFoundException("Library item with id " + libraryItemId + " not found");
        }
        if (libraryItemDetails.getCategory() != null) {
            if (!categoryRepository.existsById(libraryItemDetails.getCategory().getId())) {
                throw new ResourceNotFoundException("Category with id " + libraryItemDetails.getCategory().getId() + " not found");
            }
            existingLibraryItem.setCategory(libraryItemDetails.getCategory());
        }
        existingLibraryItem.setTitle(libraryItemDetails.getTitle());
        existingLibraryItem.setAuthor(libraryItemDetails.getAuthor());
        existingLibraryItem.setPages(libraryItemDetails.getPages());
        existingLibraryItem.setType(libraryItemDetails.getType());
        existingLibraryItem.setBorrowable(libraryItemDetails.isBorrowable());
        existingLibraryItem.setBorrowDate(libraryItemDetails.getBorrowDate());
        existingLibraryItem.setBorrower(libraryItemDetails.getBorrower());
        existingLibraryItem.setRunTimeMinutes(libraryItemDetails.getRunTimeMinutes());
        existingLibraryItem.setAcronym(existingLibraryItem.getTitle().toUpperCase().replaceAll("[^A-Z]", ""));
        return libraryItemRepository.save(existingLibraryItem);
    }

    public void deleteLibraryItem(Long libraryItemId) {
        LibraryItem existingLibraryItem = libraryItemRepository.findById(libraryItemId).orElse(null);
        if (existingLibraryItem == null) {
            throw new ResourceNotFoundException("Library item with id " + libraryItemId + " not found");
        }
        libraryItemRepository.delete(existingLibraryItem);
    }

    public LibraryItem checkOutLibraryItem(Long libraryItemId, String borrower) {
        LibraryItem existingLibraryItem = libraryItemRepository.findById(libraryItemId).orElse(null);
        if (existingLibraryItem == null) {
            throw new ResourceNotFoundException("Library item with id " + libraryItemId + " not found");
        }
        if (!existingLibraryItem.isBorrowable()) {
            throw new BadRequestException("Library item with id " + libraryItemId + " cannot be checked out, it is not borrowable");
        }
        if (existingLibraryItem.getBorrower() != null) {
            throw new BadRequestException("Library item with id " + libraryItemId + " cannot be checked out, it is already borrowed by " + existingLibraryItem.getBorrower());
        }
        existingLibraryItem.setBorrower(borrower);
        existingLibraryItem.setBorrowDate(new Date());
        libraryItemRepository.save(existingLibraryItem);
        return existingLibraryItem;
    }

    public Employees createEmployee(Employees employee) {
        if(employeeRepository.existsByIsCeoTrue()) {
            throw new BadRequestException("Cannot create more than one CEO");
        }
        if(employee.getCeo()) {
            employee.setSalary(String.valueOf(employee.getRank() * 2.725));
        } else if(employee.getManager()) {
            employee.setSalary(String.valueOf(employee.getRank() * 1.725));
        } else {
            employee.setSalary(String.valueOf(employee.getRank() * 1.125));
        }
        return employeeRepository.save(employee);
    }

    public Employees updateEmployee( Employees employee) {
        Employees existingEmployee = employeeRepository.findById(Long.valueOf(employee.getId())).orElse(null);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee with id " + employee.getId() + " not found");
        }

        if (employee.getCeo() && employeeRepository.existsByIsCeoTrueAndIdNot(employee.getId())) {
            throw new BadRequestException("Only one CEO can be created at a time in the application database");
        }
        if (employee.getManager() && employee.getManagerId() == 0) {
            throw new BadRequestException("Manager must have a manager id");
        }
        if (employee.getManagerId() != 0) {
            Employees manager = employeeRepository.findById(Long.valueOf(employee.getManagerId())).orElse(null);
            if (manager == null) {
                throw new BadRequestException("Manager with id " + employee.getManagerId() + " not found");
            }
            if (manager.getCeo()) {
                throw new BadRequestException("CEO cannot manage employees");
            }
        }
        existingEmployee.setName(employee.getName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setManager(employee.getManager());
        existingEmployee.setCeo(employee.getCeo());
        existingEmployee.setManagerId(employee.getManagerId());
        existingEmployee.setRank(employee.getRank());
        existingEmployee.setSalary(String.valueOf(calculateSalary(employee)));
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Integer id) {
        Employees existingEmployee = employeeRepository.findById(Long.valueOf(id)).orElse(null);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
        if(existingEmployee.getManager() || existingEmployee.getCeo()) {
            if(employeeRepository.findByManagerId(existingEmployee.getId())) {
                throw new BadRequestException("Cannot delete a manager or CEO that is managing another employee");
            }
        }
        employeeRepository.deleteById(Long.valueOf(id));
    }

    public List<Employees> getAllEmployees() {
        List<Employees> employees = employeeRepository.findAll();
        Map<String, List<Employees>> groupedEmployees = employees.stream()
                .collect(Collectors.groupingBy(employee -> employee.getCeo() ? "ceo" : employee.getManager() ? "manager" : "employee"));
        return (List<Employees>) groupedEmployees;
    }

    public double calculateSalary(Employees employee) {
        double salaryCoefficient;
        if (employee.getManager()) {
            salaryCoefficient = 1.725;
        } else if (employee.getCeo()) {
            salaryCoefficient = 2.725;
        } else {
            salaryCoefficient = 1.125;
        }
        return employee.getRank() * salaryCoefficient;
    }
    public List<Employees> getEmployeesByRole(String role) {
        if (role.equalsIgnoreCase("employee")) {
            return employeeRepository.findAllByIsManagerAndIsCeo(false, false);
        } else if (role.equalsIgnoreCase("manager")) {
            return employeeRepository.findAllByIsManagerAndIsCeo(true, false);
        } else if (role.equalsIgnoreCase("ceo")) {
            return employeeRepository.findAllByIsCeo(true);
        } else {
            throw new BadRequestException("Invalid role: " + role);
        }
    }
}





