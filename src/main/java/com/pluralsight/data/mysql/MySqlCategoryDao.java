package com.pluralsight.data.mysql;


import org.springframework.stereotype.Component;
import com.pluralsight.data.CategoryDao;
import com.pluralsight.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        // get all categories
        return null;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException { // replaced double curly brace code with cleaner code
        Category category = new Category();
        category.setCategoryId(row.getInt("category_id"));
        category.setName(row.getString("name"));
        category.setDescription(row.getString("description"));
        return category;
    }





    }
