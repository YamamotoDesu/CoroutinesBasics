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

## Dispatchers

```kt
    runBlocking {
//        launch(Dispatchers.Main) {
//            println("Main dispatcher. Thread: ${Thread.currentThread().name}")
//        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined1. Thread: ${Thread.currentThread().name}")
            delay(100L)
            println("Unconfined2. Thread: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Default. Thread: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            println("IO. Thread:: ${Thread.currentThread().name}")
        }

        launch(newSingleThreadContext("MyThread")) {
            println("newSingleThreadContext. Thread: ${Thread.currentThread().name}")
        }

        /*
        Unconfined1. Thread: main
        Default. Thread: DefaultDispatcher-worker-1
        IO. Thread:: DefaultDispatcher-worker-2
        Unconfined2. Thread: kotlinx.coroutines.DefaultExecutor
        newSingleThreadContext. Thread: MyThread
         */
    }
```


## Async
```kt
fun main() {
    runBlocking {
        val firstDeferred = async { getFirstValue() }
        val secondDeferred = async { getSecondValue() }

        println("Doing some processing here")
        delay(500L)
        println("Waiting for values")

        val firstValue = firstDeferred.await()
        val secondValue = secondDeferred.await()

        println("The total is ${firstValue + secondValue}")
        /*
        Doing some processing here
        Waiting for values
        Returning first value 47
        Returning second value 881
        The total is 928

        Process finished with exit code 0
         */
    }
}

suspend fun getFirstValue(): Int {
    delay(1000L)
    val value = Random.nextInt(100)
    println("Returning first value $value")
    return value
}

suspend fun getSecondValue(): Int {
    delay(2000L)
    val value = Random.nextInt(1000)
    println("Returning second value $value")
    return value
}
```

## WithContext
```kt
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
```
