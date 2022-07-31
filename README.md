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
