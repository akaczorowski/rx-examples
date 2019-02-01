import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class FilterAndDoOnNextTest {

    @Test
    fun filterAndDoOnNextTest() {

        Observable.just(1, 2, 3, 4, 5)
                .filter { println("filter $it"); true}
                .doOnNext { println("doOnNext") }
                .subscribeOn(Schedulers.io())
                .subscribe { println("onNext: $it") }

        Thread.sleep(1000)
    }
}