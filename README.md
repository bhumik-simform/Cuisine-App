readme_content = """# 🍲 Food Explorer App

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![Material Design](https://img.shields.io/badge/Material_Design-757575?style=for-the-badge&logo=material-design&logoColor=white)

A modern, Material Design-inspired Android application designed to help users discover dishes from around the world. This project serves as a comprehensive showcase of fundamental Android development concepts including UI design, Fragment navigation, RecyclerViews, and Intent handling.

---

## ✨ Features

* **Discover Foods:** Browse a beautifully designed horizontal category list and vertical scrolling feed of global dishes.
* **Rich Detail View:** Dive deep into specific dishes with high-quality hero images, localized ingredients, cooking times, and spice levels.
* **Favorites Management:** Save preferred dishes locally and view them in a dedicated favorites tab featuring empty-state illustrations.
* **Custom Settings:** Modern settings screen demonstrating app preferences and Material 3 switches.
* **Deep Device Integration:** * *Share Receiver:* Handle implicit intents to receive shared text and recipes from other applications.
    * *Notifications:* Handle `PendingIntent` to view recommended dishes directly from system notifications.

---

## 📱 App Flow & Screens Breakdown

1.  **Splash Screen:** Elegant entry point demonstrating Activity lifecycle basics with a clean UI.
2.  **Home Screen (`MainActivity` + `FoodListFragment`):** Features a modern App Bar, rounded Search Bar, horizontal category Chips, and a fully optimized `RecyclerView` for dish cards.
3.  **Food Detail Screen (`DetailFragment`):** Demonstrates data passing between fragments, fragment transactions, and triggering explicit intents (e.g., opening a Wikipedia page).
4.  **Favorites Screen:** Utilizes Bottom Navigation integration with a clear, user-friendly empty-state UI.
5.  **Settings Screen:** Demonstrates scalable settings layout building using Material Design components.
6.  **Share Receiver:** A specialized Activity built to showcase intent filters and receiving external data.
7.  **Notification Detail:** Built to handle system-level engagement via notifications.

---

## 🛠️ Tech Stack & Architecture

* **UI System:** Standard XML Layouts, Material Design 3 Components (Cards, Chips, Bottom Navigation).
* **Architecture:** Classic Activity & Fragment-based architecture (ideal for learning lifecycles).
* **Lists:** `RecyclerView` utilizing custom Adapters and ViewHolders.
* **Typography:** Custom font integration (Poppins/Inter).
* **Data Source:** Currently utilizing a robust local JSON structure in the `assets` folder (modeled after TheMealDB) enhanced with custom UI-specific fields (`rating`, `cookingTime`, `spiceLevel`).
