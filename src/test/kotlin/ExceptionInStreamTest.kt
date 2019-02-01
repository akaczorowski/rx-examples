import io.reactivex.Observable
import org.junit.Test

class ExceptionInStreamTest {

    @Test
    fun exceptionInStream() {

        val source = Observable.just(1, 2, 3)
                .map {
                    if (it == 2) throw NullPointerException()
                }
                .doOnComplete { println("onComplete()") }
                .doFinally { println("doFinally()") }

        source.subscribe({ println("Observer1 received $it") }, { println(it) })

    }
}