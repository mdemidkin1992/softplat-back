#### Project Softplat
This is a project started at Yandex Accelerator by a full team of IT professionals, including project managers, UI/UX designers, frontend, backend, QA and system analysts. We completed the MVP stage during 4Q 2023 at Yandex Accelerator and plan to further develop our platform.
___

#### Links 
* Project website link (currently in Russian only): https://softplat24.ru
* Project repository: https://github.com/software-sales-and-installations/softplat-back
* Swagger API documentation: https://api.softplat24.ru/swagger-ui/#/
___

#### Project description
* Softplat is a marketplace platform for software sales and installation, which connects sellers of software, allowing them to distribute their products, and buyers, who can browse for products through catalogue, select their favourites and make purchases; 
* Backend functionality completed during two months at Yandex Accelerator (MVP): 
    * Three access roles to the system by admin, sellers and buyers, which allows different users to register on platform, create personal accounts and perform different actions based on their roles; 
    * Sellers can create their accounts, upload their products to the platform and market them to the buyers; 
    * Buyers can create their accounts, search and filter products through catalogue, add products to their favourites lists or shopping carts and perform purchases; 
    * Admin can add vendors to the platform, moderate products, send products for update to sellers and delete products from platform in case of complaints; 
    * Backend supports storage of uploaded images by users, such as images of products, sellers and vendors;
    * When purchasing products buyers can comment them and submit complaints, which are sent to sellers and admin for moderation; 
    * Sellers and admin are able to get sales reports (in browser and download in CSV) to monitor their sales volumes and profit for selected periods of time; 
___

#### Technological stack used for backend
* Backend is written in **Java 11** and **Spring Boot**, and implements microservices architecture, which allows to quickly scale project and reuse common parts/modules to avoid copy-paste. Project consists of three modules: 
    * Security – performs gateway functions and check users access roles;
    * Main – contains implementation of the core business logic;
    * Stats – responsible for getting statistics and sales reports for sellers and admin; 
* **PostgreSQL** is used as database to store information, each module has a separate database; 
* **Spring Data JPA** and **Hibernate** are used for managing SQL quires to the database, as well as library **QueryDSL** to create complex quires using predicates and boolean expressions (extensively used for product search and filtering service in catalogue); 
* **Spring Security** is used to separate users by three main roles (admin/sellers/buyers) and provide authorisation based on JWT tokens issued for limited time to users; 
* **Spring MVC** is used to create REST API endpoints providing access for frontend to out backend application; 
* Database model entities are build using **Lombok** (constructors/getters/setters), **Mapstruct** was used for entity-DTO mapping when transferring objects between database and client;  
* The backend project code is tested by unit and integrational tests (**JUnit 5** and **Mockito**), currently the test code coverage ratio is 70% (we plan to increase it at least to 85%). 
* API documentation is generated automatically using integration with **Swagger** using **SpringFox** library; 
* Full project (both backend and frontend) is deployed on server and available at https://softplat.ru 
___

#### ER diagram
**Security**:

![SCHEME](https://github.com/software-sales-and-installations/softplat-back/blob/dev-microservices/er-diagram-db/softplat-security.jpg)

**Main**:

![SCHEME](https://github.com/software-sales-and-installations/softplat-back/blob/dev-microservices/er-diagram-db/softplat-main.jpg)

**Stats**:

![SCHEME](https://github.com/software-sales-and-installations/softplat-back/blob/dev-microservices/er-diagram-db/softplat-stats.jpg)

----
#### Example of SQL query
```
@Query("SELECT new ru.softplat.stats.server.dto.SellerReportEntry( " +
            "s.product.name, sum (s.quantity), sum(s.amount)) " +
            "FROM Stats s " +
            "WHERE s.dateBuy BETWEEN :start AND :end " +
            "GROUP BY s.product.seller.id, s.product.id, s.product.name ")
    List<SellerReportEntry> getAllStats(
            LocalDateTime start,
            LocalDateTime end);
```
----

#### Examples of HTTP requests to REST API
Below are examples of requests for all user roles in our application.

**Admin:**
- Deleting a product comment by an admin:

  ```DELETE "https://api.softplat.ru/comment/{commentId}"```

**Seller:**
- Creating a new product by seller:

  ```POST "https://api.softplat.ru/product"```

**Buyer:**
- Adding a product to the shopping cart by buyer:

  ```POST "https://api.softplat.ru/basket/{productId}"```

**Public:**
- New user registration:

  ```POST "https://api.softplat.ru/registration"```

- Product search and filtering based on a set of parameters:

  ```GET "https://api.softplat.ru/product/search?minId=0&pageSize=10&sort=NEWEST"```
___

#### Plans for future development
By the end of December 2023 our team has successfully completed the MVP stage and looks forward to continuing development of the product by adding new features to it: 
1. Integrate payments into the platform to perform transactions b/w buyers and sellers; 
2. Provide solution for users to upload and download software from the platform; 
3. Implement storing of software licence keys for distributing them to buyers; 
4. Fully automate CI/CD process of deploying our application on server using Docker; 
5. Increase our test coverage and include more complex test scenarios; 
6. Add indices and search keys to our databases to increase speed of SQL queries execution; 
7. Integrate message brokers (Kafka or RabbitMQ) to increase speed of communication between microservices and modules.
