import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class MergeWithTest {

    @Test
    fun mergeWithSingleParallelTest() {

        val single1 = Single.fromCallable {
            println("Single 1 " + Thread.currentThread().name + " - " + System.currentTimeMillis()/1000/60)
            Thread.sleep(1000)
        }

        val single2 = Single.fromCallable {
            println("Single 2 " + Thread.currentThread().name + " - " + System.currentTimeMillis()/1000/60)
            Thread.sleep(1000)
        }

        single1.mergeWith(single2)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({ println("Success") }, { println("Error") })

        Thread.sleep(2000)

    }

    @Test
    fun mergeWithCompletableParallelTest() {

        val single1 = Completable.fromCallable {
            println("Completable 1 " + Thread.currentThread().name + " - " + System.currentTimeMillis()/1000/60)
            Thread.sleep(1000)
        }

        val single2 = Completable.fromCallable {
            println("Completable 2 " + Thread.currentThread().name + " - " + System.currentTimeMillis()/1000/60)
            Thread.sleep(1000)
        }

        single1.mergeWith(single2)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({ println("Success") }, { println("Error") })

        Thread.sleep(2000)

    }
}