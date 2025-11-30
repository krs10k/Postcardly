📬 Postcardly — CS3443 Final Project

Contributors:
Karsten T Karlsgodt • Samuel C Garcia • Sean David Lewis • Charles C Lazcano

Postcardly is a JavaFX desktop application that allows users to create, save, and view digital postcards for recorded trips. The project follows the Model-View-Controller (MVC) pattern and uses file I/O to persist trip data.

This application is the final project submission for UTSA CS3443 — Application Programming.

✨ Features

Clean JavaFX UI using FXML layouts

Users can create a trip postcard and save it locally

View previously saved trips

Navigation between multiple scenes (Splash → Menu → Views → etc.)

Local resources packaged inside the application

Strict MVC separation with a Service layer for file I/O

🧩 Architecture — MVC
Layer	Folder Path	Purpose
Model	src/main/java/projects/postcardly/model	Trip data objects
View	src/main/resources/projects/postcardly/*.fxml	UI screens
Controller	src/main/java/projects/postcardly/controller	User interaction & scene switching
Service	src/main/java/projects/postcardly/service	File I/O and helper logic
📂 Project Structure
Postcardly/
 ├── src/main/java/projects/postcardly/
 │   ├── controller/
 │   ├── model/
 │   ├── service/
 │   ├── Launcher.java          # Main execution entry point
 │   └── PostcardlyApp.java     # JavaFX Application class
 │
 ├── src/main/resources/projects/postcardly/
 │   ├── data/                  # Saved trip data
 │   ├── images/                # UI graphics + postcard visuals
 │   ├── CreateTrip.fxml
 │   ├── MainMenu.fxml
 │   ├── SplashScreen.fxml
 │   ├── TripsMenu.fxml
 │   └── TripView.fxml
 │
 ├── Postcardly_UML.drawio      # UML diagram (MVC)
 ├── pom.xml                    # Maven configuration
 └── README.md

🚀 How to Run Postcardly (IntelliJ)

After cloning or downloading the repository:

1️⃣ Open the project in IntelliJ IDEA
2️⃣ Set the Project SDK to:
👉 Amazon Corretto 24.0.2 (or current project SDK used in this project)
3️⃣ In IntelliJ Project View open:

src/main/java/projects/postcardly/Launcher.java


4️⃣ Right-click → Run 'Launcher'
5️⃣ The app will launch starting at the Splash Screen

🐞 Known Issues

Must be run using the correct SDK (Amazon Corretto 24.0.2)

JavaFX may fail to load if SDK is set to a different Java version

Screen scaling may vary depending on monitor resolution
