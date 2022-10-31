# Movies Info App (MIA)
App to Consume [The Movie DB](https://www.themoviedb.org/documentation/api) and filter by popularity, on TV, on air, and top-rated. Made with Jetpack Compose.

# Application ScreenShots

![flow1](https://user-images.githubusercontent.com/42783065/198932643-a4e36963-73a2-4286-9fee-7650351769ea.jpeg)
![flow2](https://user-images.githubusercontent.com/42783065/198932657-b5b25a42-c48d-415b-b792-304404c87928.jpeg)
- Light Theme
![flow3](https://user-images.githubusercontent.com/42783065/198932669-c58d1c39-e9c0-4815-8232-4562f2d54eab.jpeg)
- Tablet
![flow4](https://user-images.githubusercontent.com/42783065/198932687-fa5a0ba3-8a93-4f7d-a56d-e9c4829da95e.jpeg)

# Challenge Objectives

- Build app to filter and see details of TV shows using [The Movie DB](https://www.themoviedb.org/documentation/api) with pagination. ✓
- Make application based on given Figma design. ✓
- Filter TV shows by popularity, on TV, on air, and top-rated. ✓
- Be able to use the app without internet connection. ✓
- Store the TV show list carried out locally, and offer to repeat them in case of not having an internet connection. ✓
- Collapsible top bar on details screen. ✓
- Save favorite TV shows on local storage (Room). ✓
- Manage Dark and Light Themes. ✓
- Use of at least one animation. ✓
- Add tablet support. ✓

# Non-native libraries used

- [Truth](https://truth.dev/) for better assertions in testing.
- [Retrofit 2](https://square.github.io/retrofit/) to handle API calls easily.
- [Coil](https://coil-kt.github.io/coil/compose/) provides an AsyncImage component to handle images from URLs easily.
- [Google SwipeToRefresh](https://search.maven.org/artifact/com.google.accompanist/accompanist-swiperefresh)

# Test Cases

- Testing Room database - tested all methods to read/write from database.
- Testing Utils method to get full path of images' URLs.
- Testing connectivity status - tested if connectivity status function is working.

# Bugs
- Splash screen doesn't work correctly on android 12, black screen is shown instead.

# Used Devices for testing

- Pixel 7 - Android 13 (Physical)
- One Plus 7T - Android 11 (Physical)
- Xiaomi Poco X3 Pro - Android 12 (Physical)
- Samsung Galaxy S21 FE - Android 12 (Physical)
- Blu 3 - Android 8 (Physical)
- Pixel 4 - Android 11 (Emulator)
- Pixel XL - Android 10 (Emulator)
- Nexus 5 - Android 9 (Emulator)
- Pixel 4a - Android 11 (Emulator)
- Pixel C (Tablet) - Android 11 (Emulator)

# Video App Demo

- [Application Video Demonstration](https://drive.google.com/file/d/1TnpVVZm5oZAsKtQGHQzPOiPqIpRHArQa/view?usp=share_link)


## License

Open Source
