


# 🎵 Record Rendezvous – E-Commerce API (Minimum Viable Product)

## 📖 Project Description

**Record Rendezvous** is a Spring Boot–based RESTful API for an online record store, built as **Capstone 3** for the Year Up United Java / Code Academy program.

The API allows users to browse music records by category while enabling administrators to securely manage products and categories. It uses **JWT authentication**, **role-based authorization**, and a **MySQL database**, demonstrating real-world backend development practices.


## 🖥️ Application Screens

> Screenshots below show the application tested using Postman and MySQL.

* **User Login (JWT Authentication)**
  <img width="884" height="875" alt="Screenshot 2025-12-18 135534" src="https://github.com/user-attachments/assets/946b357d-33ad-4f9e-84ef-8f4e303b237d" />


* **View Categories**
  <img width="883" height="871" alt="Screenshot 2025-12-18 135616" src="https://github.com/user-attachments/assets/172b41d4-eb67-4ec0-a689-87155347985d" />


* **View Products (Records)**
  <img width="886" height="867" alt="Screenshot 2025-12-18 140602" src="https://github.com/user-attachments/assets/8b15c926-5a8c-4fad-8070-d5e62c0561a2" />


* **Database Tables**
 <img width="1362" height="809" alt="Screenshot 2025-12-18 140801" src="https://github.com/user-attachments/assets/a17a8a3d-3e3e-403a-8ec5-ded0688881a2" />



## ⭐ Interesting Code Highlight

A key feature of this project is the **DAO (Data Access Object) pattern**, which separates database logic from controllers and services. (Java)


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


* Keeps database logic isolated
* Uses JDBC with safe resource handling
* Makes the application easier to maintain and extend


   ## Technologies Used

* Java 17
* Spring Boot & Spring Security (JWT)
* JDBC / DAO Pattern
* MySQL
* Maven
* Postman


  ## Future Improvements

- Order checkout and payment processing
- Inventory tracking and low-stock alerts
- Admin dashboard
- Frontend integration
- Expanded testing coverage

