package com.example.zxl.cloudmanager.tabBar.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerLab {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "客户 " + position,"12233300" + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Number: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("11111111111");
        }
        return builder.toString();
    }

    public static class DummyItem {
        public final String id;
        public final String content;
        public final String telephone;

        public DummyItem(String id, String content, String number) {
            this.id = id;
            this.content = content;
            this.telephone =number;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
