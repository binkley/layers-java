package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public abstract class XEnum<E extends XEnum<E>>
        implements Comparable<E>, Serializable {
    private final String name;
    private final int ordinal;

    public final String name() {
        return name;
    }

    public final int ordinal() {
        return ordinal;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    protected final Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public final int compareTo(final E that) {
        final XEnum<E> self = this;
        if (self.getClass() != that.getClass() && // optimization
                self.getDeclaringClass() != that.getDeclaringClass())
            throw new ClassCastException();
        return self.ordinal - ((XEnum<?>) that).ordinal;
    }

    @SuppressWarnings("unchecked")
    public final Class<E> getDeclaringClass() {
        final Class<?> clazz = getClass();
        final Class<?> zuper = clazz.getSuperclass();
        return (Class<E>) (XEnum.class == zuper ? clazz : zuper);
    }

    /*
    public static <T extends XEnum<T>> T valueOf(final Class<T> enumType,
            final String name) {
        final T result = enumType.enumConstantDirectory().get(name);
        if (null != result)
            return result;
        if (null == name)
            throw new NullPointerException("Name is null");
        throw new IllegalArgumentException(
                "No enum constant " + enumType.getCanonicalName() + "."
                        + name);
    }
    */

    @Override
    protected final void finalize() { }
}
