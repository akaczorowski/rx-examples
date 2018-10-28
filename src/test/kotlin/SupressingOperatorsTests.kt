import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import org.junit.Test
import java.util.concurrent.TimeUnit

class SupressingOperatorsTests {

    @Test
    fun filter() {
        Observable.just("Jake", "Jon", "Cassandra", "Slash")
                .filter({ s -> s.length > 4 })
                .subscribe(::println)
    }

    @Test
    fun take() {
        Observable.just("Jake", "Jon", "Cassandra", "Slash")
                .take(3)
                .subscribe(::println)

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(1, TimeUnit.SECONDS)
                .subscribe(::println)

        Thread.sleep(2000L)
    }

    @Test
    fun takeLast() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(1, TimeUnit.SECONDS)
                .takeLast(500, TimeUnit.MILLISECONDS)
                .subscribe(::println)

        Thread.sleep(2000L)
    }

    @Test
    fun takeWhile() {
        println("First")
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .takeWhile { it > 5 } // immediately calls onComplete
                .subscribe(::println)

        Thread.sleep(1000L)

        println("Second")
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .takeWhile { it < 5 }
                .subscribe(::println)

        Thread.sleep(1000L)
    }

    @Test
    fun takeUntil() {
        println("First")
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .takeUntil(Observable.just(1).delay(700, TimeUnit.MILLISECONDS))
                .subscribe(::println)

        Thread.sleep(1000L)

    }

    @Test
    fun skip() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skip(200, TimeUnit.MILLISECONDS)
                .subscribe(::println)

        Thread.sleep(500L)
    }

    @Test
    fun skipWhile() {
        println("First")
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skipWhile{ it > 5 } // never skips, condition is false form first call
                .subscribe(::println)

        Thread.sleep(1000L)

        println("First")
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skipWhile{ it < 5 }
                .subscribe(::println)

        Thread.sleep(1000L)

    }




}