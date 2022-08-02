import kotlinx.coroutines.*

fun main() {
    println("Program execution will now block")
    runBlocking {
        launch {
            delay(1000L)
            println("Task from runBlocking")
        }

        GlobalScope.launch {
            delay(500L)
            println("Task from GlobalScope")
        }

        coroutineScope {
            launch {
                delay(1500L)
                println("Task from coroutineScope")
            }
        }
    }
    println("Program execution will now continue")
    /*
    Program execution will now block
    Task from GlobalScope
    Task from runBlocking
    Task from coroutineScope
    Program execution will now continue

    Process finished with exit code 0

     */
}