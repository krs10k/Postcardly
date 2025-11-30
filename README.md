# 📬 Postcardly — CS3443 Final Project
Karsten T Karlsgodt ,Samuel C Garcia, Sean David Lewis, Charles C Lazcano

Postcardly is a JavaFX desktop application that allows users to create, save, and view digital postcards for recorded trips. The project follows the **Model-View-Controller (MVC)** pattern and uses **file I/O** to persist trip data.

This application is the final project submission for **UTSA CS3443 — Application Programming**.

---

## ✨ Features

- Clean JavaFX UI using FXML layouts
- Users can create a trip postcard and save it locally
- View previously saved trips
- Organized navigation between scenes (Splash → Menu → Views → etc.)
- Local data & resources packaged inside the application
- Follows strict MVC architecture with separate Service layer for data I/O

---

## 🧩 Architecture — MVC

| Layer | Folder | Responsibility |
|------|--------|----------------|
| Model | `/src/main/java/projects/postcardly/model` | Represents Trip data objects |
| View | `/src/main/resources/projects/postcardly/*.fxml` | UI screens and layouts |
| Controller | `/src/main/java/projects/postcardly/controller` | Handles user interaction / scene switching |
| Service | `/src/main/java/projects/postcardly/service` | File I/O + logic not tied to UI |

---

## 📂 Project Structure
Postcardly/
├── src/main/java/projects/postcardly/
│ ├── controller/
│ ├── model/
│ ├── service/
│ ├── Launcher.java # Main execution entry point
│ └── PostcardlyApp.java # JavaFX Application class
│
├── src/main/resources/projects/postcardly/
│ ├── data/ # User trip data files (read/write)
│ ├── images/ # UI assets & postcard graphics
│ ├── CreateTrip.fxml
│ ├── MainMenu.fxml
│ ├── SplashScreen.fxml
│ ├── TripsMenu.fxml
│ └── TripView.fxml
│
├── Postcardly_UML.drawio # UML Diagram (MVC)
├── pom.xml # Maven config
└── README.md

## 🛠 Java SDK Requirement (Very Important)

This project must run using:
