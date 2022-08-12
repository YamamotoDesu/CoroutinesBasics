import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val myHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception handled: ${throwable.localizedMessage}")
        }

        val job = GlobalScope.launch(myHandler) {
            println("Throwing exception from job")
            throw IndexOutOfBoundsException("exception in coroutine")
        }
        job.join()
        /*
        Throwing exception from job
        Exception handled: exception in coroutine

        Process finished with exit code 0
         */

        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException("exception from async")
        }

        try {
            deferred.await()
        } catch (e: ArithmeticException) {
            println("Caught ArithmeticException ${e.localizedMessage}")
        }
        /*
        Throwing exception from job
        Exception handled: exception in coroutine
        Throwing exception from async
        Caught ArithmeticException exception from async

        Process finished with exit code 0
         */
    }
}