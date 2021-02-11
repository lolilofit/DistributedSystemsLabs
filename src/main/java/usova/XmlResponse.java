package usova;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class XmlResponse {
    private Map<String, AtomicInteger> userEdits = new HashMap<>();

    private Map<String, AtomicInteger> tagKeys = new HashMap<>();

    private void incrementMapElement(String s, Map<String, AtomicInteger> map) {
        if(map.containsKey(s))
            map.get(s).incrementAndGet();
        else
            map.put(s, new AtomicInteger(1));
    }
    public void incrementEdits(String user) {
        incrementMapElement(user, userEdits);
    }

    public void incrementKeys(String key) {
        incrementMapElement(key, tagKeys);
    }

    public void sortAnswer() {
        userEdits = userEdits.entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().get()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    void print() {
        System.out.println("[");
        userEdits.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println("]");

        System.out.println("[");
        tagKeys.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println("]");
    }
}
