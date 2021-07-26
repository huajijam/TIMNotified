package nil.nadph.qnotified.step;

import nil.nadph.qnotified.util.DexKit;

public class DexDeobfStep extends Step {
    private final int id;

    public DexDeobfStep(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean step() {
        return DexKit.prepareFor(id);
    }

    @Override
    public boolean isDone() {
        return DexKit.checkFor(id);
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public String getDescription() {
        if (id / 10000 == 0) {
            return "定位被混淆类: " + DexKit.c(id);
        } else {
            return "定位被混淆方法: " + DexKit.c(id);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DexDeobfStep that = (DexDeobfStep) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
