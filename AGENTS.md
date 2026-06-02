# AGENTS.md — iExpense (KMP + Compose Multiplatform)

## Project Overview

This document is the **agent instruction set** for the **iExpense** Kotlin Multiplatform application. iExpense is a personal finance tracker built for Android and iOS with Compose Multiplatform. It follows **Clean Architecture** with **MVI (Model-View-Intent)** in the Presentation layer.

All code generation, review, and modification tasks must comply with both this document and the project's **[`DESIGN.md`](DESIGN.md)** (the iExpense Design System). The design system defines all tokens, colors, typography, spacing, and reusable component specs.

## Stitch Assets Storage

When downloading assets (screenshots, HTML code, images, or any other hosted files) from Stitch design screens, **always save them into `rootDir/stitch/`** (i.e. `[path_to_project]/iExpense/stitch/`). Do not place them in the project root or any other directory.

---

## Build Commands

### Android
```bash
./gradlew :composeApp:assembleDebug
```

### iOS (via Xcode)
1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Build and run

### iOS (command line)
```bash
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
```

### Tests
```bash
./gradlew :composeApp:testDebugUnitTest
```

### Lint
```bash
./gradlew :composeApp:lint
```

---

## Architecture Overview

### Clean Architecture + MVI

```
┌─────────────────────────────────────────────┐
│           Presentation Layer                │
│  ViewModel ──> StateFlow<State>             │
│  dispatch(Intent)                           │
│  SharedFlow<Effect> (one-shot side effects) │
├─────────────────────────────────────────────┤
│           Domain Layer                      │
│  Use Cases  ──>  Repository Interfaces      │
│  Domain Models, EntityCategory              │
├─────────────────────────────────────────────┤
│           Data Layer                        │
│  Repository Implementations                 │
│  Mappers, RemoteMediator, PagingSource      │
├──────────────────┬──────────────────────────┤
│  Database Layer  │    Network Layer         │
│  (Room SSOT)     │    (Ktor HTTP Client)    │
└──────────────────┴──────────────────────────┘
```

### Key Layers

(All paths are relative to `composeApp/src/commonMain/kotlin/com/mobile/iexpense/`)

| Layer | Location | Description |
|-------|----------|-------------|
| **Presentation** | `feature/*/` | MVI ViewModels, State, Intent, Effect |
| **Domain** | `core/domain/` | Pure models, repository interfaces, use cases. Zero external dependencies. |
| **Data** | `core/data/` | Repository implementations, mappers, paging sources. |
| **Database** | `core/database/` | Room entities, DAOs, local data sources. |
| **Network** | `core/network/` | Ktor client, API services, network data sources. |

---

## Source Code Conventions

### File Endings

Every source file (`.kt`, `.kts`, `.xml`, `.md`, `.toml`, etc.) must end with **exactly one trailing newline** (blank line at end of file). This prevents GitHub Pull Request diff warnings like "No newline at end of file" and ensures compatibility with standard Unix text processing tools.

**Before committing**, verify that the last byte of every changed file is `0x0a` (line feed).

---

## MVI Naming Conventions

Follow these naming conventions to maintain consistency:

| Component | Convention | Example |
|-----------|-----------|---------|
| **ViewModel** | `{Feature}ViewModel.kt` | `HomeViewModel.kt`, `AddExpenseViewModel.kt`, `AuthViewModel.kt` |
| **State** | `{Feature}State.kt` (data class) | `HomeState.kt`, `LoginState.kt` |
| **Intent** | `{Feature}Intent.kt` (sealed interface) | `AddExpenseIntent.kt` |
| **Effect** | `{Feature}Effect.kt` (sealed interface extending `UiEffect`) | `AuthEffect.kt` |
| **Effect Handler** | `{Feature}EffectHandler.kt` (extends `BaseEffectHandler`) | `AuthEffectHandler.kt` |
| **UI Models** | `{Entity}Ui.kt` (data class) | `ExpenseUi.kt`, `CategoryUi.kt` |
| **Use Cases** | `{Action}{Entity}UseCase.kt` | `GetExpensesUseCase.kt`, `AddTransactionUseCase.kt` |
| **Domain Models** | `{Entity}Model.kt` | `ExpenseModel.kt`, `CategoryModel.kt` |
| **Repositories** | `{Entity}Repository.kt` (Interface) and `{Entity}RepositoryImpl.kt` | `ExpenseRepository.kt` |
| **API Services** | `{Entity}ApiService.kt` (Interface) and `{Entity}ApiServiceImpl.kt` | `AuthApiService.kt` |

