import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ObservableEmptyExample {
    companion object {
        @JvmStatic
        fun run() {
            Observable.empty<Any>()
                    .delay(3, TimeUnit.SECONDS)
                    .subscribe(System.out::println,Throwable::printStackTrace) {println("done")}

            Thread.sleep(5000)
        }
    }
}