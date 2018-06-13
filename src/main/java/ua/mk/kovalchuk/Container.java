package ua.mk.kovalchuk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Container {

    private List<Long> container;
    private long capacity;
    private long maximum;

    public Container(final long capacity) {
        container = new ArrayList<Long>();
        maximum = 0;
        this.capacity = capacity;
    }

    public long getMaximum() {
        return maximum;
    }

    public int getCount(final long item) {
        int count = 0;
        for (int i = 0; i < container.size(); i++) {
            if (container.get(i) == item)
                count++;
        }
        return count;
    }

    public void add(final long item) {
        if (container.size() > 0) {
            if ((container.get(container.size() - 1) != item) ||
                    (container.size() >= capacity))
                deleteLast();
        }
        container.add(item);
        if (container.size() > maximum)
            maximum = container.size();

        System.out.println(Arrays.toString(container.toArray()) + "/" + capacity);
    }

    private void deleteLast() {
        container.remove(container.size() - 1);
    }
}

