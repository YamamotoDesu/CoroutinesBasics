# CoroutinesBasic
## Hello world

```kt
fun main() {
    GlobalScope.launch {
        delay(2000)
        println("World")  // 新しいコルーチンを作りバックグラウンドで動作させる
    }

    print("Hello, ") // コルーチンが待っている間メインスレッドが動き続ける
    Thread.sleep(3000) // メインスレッドを止めないように sleep する

    /**
     * Hello, World
     * Process finished with exit code 0
     */
}
```

## Coroutines are lightweight
```kt
fun main() {
    runBlocking {  // メインスレッドをブロックするエクスプレッションを作成する
        repeat(1_000_000) {
            launch { // Coroutines実行
                print(".")
            }
        }
    }

    /**
     * Threadだとクラッシュするが
     * Coroutinesだとクラッシュしない
     */
}
```

## Scope
```kt
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
```

## Suspend Function
```kt
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
```

## Jobs
```kt
runBlocking {
        val job1 = launch {
//            delay(3000L)
            println("Job1 launched")
            val job2 = launch {
                println("Job2 launched")
                delay(3000L)
                println("Job2 is finishing")
            }
            job2.invokeOnCompletion { println("Job2 completed") }

            val job3 = launch {
                println("job3 launched")
                delay(3000L)
                println("job3 is finishing")
            }
            job3.invokeOnCompletion { println("job3 completed") }
        }

        job1.invokeOnCompletion { println("Job1 is completed") }

        delay(500L)
        println("Job1 will be cancelled")
        job1.cancel()
        /*
        Job1 launched
        Job2 launched
        job3 launched
        Job1 will be cancelled
        Job2 completed
        job3 completed
        Job1 is completed

        Process finished with exit code 0
         */
    }
```
