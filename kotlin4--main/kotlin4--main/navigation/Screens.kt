sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list")
    object EditNote : Screen("edit_note") {
        const val ARG_NOTE_ID = "note_id"
        fun createRoute(noteId: String?) = "edit_note/${noteId ?: "new"}"
    }
}
