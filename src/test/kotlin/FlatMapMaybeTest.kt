import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Test

class FlatMapMaybeTest {

    @Test
    fun flatMapMaybeEmpty(): Unit {

        Observable.just(1, 2, 3)
                .flatMapMaybe {
                    if (it == 2) {
                        Maybe.empty()
                    } else {
                        Maybe.just(it)
                    }
                }
                .subscribe({ println(it) }, { println(it) })

    }

    @Test
    fun flatMapMaybeError(): Unit {

        Observable.just(1, 2, 3)
                .flatMapMaybe {
                    if (it == 2) {
                        Maybe.error(Throwable())
                    } else {
                        Maybe.just(it)
                    }
                }
                .subscribe({ println(it) }, { println(it) })

    }
}