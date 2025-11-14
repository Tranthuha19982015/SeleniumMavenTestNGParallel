package com.hatester.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestStepHelper {
    private static final Map<Long, List<String>> stepMap = new HashMap<>();
    private static final Map<Long, String> actualResultMap = new HashMap<>();

    public static void addStep(String step) {
        long threadId = Thread.currentThread().getId();
        stepMap.computeIfAbsent(threadId, k -> new ArrayList<>()).add(step);
    }


    // Lưu message lỗi thực tế
    public static void setActualResult(String message) {
        actualResultMap.put(Thread.currentThread().getId(), message);
    }

    // Lấy message lỗi thực tế
    public static String getActualResult() {
        return actualResultMap.getOrDefault(Thread.currentThread().getId(), "");
    }

    public static List<String> getSteps() {
        return stepMap.getOrDefault(Thread.currentThread().getId(), new ArrayList<>());
    }

    public static void clear() {
        stepMap.remove(Thread.currentThread().getId());
    }
}
