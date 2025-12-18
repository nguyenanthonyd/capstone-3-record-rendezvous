


# 🎵 Record Rendezvous – E-Commerce API (Minimum Viable Product)

## 📖 Project Description

**Record Rendezvous** is a Spring Boot–based RESTful API for an online record store, built as **Capstone 3** for the Year Up United Java / Code Academy program.

The API allows users to browse music records by category while enabling administrators to securely manage products and categories. It uses **JWT authentication**, **role-based authorization**, and a **MySQL database**, demonstrating real-world backend development practices.

⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻
## 🖥️ Application Screens

> Screenshots below show the application tested using Postman and MySQL.

* **User Login (JWT Authentication)**
  ![Postman Login](images/postman-login.png)

* **View Categories**
  ![Get Categories](images/get-categories.png)

* **View Products (Records)**
  ![Get Products](images/get-products.png)

* **Database Tables**
  ![MySQL Tables](images/mysql-tables.png)

⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻

## ⭐ Interesting Code Highlight

A key feature of this project is the **DAO (Data Access Object) pattern**, which separates database logic from controllers and services.

```java
@Component
public class JdbcProductDao implements ProductDao {

    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, name, price, category_id FROM products";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("category_id")
                ));
            }
        }
        return products;
    }
}
⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻

* Keeps database logic isolated
* Uses JDBC with safe resource handling
* Makes the application easier to maintain and extend

⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻

   Technologies Used

* Java 17
* Spring Boot & Spring Security (JWT)
* JDBC / DAO Pattern
* MySQL
* Maven
* Postman

⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻⸻

  Future Improvements

- Order checkout and payment processing
- Inventory tracking and low-stock alerts
- Admin dashboard
- Frontend integration
- Expanded testing coverage