---

## State Management (MVI + UDF)

The application uses **MVI** with a **Unidirectional Data Flow (UDF)** pattern:

1. **State**: A single `data class` representing the entire screen state. Exposed as `StateFlow<State>`.
2. **Intent**: A `sealed interface` for all user actions. The UI calls `viewModel.dispatch(intent)`. **Never use `onIntent` in ViewModel.**
3. **Effect**: A `sealed interface` extending `UiEffect` for one-shot side effects (navigation, toasts). Emitted via a `Channel` and collected via `LaunchedEffect`.
4. **ViewModel**: Extends `BaseViewModel`, holds `MutableStateFlow<State>` and a `Channel<Effect>`, exposes `StateFlow<State>` and `Flow<Effect>`, and implements `dispatch(intent)`. ViewModels should be `internal class`.
5. **EffectHandler**: Extends `BaseEffectHandler<Effect>` and bridges effects to Compose-only APIs (navigation, toasts). Kept outside the ViewModel for KMP portability.
6. **Screen Structure**: Every screen uses a **Route → Screen → Content** three-layer composable structure.

### UiEffect Marker Interface

```kotlin
// core/common/effect/UiEffect.kt
interface UiEffect
```

All Effect sealed classes implement this marker:

```kotlin
sealed interface SignInEmailEffect : UiEffect {
    data class ShowError(val message: String?) : SignInEmailEffect
    data object NavigateToHome : SignInEmailEffect
}
```

### BaseViewModel + Channel-Based Effects

```kotlin
abstract class BaseViewModel : ViewModel() {

    abstract fun onFailure(error: Throwable)

    protected open fun handleFailure(failure: AppResult.Failure<*>) {
        // log to Firebase / Crashlytics
        onFailure(failure.error)
    }
}

class {Feature}ViewModel(
    private val getItemsUseCase: GetItemsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow({Feature}State())
    val state: StateFlow<{Feature}State> = _state.asStateFlow()

    private val _effect = EffectChannel<{Feature}Effect>()
    val effect: Flow<{Feature}Effect> = _effect.receiveAsFlow()

    init { dispatch({Feature}Intent.OnInit) }

    fun dispatch(intent: {Feature}Intent) = when (intent) {
        {Feature}Intent.OnInit -> onInit()
        {Feature}Intent.Retry -> onRetry()
        {Feature}Intent.DismissError -> _state.reduce { it.copy(error = null) }
    }

    override fun onFailure(error: Throwable) {
        _state.reduce { it.copy(isLoading = false) }
        _effect.sendEffect({Feature}Effect.ShowError(error.message))
    }
}
```

> Use `_state.reduce { it.copy(...) }` instead of `_state.update { }`. This is the project's convention.

### BaseEffectHandler

```kotlin
abstract class BaseEffectHandler<T : UiEffect> {
    abstract val backStack: NavBackStack<NavKey>
    abstract fun handleEffect(effect: T)
    protected fun performNavigateBack() {
        backStack.removeLastOrNull()
    }
}
```

### Route → Screen → Content Three-Layer Structure

Every screen has a `*Route` function that receives `NavBackStack<NavKey>`, wires up the ViewModel and EffectHandler, and delegates to a stateless `*Screen`.

The Route must follow this exact injection pattern:

```kotlin
import org.koin.compose.viewmodel.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun {Feature}Route(backStack: NavBackStack<NavKey>) {
    val unknownErrorMessage = stringResource(Res.string.unknown_error)
    val viewModel: {Feature}ViewModel = koinViewModel()
    val effectHandler: {Feature}EffectHandler = koinInject {
        parametersOf(backStack, unknownErrorMessage)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect(effectHandler::handleEffect)
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch({Feature}Intent.OnInit)
    }

    {Feature}Screen(
        state = state,
        onIntent = viewModel::dispatch
    )
}
```

Rules for the Route:
- **Only Koin references go in Route**: `koinViewModel()`, `koinInject { parametersOf(...) }`, `stringResource()`
- **Never create ViewModels or EffectHandlers manually** — always inject via Koin
- **EffectHandler receives two parameters**: `NavBackStack<NavKey>` and `String` (unknown-error message), in that order
- **State collection**: Always use `collectAsStateWithLifecycle()`, never plain `collectAsState()`
- **Effect wiring**: Use `LaunchedEffect(viewModel.effect)` forwarding to `effectHandler::handleEffect`
- **Init dispatch**: Use `LaunchedEffect(Unit)` to call `viewModel.dispatch({Feature}Intent.OnInit)`
- **Delegation to Screen**: Pass only `state` and `onIntent = viewModel::dispatch`

