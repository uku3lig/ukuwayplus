package continued.hideaway.mod.feat.lifecycle;

import java.util.ArrayList;
import java.util.List;

public class Lifecycle {
    public static List<Task> functionStack = new ArrayList<>();
    public static List<Task> oneOffStack = new ArrayList<>();

    public void tick() {
        for (Task oneOff : oneOffStack) {
            oneOff.run();
        }
        oneOffStack.clear();

        for (Task func : functionStack) {
            func.run();
        }
    }

    public Lifecycle add(Task func) {
        functionStack.add(func);
        return this;
    }

    public Lifecycle addOneOff(Task func) {
        oneOffStack.add(func);
        return this;
    }
}
