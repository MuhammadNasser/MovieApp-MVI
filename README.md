
# 🎬 **Movie App - MVI** 🎬

## 📱 **Description**
Movie App is an Android application that displays movies fetched from the TMDb API. 🎥 The app allows users to view a list of movies, check full details of each one, mark movies as favorites, and supports offline functionality for caching data. 🌐💾

## ⚙️ **Installation**

To run the app, follow these steps:

1. Clone the repository:
   ```
   git clone <repository_url>
   ```

2. Open the project in Android Studio. 🖥️

3. Add your TMDb API Key to the `gradle.properties` file:
   ```properties
   TMDB_API_KEY=your_api_key_here
   ```

4. Build and run the app on your Android device or emulator. 📲

## 🚀 **Usage**

Once the app is installed, you can start using it to browse and interact with movies:

- **Home Screen** 🏠: Displays a list of movies, with basic information such as poster, title, and release date. 🎬 Users can toggle between vertical list and grid layouts using a UI control. 🔄 Data fetching is paginated.

- **Favorite Functionality** 💖: Users can mark or unmark a movie as a favorite, and the list on the home screen will reflect the favorite state. Favorites are stored locally using Room. 🗂️

- **Details Screen** 🧐: Tapping on a movie item navigates to a details screen where the user can view detailed information like overview, genres, and runtime. ⏱️ The back button returns to the home screen, preserving the scroll position. 🔙

## 🛠️ **Features**

- **Home Screen**:
  - Displays a list of movies with poster, title, and release date. 🎥
  - Toggle between vertical list view and grid layout using a UI control. 🔄
  - Pagination for fetching movie data. 🔄

- **Favorite Functionality**:
  - Mark/unmark a movie as a favorite. 💖
  - Store favorite movies locally with Room. 🗂️
  - Reflect the favorite state in the home list. 🔄

- **Details Screen**:
  - View movie details like overview, genres, and runtime. 🕵️‍♂️
  - Back button returns to the home screen while preserving scroll position. 🔙

## 🧑‍💻 **Technologies Used**

- **Architecture**: MVI (Model-View-Intent) 🧑‍💻
- **Layout**: XML-based layouts 🖼️
- **Async Operations**: Coroutines + Flow 🔄
- **Pagination**: Paging 3 Library 🔄
- **Local Storage**: Room for offline caching of data 💾
- **Dependency Injection**: Koin for DI 🧩
- **Navigation**: Single activity architecture using Navigation Component 🚶‍♀️

## 🧪 **Testing**

Unit tests are written for business logic. You can run the tests by executing the following command:
```bash
./gradlew test
```

## 📸 **Screenshots**
*(Add your screenshots here)*
