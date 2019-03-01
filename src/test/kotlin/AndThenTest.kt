import io.reactivex.Completable
import org.junit.Test

class AndThenTest {

    @Test
    fun andThenWenError() {
        Completable.error(Throwable())
                .andThen(Completable.fromAction { println("executed") })
                        .subscribe({ println("success!") }, { println("error") })
    }

    @Test
    fun andThenTest() {
        Completable.complete()
                .andThen{ println("executed")
                        it.onComplete()}
                .subscribe({ println("success!") }, { println("error") })

        Thread.sleep(2000)
    }
}