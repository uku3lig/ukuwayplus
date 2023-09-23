package continued.hideaway.mod.feat.lifecycle;

import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Lifecycle {
    public static List<Task> functionStack = new ArrayList<>();
    public static Map<String, CompletableFuture<Void>> asyncFunctionsStack = new HashMap<>();

    public void tick() {
        for (Task func : functionStack) {
            func.run();
        }

        for (CompletableFuture<Void> asyncFunc : asyncFunctionsStack.values()) {
            asyncFunc.join();
        }
        asyncFunctionsStack.clear();

    }

    public Lifecycle add(Task func) {
        functionStack.add(func);
        return this;
    }

    public Lifecycle addAsync(String taskName, CompletableFuture<Void> asyncFunc) {
        asyncFunctionsStack.put(taskName, asyncFunc);
        return this;
    }

    public boolean hasAsync(String taskName) {
        return asyncFunctionsStack.containsKey(taskName);
    }
}

