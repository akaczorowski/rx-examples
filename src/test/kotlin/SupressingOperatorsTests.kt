import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

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
                .skipWhile { it > 5 } // never skips, condition is false form first call
                .subscribe(::println)

        Thread.sleep(1000L)

        println("First")
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skipWhile { it < 5 }
                .subscribe(::println)

        Thread.sleep(1000L)

    }

    @Test
    fun skipUntil() {
        val observable1 = Observable.create<Int> { it ->
            for (i in 0..10) {
                Thread.sleep(1000)
                it.onNext(i)
            }
            it.onComplete()
        }

        val observable2 = Observable.timer(3, TimeUnit.SECONDS)
                .flatMap { _ -> Observable.just(11, 22, 33, 44, 55) }

        observable1.skipUntil(observable2)
                .subscribe { onNext -> println("OnNext:" + onNext!!) }
    }

    @Test
    fun flatMapParallelWithSubscribeOn() {

        val numOfCores = Runtime.getRuntime().availableProcessors()
        val assigner = AtomicInteger(0)

        Observable.range(500, 3)
                .groupBy { assigner.incrementAndGet() % numOfCores }
                .doOnNext { printMessage("dispatching work.") }
                .flatMap { grp ->
                    grp
                            .doOnNext { printMessage("before start.") }
                            .subscribeOn(Schedulers.computation())
                            .doOnNext { printMessage("started.") }
                            .map(::timeConsumingOperation)
                }
                .subscribe({ printMessage(" done, arg: $it, time:") }, { println(it) })

        Thread.sleep(6000)
        println("All DONE. FINISHING...")
    }

    @Test
    fun flatMapParallelWithObserveOn() {

        val numOfCores = Runtime.getRuntime().availableProcessors()
        val assigner = AtomicInteger(0)

        Observable.range(500, 3)
                .groupBy { assigner.incrementAndGet() % numOfCores }
                .doOnNext { printMessage("dispatching work.") }
                .flatMap { grp ->
                    grp
                            .doOnNext { printMessage("before start.") }
                            .observeOn(Schedulers.computation())
                            .doOnNext { printMessage("started.") }
                            .map(::timeConsumingOperation)
                }
                .subscribe({ printMessage(" done, arg: $it, time:") }, { println(it) })

        Thread.sleep(6000)
        println("All DONE. FINISHING...")
    }

    fun printMessage(text: String) {
        println(Thread.currentThread().name + " " + text + " " + LocalTime.now().toString())
    }

    fun timeConsumingOperation(value: Int): Int {
        val time = 500 + Math.abs(Random().nextLong() % 5000)
        println(Thread.currentThread().name + " computing, arg: $value, last: $time s")
        Thread.sleep(time)
//        Thread.sleep(Math.abs(Random().nextLong() % 5000))

        return value
    }

}