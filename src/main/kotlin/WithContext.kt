import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
        launch(Dispatchers.Default) {
            println("First context: $coroutineContext")
            withContext(Dispatchers.IO) {
                println("Second context: $coroutineContext")
            }
            println("Third context: $coroutineContext")
            /*
            First context: [StandaloneCoroutine{Active}@33da441, Dispatchers.Default]
            Second context: [DispatchedCoroutine{Active}@62636099, Dispatchers.IO]
            Third context: [StandaloneCoroutine{Active}@33da441, Dispatchers.Default]

            Process finished with exit code 0
             */
        }
    }
}