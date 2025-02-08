# 📦 Electronic Store - E-Commerce Backend  

This is a backend service for an electronic store, built using **Spring Boot**, **Java 17**, and **Maven**. It provides APIs for managing products, users, orders, and more.  

## 🚀 Tech Stack  
- **Java 17**  
- **Spring Boot**  
- **Spring Data JPA (Hibernate)**  
- **MySQL/PostgreSQL** (Configurable in application properties)  
- **Maven**  
- **Lombok**  
- **JWT Authentication**  

## 📌 Features  
✅ User Authentication & Authorization  
✅ Product Management  
✅ Order & Cart Functionality  
✅ Secure REST APIs  
✅ Pagination & Sorting  

## 🔧 Setup Instructions  

1️⃣ Clone the repository:  
```sh
git clone https://github.com/sarveshdutt17feb/ElectronicStore.git
cd ElectronicStore
```
2️⃣ Configure the database in `application.properties`:  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/electronic_store
spring.datasource.username=root
spring.datasource.password=yourpassword
```
3️⃣ Build & run the application:  
```sh
mvn clean install  
mvn spring-boot:run  
```
4️⃣ Access APIs via Postman or Swagger (if enabled).  

## 📁 Project Structure  
```
ElectronicStore/
│── src/main/java/com/lcwd/electronic/store/   # Main Java source files  
│── src/main/resources/                         # Application properties & static resources  
│── pom.xml                                     # Maven dependencies  
│── README.md                                   # Project documentation  
```

## 📜 API Endpoints  
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/products` | Fetch all products |
| POST | `/products` | Add a new product |
| GET | `/orders` | Fetch all orders |
| POST | `/users/register` | Register a new user |

## 🤝 Contributing  
Feel free to raise issues or contribute by creating pull requests!  

## 🤝 Author- Sarvesh Richhariya  


