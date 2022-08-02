import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var functionCalls = 0

fun main() {
    GlobalScope.launch { completeMessage() }
    GlobalScope.launch { improvedMessage() }
    print("Hello, ")
    Thread.sleep(2000L)
    println("There have been $functionCalls calls so far")
    /*
    Hello, World!
    Suspend function are cool
    There have been 2 calls so far

    Process finished with exit code 0
     */
}

suspend fun completeMessage() {
    delay(500L)
    println("World!")
    functionCalls++
}

suspend fun improvedMessage() {
    delay(1000L)
    println("Suspend function are cool")
    functionCalls++
}