The Screen must be completely stateless:

```kotlin
@Composable
internal fun {Feature}Screen(
    state: {Feature}State,
    onIntent: ({Feature}Intent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(...) { innerPadding ->
            // Content
        }
        AppLoadingOverlay(visible = state.isLoading)
    }
}
```

### Navigation3 - NavKey-Based Routing

Routes are defined as `@Serializable` sealed classes implementing `NavKey`:

```kotlin
@Serializable
sealed interface Navigation : NavKey {
    @Serializable data object Home : Navigation
    @Serializable data class Otp(val identity: String) : Navigation
}
```

#### Serialization Setup

Every key must be registered in a `SavedStateConfiguration`:

```kotlin
val navigationConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class, Navigation::class, Navigation.serializer())
        polymorphic(NavKey::class, Navigation.Home::class, Navigation.Home.serializer())
        polymorphic(NavKey::class, Navigation.Otp::class, Navigation.Otp.serializer())
    }
}
```

#### Registration in `App.kt`

```kotlin
val navBackStack = rememberNavBackStack(
    configuration = navigationConfig,
    startKey = Navigation.Home
)

NavDisplay(
    backStack = navBackStack,
    entryDecorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    ),
    entryProvider = entryProvider {
        entry<Navigation.Home> { HomeRoute(backStack) }
        entry<Navigation.Otp> { OtpRoute(backStack, it.identity) }
    }
)
```

> For details on operations like `replace()` or `replaceAll()`, see [`navigation.md`](./architecture/ui/navigation.md).

### State Collection Rules

Always use `collectAsStateWithLifecycle()` — never plain `collectAsState()`:

```kotlin
val state by viewModel.state.collectAsStateWithLifecycle()
```

### Effect Rules

- **Effects are one-shot**: They go through a `Channel`, never in State.
- **Never mutate state directly**: Use `_state.reduce { it.copy(...) }`.
- **Use `dispatch(Intent)` only**: Do not use `onIntent`.

---

## AppResult<T> (Unified Result Wrapper)

Used across the app for loading/success/failure states. Defined in `core/common/result/AppResult.kt`.

```kotlin
sealed interface AppResult<T> {
    data class Loading<T>(val data: T? = null) : AppResult<T>
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure<T>(val error: Throwable) : AppResult<T>
}

fun <T> AppResult<T>.value(): T? = when (this) {
    is AppResult.Loading -> data
    is AppResult.Success -> data
    is AppResult.Failure -> null
}
```

> Named `AppResult` to avoid conflict with Kotlin's built-in `Result` class.

---

## EntityCategory (Categorization Enum)

A single canonical enum owned by the domain layer. Used across all layers:

```kotlin
sealed interface EntityCategory {
    val key: String

    enum class ExpenseCategory(val key: String) : EntityCategory {
        FOOD("food"), TRANSPORT("transport"), UTILITIES("utilities"),
        ENTERTAINMENT("entertainment"), HEALTH("health"), SHOPPING("shopping")
    }

    enum class IncomeCategory(val key: String) : EntityCategory {
        SALARY("salary"), INVESTMENT("investment"), FREELANCE("freelance")
    }
}
```

- **Database**: Persisted via Room `TypeConverter` → stores the `key` string
- **Network**: Routed via `when(category)` in the network data source → no duplicate enums needed

---

## Dependency Injection (Koin)

Modules are defined in `composeApp/src/commonMain/kotlin/com/mobile/iexpense/di/KoinModules.kt`.

### Registration Quick Reference

| Component | Registration | Module |
|-----------|-------------|--------|
| ViewModel | `viewModelOf(::MyViewModel)` | `presentationModule` |
| Use Case | `singleOf(::MyUseCase)` | `useCaseModule` |
| Repository Impl | `singleOf(::MyRepoImpl) bind MyRepo::class` | `repositoryModule` |
| Database DataSource | `singleOf(::MyDbDataSource)` | `databaseModule` |
| DAO | `single { get<AppDatabase>().myDao() }` | `databaseModule` |
| Network DataSource | `singleOf(::MyNetworkDataSource)` | `networkModule` |
| API Service | `singleOf(::MyApiImpl) bind MyApiService::class` | `networkModule` |
| EffectHandler (with params) | `factoryOf(::MyEffectHandler)` | `presentationModule` |

