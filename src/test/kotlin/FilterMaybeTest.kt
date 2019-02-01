import io.reactivex.Maybe
import org.junit.Test

class FilterMaybeTest {

    @Test
    fun filterMaybeEmpty(): Unit {

        Maybe.just<List<Int>>(listOf())
                .filter { it.isNotEmpty() }
                .subscribe({ println(it) }, { println(it) }, { println("onComplete") })
    }
}