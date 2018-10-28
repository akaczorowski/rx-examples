import io.reactivex.Observable
import io.reactivex.observables.ConnectableObservable
import org.junit.Before
import org.junit.Test

class MulticastingTests {

    @Before
    fun setUp() {
        println("setup")
    }

    @Test
    fun tooSoonPublishCallDifferentStreams() {
        val threeInts: ConnectableObservable<Int> = Observable.range(1, 3).publish()

        val threeRandoms = threeInts.map { Math.random() * 1000000 }
        threeRandoms.subscribe { println("Observer1: $it") }
        threeRandoms.subscribe { println("Observer2: $it") }

        threeInts.connect()
    }

    @Test
    fun publishAtTheEndTheSameStreams() {
        val threeNumbers: ConnectableObservable<Double> = Observable.range(1, 3)
                .map { Math.random() * 1000000 }
                .publish()

        threeNumbers.subscribe { println("Observer1: $it") }
        threeNumbers.subscribe { println("Observer2: $it") }

        threeNumbers.connect()


    }
}