> **EffectHandler** is registered as `factoryOf` because it receives constructor parameters (`NavBackStack<NavKey>`, `unknownErrorMessage`) via `parametersOf` at injection time. Never register it as `single` or `viewModelOf`.

### Module Order

```kotlin
val sharedModule = module {
    includes(platformModule, networkModule, databaseModule, repositoryModule, useCaseModule, presentationModule)
}
```

---

## Room KMP Setup

The project uses Room for Kotlin Multiplatform. Strictly follow these rules to avoid build failures:

- **The `room-ktx` Trap**: **Never** add `room-ktx` to the project. It is not KMP-compatible. Use `room-runtime` (KMP) which has built-in Coroutines/Flow support.
- **iOS Driver**: Use `sqlite-bundled` for the iOS driver; Android uses the platform-native driver.
- **KSP Registration**: Register the Room compiler KSP dependency for **every** compilation target (Android and all iOS architectures).
- **Schema Export**: Configure `room { schemaDirectory(...) }` in the Gradle file.

For full technical setup details, see [`room-kmp-setup.md`](./architecture/room-kmp-setup.md).

---

## Layer Interaction & Rules

To maintain Clean Architecture, follow these rules:

- **Presentation → Domain**: ViewModels interact only with **Use Cases** (not Repositories).
- **Domain → Data**: Domain defines **Repository Interfaces**. Data implements them.
- **Data → Domain**: All data from Network/Database MUST be mapped to **Domain Models** before reaching Domain/Presentation.
- **Dependency Rule**: Dependencies point inwards. `Domain` has no dependencies on other layers.
- **State immutability**: Always use `_state.reduce { it.copy(...) }` — never mutate properties directly.
- **Effects**: One-shot events go through `Channel<Effect>` (`_effect.receiveAsFlow()`), never in State.
- **Optimistic updates**: For local write operations, update State immediately, then fire the use case.

---

## Compose UI System

> **Primary Design Reference**: All colors, typography, spacing, shape tokens, and component specifications are defined in **[`DESIGN.md`](DESIGN.md)**. When implementing or reviewing UI code, always consult `DESIGN.md` first.
>
> **Color Sources**: Light/dark hex values live in [`design/DESIGN-light.md`](design/DESIGN-light.md) and [`design/DESIGN-dark.md`](design/DESIGN-dark.md).
>
> **Theming Implementation**: If your project supports dynamic accent switching, see [`architecture/ui/theming-dynamic.md`](./architecture/ui/theming-dynamic.md). For a single static theme, see [`architecture/ui/theming-static.md`](./architecture/ui/theming-static.md).
>
> **Token System**: Semantic tokens (`backgroundPrimary`, `textPrimary`, `heading3xl`, `spacingLg`, `radiusMd`, etc.) are consumed via `DesignSystem.colors` and `DesignSystem.typography`. Never hardcode hex or `dp` values in screen code.
>
> **String Resources**: Every user-facing text — including labels, placeholders, `contentDescription`, error messages, and toast text — must use `stringResource(Res.string.*)`. Never hardcode English strings in composables, ViewModels, or EffectHandlers. All strings must be defined in `composeResources/values/strings.xml` (and mirrored in locale-specific variants).

### Compose Preview Guidelines

When generating or modifying `@Preview` annotations for Composable functions, **always** include `showBackground = true`. This ensures the preview renders with a visible background and avoids transparent or invisible previews in the design tools.

```kotlin
// Correct
@Preview(showBackground = true)
@Composable
internal fun SummaryCardPreview() { ... }

// Incorrect
@Preview
@Composable
internal fun SummaryCardPreview() { ... }
```

### Design System File Architecture

When generating or syncing design tokens to the codebase, produce files in this structure (per [`DESIGN.md`](DESIGN.md) §13):

