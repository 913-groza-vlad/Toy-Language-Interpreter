package Model.ADTstructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockTable implements ILockTable{
    private Map<Integer, Integer> lockTable;
    private int freeLocation = 0;

    public LockTable() {
        this.lockTable = new HashMap<>();
    }

    @Override
    public boolean isDefined(Integer pos) {
        synchronized (this) {
            return lockTable.containsKey(pos);
        }
    }

    @Override
    public void update(Integer key, Integer value) {
        synchronized (this) {
            lockTable.put(key, value);
        }
    }

    @Override
    public Integer lookUp(Integer pos) {
        synchronized (this) {
            return lockTable.get(pos);
        }
    }
    @Override
    public Map<Integer, Integer> getContent() {
        synchronized (this) {
            return lockTable;
        }
    }

    @Override
    public int getFreeLocation() {
        synchronized (this) {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public Set<Integer> keySet() {
        return lockTable.keySet();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int k: this.keySet()) {
            stringBuilder.append("\t   ").append(k).append(" -> ").append(this.lookUp(k)).append("\n");
        }
        return stringBuilder.toString();
    }
}