import io.reactivex.Completable
import org.junit.Test

class ExceptionInRxStreamTest {

    @Test
    fun exceptionInStream() {
        Completable.fromCallable {
            doSomething()
        }
                .andThen{ println("executed")
                    it.onComplete()}
                .subscribe({ println("success!") }, { it.printStackTrace() })
    }

    private fun doSomething() {
        println("asasa")
        throw Exception("Exception throw!!!")
    }
}