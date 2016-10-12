package hm.binkley.layers;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public enum DisplayStyle {
    BRACES(", ", "{", "}"),
    BRACKETS(", ", "[", "]");
    private final String delimiter;
    private final String prefix;
    private final String suffix;

    DisplayStyle(final String delimiter, final String prefix,
            final String suffix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public final String display(final Map<Object, ?> map) {
        final Function<Map.Entry<Object, ?>, String> display = e -> {
            final Object key = e.getKey();
            return (key instanceof Class ? ((Class) key).getSimpleName()
                    : key) + "=" + e.getValue();
        };
        return map.entrySet().stream().
                map(display).
                collect(joining(delimiter, prefix, suffix));
    }
}
