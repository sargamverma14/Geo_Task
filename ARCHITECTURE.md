# App Architecture Overview

This Android project is built following **Clean Architecture** principles combined with the **MVI (Model-View-Intent)** architectural pattern, leveraging **Jetpack Compose**, **Kotlin Coroutines / Flows**, **Dagger Hilt** for Dependency Injection, **Retrofit** for networking, and **Room** for local caching.

---

## 1. Architectural Layers (Clean Architecture)

Clean Architecture separates the software into layers with strict dependency rules (dependencies point inwards).

```
[ Presentation Layer (Jetpack Compose + MVI ViewModels) ]
                       ↓
[ Domain Layer (Use Cases + Pure Models + Repository Interfaces) ]
                       ↑
[ Data Layer (Repository Implementations + Room DAO/Entities + Retrofit APIs/DTOs) ]
```

### A. Domain Layer (Innermost Layer)
- Contains business logic and domain models.
- **`model/`**: Pure Kotlin data classes representing domain entities (e.g., [`Post.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/domain/model/Post.kt)).
- **`repository/`**: Abstract interfaces defining data operations (e.g., [`PostRepository.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/domain/repository/PostRepository.kt)).
- **`usecase/`**: Encapsulate single business actions/use cases (e.g., [`GetPostsUseCase.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/domain/usecase/GetPostsUseCase.kt)).

### B. Data Layer
- Implements repository interfaces defined in the domain layer.
- Coordinates between local cache (`Room`) and remote server (`Retrofit`).
- **`local/`**: Room database, DAOs ([`PostDao.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/data/local/PostDao.kt)), and database entities.
- **`remote/`**: Retrofit API services ([`PostApi.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/data/remote/PostApi.kt)) and DTOs.
- **`repository/`**: Concrete repository implementations (e.g., [`PostRepositoryImpl.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/data/repository/PostRepositoryImpl.kt)) with mappers between DTO/Entity and Domain models.

### C. Presentation Layer (UI & MVI)
- Built with **Jetpack Compose** and **MVI (Model-View-Intent)** pattern via `BaseViewModel`.
- **`base/`**: [`BaseViewModel.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/presentation/base/BaseViewModel.kt) handling State, Events, and Side Effects.
- **`home/`**:
  - `HomeContract.kt`: Defines `HomeState`, `HomeEvent`, and `HomeEffect`.
  - `HomeViewModel.kt`: Processes user events, invokes use cases, and updates state flows.
  - `HomeScreen.kt`: Declarative UI observing state and emitting events.
- **`navigation/`**: Jetpack Navigation Compose setup ([`NavGraph.kt`](file:///D:/Android%20projects/NewComposewithMVVM/app/src/main/java/com/mvvm/compose/navigation/NavGraph.kt)).

---

## 2. MVI (Model-View-Intent) Pattern

1. **State (`HomeState`)**: Immutable representation of the UI state at any given moment.
2. **Event (`HomeEvent`)**: User actions or lifecycle events sent from View to ViewModel.
3. **Effect (`HomeEffect`)**: One-time side effects (e.g., showing a Snackbar or navigation triggers).
4. **Unidirectional Data Flow (UDF)**:
   - User interacts with UI $\rightarrow$ sends `Event` to `ViewModel`.
   - `ViewModel` processes event $\rightarrow$ updates `State` (`MutableStateFlow`).
   - UI observes `State` changes $\rightarrow$ renders the screen declaratively.