```text
composeApp/src/commonMain/kotlin/com/mobile/iexpense/core/component/theme/
├── ColorPalettes.kt          ← Raw hex constants (~150 colors)
├── CustomThemeColors.kt      ← Semantic data class + createTheme()
├── AppTypography.kt          ← Typography data class with Poppins
├── Dimens.kt                 ← Spacing, radius, icon, button tokens
└── Theme.kt                  ← AppTheme composable + LocalCustomColors / LocalAppTypography

composeApp/src/commonMain/kotlin/com/mobile/iexpense/core/component/
├── button/
│   ├── AppButton.kt
│   └── AppButtonConfig.kt
├── textfield/
│   ├── AppTextField.kt
│   ├── AppTextFieldConfig.kt
│   ├── AppPasswordTextField.kt
│   └── AppPasswordTextFieldConfig.kt
├── header/
│   ├── AppHeaderPage.kt      ← Left-aligned with back button
│   ├── AppCenterHeaderPage.kt ← Center-aligned, no back button
│   └── ...
├── overlay/
│   └── AppLoadingOverlay.kt
├── toast/
│   ├── AppToast.kt
│   ├── AppToastCard.kt
│   └── AppToastHost.kt
└── misc/
    ├── AppOtpInputField.kt
    └── ForbiddenContent.kt
```

### Focus Management

Use `Modifier.clearFocusOnTap()` on root form containers to dismiss the keyboard when tapping outside input fields:

```kotlin
fun Modifier.clearFocusOnTap(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    pointerInput(focusManager) {
        detectTapGestures(onTap = {
            focusManager.clearFocus(force = true)
        })
    }
}
```

### Toast Queue (AppToast)

> Optional — only if your project uses a channel-based toast system.

Toasts use a `Channel`-backed queue to avoid overwriting each other:

```kotlin
private val channel = Channel<AppToastData>(
    capacity = 16,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)
```

Display the toast host at the root:

```kotlin
@Composable
fun AppToastHost(modifier: Modifier) {
    val current by AppToast.current.collectAsStateWithLifecycle(null)
    AnimatedVisibility(
        visible = current != null,
        enter = fadeIn() + slideInVertically { it / 3 },
        exit = fadeOut() + slideOutVertically { it / 3 }
    ) {
        current?.let { AppToastCard(data = it) }
    }
}
```

Place `AppToastHost` at the root inside `Box` — do not embed inside individual screens.

### Input Validation Debouncing

> Optional — only if your project uses `launchDebounced` for form validation.

Use `launchDebounced` for form validation to avoid firing on every keystroke:

```kotlin
private var emailValidationJob: Job? = null

private fun handleEmailChanged(email: String) {
    _state.reduce { it.copy(email = email, emailErrorCode = null) }
    emailValidationJob = viewModelScope.launchDebounced(emailValidationJob) {
        val result = inputValidator.isEmailValid(_state.value.email)
        if (result.first.not()) {
            _state.reduce { it.copy(emailErrorCode = result.second) }
        }
    }
}
```

---

## Data Flow & Strategy

The application follows an **Offline-First** strategy:

1. **Reactive Streams**: Everything is exposed as Kotlin `Flow`.
2. **Single Source of Truth (SSOT)**: The local Database (Room) is the SSOT.
3. **Pattern: Fetch & Cache**: Standard requests use `networkBoundResource`:
   - Emits cached data from DB
   - Fetches from Network if needed
   - Saves result to DB
   - Automatically emits updated data
4. **Pattern: Paginated Fetch & Cache**: Infinite lists use `Pager` + `RemoteMediator`.
5. **Pattern: Search**: Usually network-only via `PagingSource` unless caching is required.

### Which Pattern to Use?

| Scenario | Pattern | Paging | Caching |
|----------|---------|:------:|:-------:|
| List screen (offline-first) | `networkBoundResource` | No | Yes |
| Infinite scroll list | `Pager` + `RemoteMediator` | Yes | Yes |
| Search | `Pager` + `PagingSource` | Yes | No |
| Single item details | `networkBoundResource` | No | Yes |
| Local CRUD | Direct DB calls | No | N/A |

---

## New Feature Scaffolding

Full step-by-step: see [new-feature-cheatsheet.md](./architecture/new-feature-cheatsheet.md).

Quick checklist:
```
[ ] Domain Model           core/domain/model/{Entity}Model.kt
[ ] Repository Interface   core/domain/repository/{Entity}Repository.kt
[ ] Use Case(s)            core/domain/usecase/{Action}{Entity}UseCase.kt
[ ] DB Entity              core/database/model/{Entity}Entity.kt
[ ] DB DAO                 core/database/dao/{Entity}Dao.kt
[ ] DB Data Source         core/database/source/{Entity}DatabaseDataSource.kt
[ ] Network Model (DTO)    core/network/model/Network{Entity}.kt
[ ] Network Response DTO   core/network/model/response/{Entity}ResponseDto.kt
[ ] Network API Service    core/network/api/{Entity}ApiService.kt
[ ] Network Data Source    core/network/source/{Entity}NetworkDataSource.kt
[ ] Data Mappers           core/data/mapper/{Entity}Mapper.kt
[ ] Paging Components      core/data/paging/{Entity}RemoteMediator.kt (if needed)
[ ] Repository Impl        core/data/repository/{Entity}RepositoryImpl.kt
[ ] State                  feature/{feature}/{Feature}State.kt
[ ] Intent                 feature/{feature}/{Feature}Intent.kt
[ ] Effect                 feature/{feature}/{Feature}Effect.kt (if needed)
[ ] ViewModel              feature/{feature}/{Feature}ViewModel.kt
[ ] Koin Registration      di/KoinModules.kt
```

