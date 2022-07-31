# CoroutinesBasics

## Hello world
```kt
fun main() {
    GlobalScope.launch {
        delay(2000)
        println("World")
    }

    print("Hello, ")
    Thread.sleep(3000)

    /**
     * Hello, World
     * Process finished with exit code 0
     */
}
```

## Coroutines are lightweight
```kt
fun main() {
    runBlocking {
        repeat(1_000_000) {
            launch {
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
