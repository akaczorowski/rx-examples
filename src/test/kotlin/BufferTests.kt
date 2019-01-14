import io.reactivex.Observable
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

class BufferTests {

    @Test
    fun bufferTest(): Unit {

        Observable.just(1,2,3,4)
                .delay(500, TimeUnit.MILLISECONDS)
                .buffer(200, TimeUnit.MILLISECONDS)
                .subscribe {
                    println("Size: ${it.size}")
                }

        Thread.sleep(600)
    }
}