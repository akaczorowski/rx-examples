import io.reactivex.Completable
import org.junit.Test

class StartWithTest {

    @Test
    fun startWithWhenError() {
        Completable.fromCallable { println("executed") }
                .startWith(Completable.error(Throwable()))
                .subscribe({ println("success!") }, { println("error") })
    }
}