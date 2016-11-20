package hm.binkley.layers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public enum DisplayStyle {
    BRACES(", ", "{", "}"),
    BRACKETS(", ", "[", "]");
    private static final ToString display = new ToString();

    private final String delimiter;
    private final String prefix;
    private final String suffix;

    DisplayStyle(final String delimiter, final String prefix,
            final String suffix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public final String display(final Map<?, ?> map) {
        return map.entrySet().stream().
                map(display).
                collect(joining(delimiter, prefix, suffix));
    }

    private static final class ToString
            implements Function<Entry<?, ?>, String> {
        @Override
        public String apply(final Entry<?, ?> e) {
            final Object key = e.getKey();
            final String k;
            if (key instanceof Class)
                k = ((Class) key).getSimpleName();
            else if (key instanceof Layer)
                k = ((Layer) key).name();
            else
                k = key.toString();
            final Object value = e.getValue();
            final String v;
            if (value instanceof String)
                v = "\"" + value + '"';
            else
                v = value.toString();
            return k + "=" + v;
        }
    }
}
