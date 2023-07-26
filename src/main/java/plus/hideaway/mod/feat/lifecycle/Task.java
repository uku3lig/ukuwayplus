package plus.hideaway.mod.feat.lifecycle;

public class Task {

    Runnable runnable;
    int wait;
    int pointer = 0;

    private Task(int w, Runnable r) {
        wait = w;
        runnable = r;
    }

    public void run() {
        if (pointer == wait) {
            runnable.run();
            pointer = 0;
        }
        else pointer++;
    }

    public static Task of(Runnable r, int w) {
        return new Task(w, r);
    }

}
