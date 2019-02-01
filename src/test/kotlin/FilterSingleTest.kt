import io.reactivex.Single
import org.junit.Test

class FilterSingleTest {

    @Test
    fun filterMaybeEmpty(): Unit {

        Single.just<List<Int>>(listOf())
                .filter { it.isNotEmpty() }
                .subscribe({ println(it) }, { println(it) }, { println("onComplete") })
    }
}