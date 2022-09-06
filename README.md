# Introduction
NasaYoyo Test is an Android app that allows users to scroll through the list of Milky Way images Using the NASA Search API

## Screenshots

![Gallery Image] (/app/libs/screenshots/gallery.png)
![Details Image] (/app/libs/screenshots/details.png)

## Architecture Components

    * Recylcerview
    * ViewModels
    * LiveData
    * Paging
    * Navigation
    * ViewBinding

### [Paging Library] (https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
The Paging library includes the following features:

    * In-memory caching for your paged data. This ensures that your app uses system resources efficiently while working with paged data.
    * Built-in request deduplication, ensuring that your app uses network bandwidth and system resources efficiently.
    * Configurable RecyclerView adapters that automatically request data as the user scrolls toward the end of the loaded data.
    * First-class support for Kotlin coroutines and Flow, as well as LiveData and RxJava.
    * Built-in support for error handling, including refresh and retry capabilities.

### [Dagger Hilt] (https://developer.android.com/training/dependency-injection/hilt-android)

Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Doing manual dependency injection requires you to construct every class and its dependencies by hand, and to use containers to reuse and manage dependencies


### To be Improved
    - The app needs to impement caching through room db
    - Paging 3 remote mediator should also be added
    - Add more testing scenarios

