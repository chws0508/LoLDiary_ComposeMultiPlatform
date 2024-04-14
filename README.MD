<h1 align="center">LoL Diary</h1>

<p align="center">  
🗡️ LoL Diary demonstrates modern Compose Multiplatform development with clean architecture
</p>
</br>

<img src="/preview/android_demo.gif" width="320"/>

<img src="/preview/ios_demo.gif" width="320"/>

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Koin](https://github.com/InsertKoinIO/koin): for Kotlin Multiplatform dependency injection.
- Architecture
  - MVVM Architecture (View - ViewModel - Model): Facilitates separation of concerns and promotes maintainability.
  - Repository Pattern
- [Ktor](https://github.com/ktorio/ktor): Construct the REST APIs and paging network data.
- [Sandwich](https://github.com/skydoves/Sandwich): Construct a lightweight and modern response interface to handle network payload for Android.
- [Kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization): A modern JSON library for Kotlin.
- [Turbine](https://github.com/cashapp/turbine): A small testing library for kotlinx.coroutines Flow.
- [Material-Components](https://github.com/material-components/material-components-android): Material design components for building ripple animation, and CardView.
- [Kamel](https://github.com/Kamel-Media/Kamel): Network Image Loading Library for Kotlin Multiplatform
- [Timber](https://github.com/JakeWharton/timber): A logger with a small, extensible API.
- [SQLDelight](https://github.com/cashapp/sqldelight): Database Library for Kotlin Multiplatform
- [Moko-Resources](https://github.com/icerockdev/moko-resources): Resource Library for Kotlin Multiplatform

## Modularization

![architecture](/preview/dependency_graph.png)

## Open API

LoL Diary using the [Riot API](https://developer.riotgames.com/) for constructing RESTful API.<br>