---

## MVI vs MVVM Quick Reference

| MVVM | MVI | Change |
|------|-----|--------|
| `UiState` data class | `State` data class | Rename |
| `Event` sealed interface | `Intent` sealed interface | Rename |
| `onEvent(event)` | `dispatch(intent)` | Rename |
| `_uiState.update { }` | `_state.reduce { it.copy(...) }` | **New convention** |
| `userMessage` in UiState | `Channel<Effect>` + `Flow<Effect>` | **New pattern** |
| Inline state updates | Optional `reduce()` function | **New (optional)** |

**Non-UI layers (Domain, Data, Database, Network, DI) require zero changes** — they are agnostic to the presentation pattern.

---

## Detailed Pattern Documentation

| Document | Content |
|----------|---------|
| [domain-layer.md](./architecture/domain-layer.md) | Domain models, repository interfaces, use cases, AppResult |
| [data-layer.md](./architecture/data-layer.md) | 5 repository patterns, networkBoundResource, mappers, RemoteMediator, PagingSource |
| [database-layer.md](./architecture/database-layer.md) | Room entities, DAOs, data sources, TypeConverter, transaction provider |
| [network-layer.md](./architecture/network-layer.md) | Ktor setup, safeApiCall, API services, network data sources |
| [presentation-layer.md](./architecture/presentation-layer.md) | MVI ViewModels, State, Intent, Effect, reducer pattern |
| [dependency-injection.md](./architecture/dependency-injection.md) | Koin module structure, registration patterns |
| [room-kmp-setup.md](./architecture/room-kmp-setup.md) | **Room KMP setup** — Gradle configuration, KSP registration, and the `room-ktx` trap |
| [new-feature-cheatsheet.md](./architecture/new-feature-cheatsheet.md) | Step-by-step scaffold for new features |

---

## UI Pattern Documentation

These documents cover Presentation layer implementation details, Compose patterns, screen architecture, and design system rules. When working on UI code, consult these in addition to the core architecture docs above.

| Document | Content |
|----------|---------|
| **[`DESIGN.md`](DESIGN.md)** | **Primary design spec** — colors, typography, spacing, shape tokens, component specs, and do's/don'ts |
| [navigation.md](./architecture/ui/navigation.md) | **Navigation3 architecture** — typed keys, serialization, backstack operations, MVI flow |
| [screen-state-collection.md](./architecture/ui/screen-state-collection.md) | `collectAsStateWithLifecycle` usage, state hoisting, lifecycle awareness |
| [event-dispatching.md](./architecture/ui/event-dispatching.md) | Intent dispatching, Effect handling, MVI unidirectional flow in Compose |
| [loading-error-content-states.md](./architecture/ui/loading-error-content-states.md) | Loading shimmer, error placeholders, empty states, content flip |
| [paging-integration.md](./architecture/ui/paging-integration.md) | Paging integration with Compose LazyLists, load states |
| [screen-architecture.md](./architecture/ui/screen-architecture.md) | Route → Screen → Content three-layer composable structure |
| [reusable-ui-patterns.md](./architecture/ui/reusable-ui-patterns.md) | Design system, reusable components, resource management |
| [edge-cases.md](./architecture/ui/edge-cases.md) | Edge-case handling, advanced patterns, BaseViewModel hardening |
| [testing-patterns.md](./architecture/ui/testing-patterns.md) | UI layer testing patterns, test utilities, current coverage gaps |
| [ui-cheatsheet.md](./architecture/ui/ui-cheatsheet.md) | Quick-reference for scaffolding screens, components, and themes |
| [theming-dynamic.md](./architecture/ui/theming-dynamic.md) | Dynamic accent theming with `ThemeManager` (runtime switching) |
| [theming-static.md](./architecture/ui/theming-static.md) | Single static theme (light/dark only, no runtime switching) |
