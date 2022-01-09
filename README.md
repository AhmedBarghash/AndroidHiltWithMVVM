# WeatherLogger
Android application to save weather conditions for your current location and the background change based on your time.

## Screenshot
![Screenshot_20220109_224518](https://user-images.githubusercontent.com/18752334/148705453-23c750f5-d6d4-40ee-a6c1-50f0ffbbae28.png)
![Screenshot_20220109_224456](https://user-images.githubusercontent.com/18752334/148705457-561023dd-0376-4377-b06e-b08160bd08ca.png)
![Screenshot_20220109_224431](https://user-images.githubusercontent.com/18752334/148705464-7ef4eeb8-e26e-4f39-8234-ccd94e18becd.png)
![Screenshot_20220109_224410](https://user-images.githubusercontent.com/18752334/148705466-0e359ebc-e64a-49c8-b5a2-ce3058fc4e75.png)

## Project setup & Info
it's a basic Android studio project used to implement the new Architecture components in the jet back Clone the repo, open the project in Android Studio, hit "Run". Done!
The application is compatible with Android 21 (cannot be smaller than version 21 declared in the library) and onwards

## Verison
Android Studio : Android Studio Arctic Fox ,
kotlin : 1.5.31,
Gradle : 7.0.4

## Architecture overview

The app is a client: where operations are performed by calling API endpoints over the network and local data is in effect mutable. Local data is only modified as a result of user requests.
The app client must have location permission otherwise the API calls will not affect  local data

The domain model objects are used throughout the app. They are plain Kotlin/Java objects. 
They should not be directly tied to persistence details, nor should they be directly tied to network API details. 
The persistence/network layer translates to the local domain model as needed, the rest of the app should not have to know about those implementation details.

## Dependencies For 
- Retrofit2
- ROOM In Android  
- MVVM Desing Pattern
- Hilt
- RXJava
- lifecycle livedata
