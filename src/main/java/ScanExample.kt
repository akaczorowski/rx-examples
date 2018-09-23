import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class ScanExample {
    companion object {
        @JvmStatic
        fun run() {
            Observable.just("John", "Andrew", "Mike", "Larry", "Ozzy")
                    .map(String::length)
                    .scan(0) { accumulator, next -> accumulator + next }
                    .subscribe { s -> System.out.println(s) }

            println()
            System.out.println("Without initial value.")

            Observable.just("John", "Andrew", "Mike", "Larry", "Ozzy")
                    .map(String::length)
                    .scan{ accumulator, next -> accumulator + next }
                    .subscribe { s -> System.out.println(s) }
        }
    }
}