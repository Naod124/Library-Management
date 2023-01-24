package Reprositories;

import Model.Category;
import Model.LibraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long> {

//    List<LibraryItem> findByCategory(@Param("name") Category name);

    boolean existsByCategoryId(int categoryId);
    List<LibraryItem> findByCategory(Category category);
    //findAllByOrderByCategoryNameAsc
    List<LibraryItem> findAllByOrderByType();

    List<LibraryItem> findAll();



}
