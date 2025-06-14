import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NotesNavGraph(
    navController: NavHostController,
    viewModel: NotesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesList.route
    ) {
        composable(Screen.NotesList.route) {
            NotesListScreen(
                onNoteClick = { noteId ->
                    navController.navigate(
                        Screen.EditNote.createRoute(noteId)
                    )
                },
                onAddNote = {
                    navController.navigate(
                        Screen.EditNote.createRoute(null)
                    )
                },
                viewModel = viewModel
            )
        }
        
        composable(
            route = "${Screen.EditNote.route}/{${Screen.EditNote.ARG_NOTE_ID}}",
            arguments = listOf(navArgument(Screen.EditNote.ARG_NOTE_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString(Screen.EditNote.ARG_NOTE_ID)
            EditNoteScreen(
                noteId = if (noteId == "new") null else noteId,
                onSave = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
