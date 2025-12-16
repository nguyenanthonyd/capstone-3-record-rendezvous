package com.pluralsight.controller;

import com.pluralsight.data.CategoryDao;
import com.pluralsight.data.ProductDao;
import com.pluralsight.model.Category;
import com.pluralsight.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // GET http://localhost:8080/categories
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Category> getAll() {
        try {
            return categoryDao.getAllCategories();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // GET http://localhost:8080/categories/1
    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id) {
        try {
            Category category = categoryDao.getById(id);
            if (category == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return category;
        } catch (ResponseStatusException ex) {
            throw ex; // keep 404s
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // GET http://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    @PreAuthorize("permitAll()")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        try {
            return productDao.listByCategoryId(categoryId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // POST http://localhost:8080/categories  (ADMIN only)
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        try {
            return categoryDao.create(category);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // PUT http://localhost:8080/categories/1  (ADMIN only)
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {
            // optional: make sure it exists first
            Category existing = categoryDao.getById(id);
            if (existing == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.update(id, category);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // DELETE http://localhost:8080/categories/1  (ADMIN only)
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id) {
        try {
            Category existing = categoryDao.getById(id);
            if (existing == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.delete(id);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
