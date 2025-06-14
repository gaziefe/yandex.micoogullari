@HiltAndroidApp
class NotesApp : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesTheme {
                val state by viewModel.state.collectAsState()
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    viewModel.effect.collect { effect ->
                        when (effect) {
                            is NotesEffect.NavigateToEdit -> {
                                // Handle navigation
                            }
                            is NotesEffect.ShowError -> {
                                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                NotesListScreen(
                    state = state,
                    onEvent = viewModel::handleEvent
                )
            }
        }
    }
}
