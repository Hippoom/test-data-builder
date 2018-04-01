package com.github.hippoom.tdb;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.github.hippoom.tdb.reflection.MethodInvoker;
import lombok.Getter;

/**
 * Building a List of test data.
 * <p> It is easy to build a list of test data with the static factory method {@link #listOfSize(int, Function)}.</p>
 * <pre>
 * {@code
 *     import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;
 *
 *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder()).build();
 * }
 * </pre>
 *
 * @param <T> the type of the test data builder
 */
public class GenericTestDataListBuilder<T> {

    @Getter(PRIVATE)
    private List<T> elements;
    private List<T> currentElements;

    private GenericTestDataListBuilder(List<T> elements) {
        this.elements = elements;
    }

    /**
     * Apply a function to the first n elements of the list.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .theFirst(2, builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param size   number of elements.
     * @param wither function that takes the test data builder.
     * @return this for fluent api.
     * @see #theFirst(int)
     */
    public GenericTestDataListBuilder<T> theFirst(int size,
                                                  Function<T, T> wither) {
        return theFirst(size).apply(wither);
    }

    /**
     * Select the first n elements of the list. You can apply a function to to customize them.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .theFirst(2).apply(builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param size number of elements
     * @return this for fluent api.
     * @see #theFirst(int, Function)
     * @see #apply(Function)
     */
    public GenericTestDataListBuilder<T> theFirst(int size) {
        return number(IntStream.range(1, size + 1).toArray());
    }

    /**
     * Apply a function to the last n elements of the list.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .theLast(2, builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param size   number of elements.
     * @param wither function that takes the test data builder.
     * @return this for fluent api.
     * @see #theLast(int) (int)
     */
    public GenericTestDataListBuilder<T> theLast(int size,
                                                 Function<T, T> wither) {
        return theLast(size).apply(wither);
    }

    /**
     * Select the last n elements of the list. You can apply a function to to customize them.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .theLast(2).apply(builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param size number of elements
     * @return this for fluent api.
     * @see #theLast(int, Function)
     * @see #apply(Function)
     */
    public GenericTestDataListBuilder<T> theLast(int size) {
        int startElementSequence = getElements().size() - size + 1;
        int lastElementSequence = getElements().size() + 1;
        return number(IntStream.range(startElementSequence, lastElementSequence).toArray());
    }

    /**
     * Apply a function to selected elements.
     *
     * @param wither to customize the test data builder
     * @return this for fluent api.
     * @see #theFirst(int)
     * @see #theLast(int)
     * @see #number(int...)
     * @see #range(int, int)
     * @see #all()
     */
    public GenericTestDataListBuilder<T> apply(Function<T, T> wither) {
        this.currentElements
            .forEach(wither::apply);
        return this;
    }

    /**
     * Apply a function to the number n element of the list, starts from 1.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .number(2, builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param sequence starts from 1
     * @param wither   customize the selected elements
     * @return this for fluent api.
     * @see #number(int...)
     */
    public GenericTestDataListBuilder<T> number(int sequence,
                                                Function<T, T> wither) {
        return number(sequence).apply(wither);
    }

    /**
     * Apply a function to the number n elements of the list, starts from 1.
     * * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .number(2, 3, 4).apply(builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param sequences an array of number n elements
     * @return this for fluent api.
     * @see #number(int, Function)
     */
    public GenericTestDataListBuilder<T> number(int... sequences) {
        this.currentElements = IntStream.of(sequences)
            .map(sequence -> sequence - 1)
            .mapToObj(index -> getElements().get(index))
            .collect(toList());
        return this;
    }

    /**
     * Apply a function to consecutive elements of the list.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .range(2, 4).apply(builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param fromSequence starts from 1, inclusive
     * @param toSequence   starts from 1, inclusive
     * @return this for fluent api.
     * @see #apply(Function)
     */
    public GenericTestDataListBuilder<T> range(int fromSequence, int toSequence) {
        this.currentElements = getElements().subList(fromSequence - 1, toSequence);
        return this;
    }

    /**
     * Apply a function to all elements.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .all(builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @param wither to customize the builders
     * @return this for fluent api.
     * @see #all()
     */
    public GenericTestDataListBuilder<T> all(Function<T, T> wither) {
        return all().apply(wither);
    }

    /**
     * Select all elements.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
     *         .all().apply(builder -> builder.paid())
     *         .build();
     * }
     * </pre>
     *
     * @return this for fluent api.
     * @see #all(Function)
     */
    public GenericTestDataListBuilder<T> all() {
        this.currentElements = new ArrayList<>(getElements());
        return this;
    }

    /**
     * Close the builder and return a list of test data.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder()).build(builder -> builder.toModel());
     * }
     * </pre>
     *
     * @param builder the function that convert the test data builder to test data.
     * @param <B>     the type of the test data.
     * @return a list of test data.
     */
    public <B> List<B> build(Function<T, B> builder) {
        return elements.stream()
            .map(builder)
            .collect(toList());
    }

    /**
     * Close the builder and return a list of test data.
     * <p>This method assumes the test data builder has a `build()` method</p>
     *
     * @param <B> the type of the test data.
     * @return a list of test data.
     * @see #build(Function)
     */
    public <B> List<B> build() {
        return build(t -> MethodInvoker.invoke(t, "build"));
    }

    /**
     * A convenient static factory method to initialize a list of test data builder.
     * <p> Example: </p>
     * <pre>
     * {@code
     *     import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;
     *
     *     List<Order> orders = listOfSize(5, sequence -> new OrderBuilder()).build();
     * }
     * </pre>
     *
     * @param size      of the list
     * @param byDefault a function that takes the sequence of the list (starts from 1) and returns a test data builder.
     * @param <T>       the type of the test data builder.
     * @return GenericTestDataListBuilder that you can customize the elements further.
     */
    public static <T> GenericTestDataListBuilder<T> listOfSize(int size,
                                                               Function<Integer, T> byDefault) {
        return new GenericTestDataListBuilder<>(
            IntStream.range(0, size)
                .map(index -> index + 1)
                .mapToObj(byDefault::apply)
                .collect(toList()));
    }
}
