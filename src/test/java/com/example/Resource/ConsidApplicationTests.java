package com.example.Resource;

import Model.Category;
import Model.Employees;
import Model.LibraryItem;
import Reprositories.EmployeesRepository;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest
@RunWith(SpringRunner.class)
@SpringBootTest
class ConsidApplicationTests {

    @Mock
    Services.LibraryService service;

    @InjectMocks
    Controller.Controller employeeController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreateEmployee() {
        Employees employee = new Employees();
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setManager(false);
        employee.setCeo(false);
        employee.setManagerId(1);
        employee.setRank(5);


        Employees returnedEmployee = service.createEmployee(employee);

        assertEquals("John", returnedEmployee.getName());
        assertEquals("Doe", returnedEmployee.getLastName());
        assertEquals(false, returnedEmployee.getManager());
        assertEquals(false, returnedEmployee.getCeo());
        assertEquals(1, returnedEmployee.getManagerId());
        assertEquals(5, returnedEmployee.getRank());
        assertEquals(String.valueOf(5625), returnedEmployee.getSalary());
    }

    @Test
    public void testSaveLibraryItem() {
        LibraryItem item = new LibraryItem();
        item.setTitle("Test Item");
        item.setAuthor("Test Author");
        item.setPages(100);
        item.setType("Test Type");
        item.setBorrowable(true);
        item.setBorrowDate(new Date());
        item.setBorrower("Test Borrower");
        item.setRunTimeMinutes(60);
        item.setCategory(new Category(1L, "Test Category"));

        LibraryItem savedItem = service.createLibraryItem(item);
        Assertions.assertNotNull(savedItem);
        assertEquals(item.getTitle(), savedItem.getTitle());
        assertEquals(item.getAuthor(), savedItem.getAuthor());
        assertEquals(item.getPages(), savedItem.getPages());
        assertEquals(item.getType(), savedItem.getType());
        assertEquals(item.getBorrowDate(), savedItem.getBorrowDate());
        assertEquals(item.getBorrower(), savedItem.getBorrower());
        assertEquals(item.getRunTimeMinutes(), savedItem.getRunTimeMinutes());
        assertEquals(item.getCategory(), savedItem.getCategory());
    }
}
