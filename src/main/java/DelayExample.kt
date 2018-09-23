import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DelayExample {

    companion object {
        @JvmStatic
        fun run() {
            Observable.just("John", "Andrew", "Mike", "Larry", "Ozzy")
                    .delay(3, TimeUnit.SECONDS)
                    .subscribe { s -> System.out.println(s) }

            Thread.sleep(5000)
        }
    }
}