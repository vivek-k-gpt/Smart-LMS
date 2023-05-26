package com.gl.smartlms.restControllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.CategoryRepository;
import com.gl.smartlms.restController.CategoryRestController;
import com.gl.smartlms.service.CategoryService;
import com.gl.smartlms.service.UserService;
@SpringBootTest
public class CategoryRestControllerTest {
    @InjectMocks
    CategoryRestController catcon;
    @Mock
    CategoryRepository catrepo;
    public Category buildCategory() {
        Category category = new Category();
        category.setId(1l);
        category.setName("comic");
        category.setShortName("com");
        category.setNotes("This contains Comic books");
        return category;
    }
    @Test
    public void saveUserTest() {
        Category category = buildCategory();
        when(catrepo.save(category)).thenReturn(category);
        assertEquals(category, catrepo.save(category));
    }
    @Test
    public void showAllmemberTest() {
        List<Category> clist = new ArrayList<>();
        Category cat = buildCategory();
        clist.add(cat);
        when(catrepo.findAll()).thenReturn(clist);
        assertEquals(1, catrepo.findAll().size());
    }
    @Test
    public void showAllCategorySortedByNameTest() {
        List<Category> clist = new ArrayList<>();
        when(catrepo.findAllByOrderByNameAsc()).thenReturn(clist);
        assertEquals(0, catrepo.findAllByOrderByNameAsc().size());
    }
    @Test
    public void countAllCategoryTest()
    {
        when(catrepo.count()).thenReturn((long) 2);
        
        assertEquals(2,catrepo.count());
    }
    @Test
    public void findCategoryByIdTest() {
        Optional<Category> category = Optional.ofNullable(buildCategory());
        when(catrepo.findById((long) 1)).thenReturn(category);
        assertEquals(category, catrepo.findById((long) 1));
    }
    @Test
    public void deleteCategoryByIdTest() {
        Optional<Category> category = Optional.ofNullable(buildCategory());
        catrepo.deleteById((long) 1);
        verify(catrepo, times(1)).deleteById((long) 1);
    }
}