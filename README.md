<p align="center">
  <a href="README.md"><img src="https://img.shields.io/badge/🇬🇧-English-blue?style=for-the-badge" alt="English"></a>
  <a href="README.fa.md"><img src="https://img.shields.io/badge/🇮🇷-فارسی-green?style=for-the-badge" alt="Persian"></a>
</p>

# 🐔 Farm Frenzy 2 – Simulation Game

> A Java-based farm management and strategy game, developed as the final project for the **Advanced Programming** course at the University of Isfahan.

---

## 📖 Project Overview

**Farm Frenzy 2** is a 2D time-management and strategy game where the player manages a farm, raises domestic animals, processes raw materials using machines, and sells products to earn coins. The game is played on a **5×6 grid** and follows an **MVC architecture** with multithreading, MySQL database, and JavaFX UI.

---

## ✨ Key Features

### 🐄 Animal System (OOP + Inheritance)
- **Domestic Animals:** Chicken, Cow, Ostrich (produce eggs, milk, feathers)
- **Helper Animals:** Dog (guards against wild animals), Cat (collects products)
- **Wild Animals:** Panda (attacks farm animals, can be captured and sold)

### 🏭 Production Chain (Machines)
| Machine | Input | Output |
|---------|-------|--------|
| Egg Powder Machine | Egg | Egg Powder |
| Bakery | Egg Powder | Bread |
| Butter Machine | Milk | Butter |
| Yarn Machine | Feather | Yarn |
| Weaving Machine | Yarn + Color | Cloth |
| Sewing Machine | Cloth + Ribbon + Button | Clothes |

### 📦 Resource Management
- **Warehouse** with limited capacity (upgradable)
- **Water Well** for planting grass (with cooldown)
- **Truck** for selling products to the city (simulated travel time)
- **Airplane** for ordering raw materials from the city (simulated flight time)

### 🎯 Game Progression
- Each level has specific goals (e.g., earn X coins, produce Y products)
- Timer-based challenges
- Star rating system based on performance
- Progress saved in **MySQL database**

### 🧵 Multithreading & Concurrency
- `ExecutorService` for thread management
- `Platform.runLater()` for UI updates from background threads
- `synchronized` blocks for shared resources (warehouse, coins, animal states)
- All threads support **pause/resume** (game pause feature)

---

## 🛠 Technologies Used

| Component | Technology |
|-----------|------------|
| Language | Java (JDK 17) |
| UI Framework | JavaFX |
| Database | MySQL + JDBC |
| Concurrency | ExecutorService, Thread |
| Architecture | MVC |
| Build Tool | Maven |
| Version Control | Git + GitHub |

---

## 🚀 How to Run

### Prerequisites
- JDK 17 or higher
- MySQL Server
- IntelliJ IDEA (or any Java IDE)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Asadix85/Farm-Frenzy-Java.git
Import as a Maven project in IntelliJ.

Run the SQL scripts in /database to create the required tables.

Update application.properties with your MySQL credentials.

Run Main.java to launch the game.

## 🧱 Project Structure (MVC)
```text
src/main/java/com/example/farm/
├── model/               // Entities (Animal, Product, Machine, ...)
│   ├── animal/
│   ├── product/
│   ├── machine/
│   └── building/
├── controller/          // Game logic, thread management
├── view/                // JavaFX UI pages
├── repository/          // Database access layer (JDBC)
├── exception/           // Custom exceptions
├── util/                // Helpers (validation, threading)
└── Main.java            // Entry point
```

## 👥 Development Team

Amirhossein Karimi Zarchi

Hedyeh Shiasi

Farnoush Izadyar

Hamid Sadegh Jeyhani

Mahtab Dehbashi

(Project designers – Advanced Programming course, University of Isfahan)

📜 License
This project is developed for educational purposes only as part of the Advanced Programming course at the University of Isfahan.

🙌 Acknowledgments
Special thanks to Dr. Ramezani and the TA team for their guidance throughout the course.

⭐ If you find this project useful, don't forget to give it a star on GitHub!
---
