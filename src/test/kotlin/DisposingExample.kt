import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Test
import java.util.concurrent.TimeUnit

class DisposingExample {

    @Test
    fun disposeTest1(): Unit {

        val source = Observable
                .interval(200, TimeUnit.MILLISECONDS)
                .doOnNext { println("Emiting: $it") }
                .doOnTerminate { println("doOnTerminate") }
                .doFinally { println("doFinally()") }

        val observer1 = source.subscribe { println("Observer1 received: $it") }
        val observer2 = source.subscribe { println("Observer2 received: $it") }

        Thread.sleep(1000L)

        observer1.dispose()
        Thread.sleep(1000L)

        observer2.dispose()
        Thread.sleep(1000L)

    }

    @Test
    fun disposeTest2(): Unit {
        val disposables = CompositeDisposable()

        val source = Observable
                .interval(200, TimeUnit.MILLISECONDS)
                .doOnNext { println("Emiting: $it") }
                .doOnTerminate { println("doOnTerminate") }
                .doFinally { println("doFinally()") }
                .doOnSubscribe { disposables.add(it) }

        val observer1 = source.subscribe { println("Observer1 received: $it") }
        val observer2 = source.subscribe { println("Observer2 received: $it") }

        Thread.sleep(1000L)
        disposables.clear()

        observer1.dispose()
        Thread.sleep(1000L)

        observer2.dispose()
        Thread.sleep(1000L)

    }

    @Test
    fun disposeTest3(): Unit {
        val disposables = CompositeDisposable()

        val source = startInterval(disposables)
                .onErrorResumeNext(startInterval(disposables))

        val observer1 = source.subscribe { println("Observer1 received: $it") }
        val observer2 = source.subscribe { println("Observer2 received: $it") }

        Thread.sleep(1000L)
        disposables.clear()

        observer1.dispose()
        Thread.sleep(1000L)

        observer2.dispose()
        Thread.sleep(1000L)

    }

    private fun startInterval(disposables: CompositeDisposable): Observable<Long> {
        return Observable
                .interval(200, TimeUnit.MILLISECONDS)
                .flatMap {
                    if (it > 2) {
                        Observable.error(Throwable())
                    } else {
                        Observable.just(it)
                    }
                }
                .doOnNext { println("Emiting: $it") }
                .doOnTerminate { println("doOnTerminate") }
                .doFinally { println("doFinally()") }
                .doOnSubscribe { disposables.add(it) }
                .doOnError { println("doOnError()") }
    }
}