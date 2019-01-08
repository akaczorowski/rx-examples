import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.junit.Test


class PublishSubjectTests {

    @Test
    fun publishSubjectDispose() {
        val disposables = CompositeDisposable()
        val publishSubject = PublishSubject.create<Int>()
        val source = publishSubject.doFinally {
            println("doFinally")
        }

// It will get 1, 2, 3, 4 and onComplete
        val subscriber1 = source.subscribe { println("subscriber1: $it") }
        val subscriber2 = source.subscribe { println("subscriber2: $it") }

        publishSubject.onNext(1)
        publishSubject.onNext(2)
        subscriber1.dispose()
        publishSubject.onNext(3)

// It will get 4 and onComplete for second observer also.

        publishSubject.onNext(4)
        publishSubject.onComplete()

        Thread.sleep(1000)
    }
}