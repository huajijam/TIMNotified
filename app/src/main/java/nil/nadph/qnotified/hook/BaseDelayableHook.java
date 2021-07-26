package nil.nadph.qnotified.hook;

import nil.nadph.qnotified.SyncUtils;
import nil.nadph.qnotified.step.Step;
import nil.nadph.qnotified.util.NonNull;

public abstract class BaseDelayableHook extends AbsDelayableHook {

    @Override
    public boolean isTargetProc() {
        return (getEffectiveProc() & SyncUtils.getProcessType()) != 0;
    }

    @Override
    public abstract int getEffectiveProc();

    @Override
    public abstract boolean isInited();

    @Override
    public abstract boolean init();

    @Override
    public boolean sync() {
        return true;
    }

    @NonNull
    @Override
    public abstract Step[] getPreconditions();

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean checkPreconditions() {
        for (Step i : getPreconditions()) {
            if (!i.isDone()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + (isInited() ? "inited" : "") + "," + (isEnabled() ? "enabled" : "") + "," + SyncUtils.getProcessName() + ")";
    }

}