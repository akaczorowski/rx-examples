import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TrampolineExample {
    static String result = "";

    public static void run() {
        Scheduler scheduler = Schedulers.trampoline();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(() -> {
            result += Thread.currentThread().getName() + "Start";
            worker.schedule(() -> {
                result += "_middleStart";
                worker.schedule(() ->
                        result += "_worker_"
                );
                result += "_middleEnd";
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result += "_mainEnd";
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Assert.assertTrue(result.equals("mainStart_mainEnd_middleStart_middleEnd_worker_"));

        System.out.println(result);
    }
}
