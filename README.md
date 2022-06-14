# MVVM_Exmaple
Android application as a live example for MVVM with koltin and Hilt.

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
