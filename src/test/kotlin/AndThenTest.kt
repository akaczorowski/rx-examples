import io.reactivex.Completable
import org.junit.Test

class AndThenTest {

    @Test
    fun andThenWenError() {
        Completable.error(Throwable())
                .andThen(Completable.fromAction { println("executed") })
                        .subscribe({ println("success!") }, { println("error") })
    }
}