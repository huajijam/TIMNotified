package nil.nadph.qnotified.step;

public abstract class Step implements Comparable<Step> {
    abstract public boolean step();

    abstract public boolean isDone();

    abstract public int getPriority();

    abstract public String getDescription();

    @Override
    public int compareTo(Step o) {
        return this.getPriority() - o.getPriority();
    }
}
