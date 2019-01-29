import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleSource
import org.junit.Test

class FlatMapSingleTest {

    @Test
    fun flatMapSingleError(): Unit {

        Observable.just(1, 2, 3)
                .flatMapSingle {
                    if(it == 2){
                        Single.error(Throwable("KUKU")) }
                    else{
                        Single.just(it)
                    }
                }
                .subscribe({ println(it)}, { println(it)})

    }
}