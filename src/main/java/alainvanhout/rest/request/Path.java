package alainvanhout.rest.request;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Path {

    private String step;
    private Queue<String> steps = new LinkedList<>();
    private List<String> passedSteps = new ArrayList<>();

    public boolean hasNextStep() {
        return !steps.isEmpty();
    }

    public boolean isDone() {
        return steps.isEmpty();
    }

    public void forceDone() {
        passedSteps.addAll(steps);
        steps = new LinkedList<>();
    }

    public String nextStep() {
        step = steps.poll();
        passedSteps.add(step);
        return step;
    }

    public String peekStep() {
        step = steps.peek();
        return step;
    }

    public Queue<String> getSteps() {
        return steps;
    }

    public String getStep() {
        return step;
    }

    public Path steps(Queue<String> steps) {
        this.steps = steps;
        return this;
    }

    public List<String> getPassedSteps() {
        return passedSteps;
    }
}
