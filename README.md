# test-data-builder
[![Build Status](https://travis-ci.org/Hippoom/test-data-builder.svg?branch=master)](https://travis-ci.org/Hippoom/test-data-builder)[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hippoom/test-data-builder/badge.png)](https://maven-badges.herokuapp.com/maven-central/com.github.hippoom/test-data-builder)
A tiny library to simplify test data building.

## Installation
You can download the binary from [Maven Central Repository](http://mvnrepository.com/artifact/com.github.hippoom/test-data-builder).


## Usage
######  Building a List of test data

It is easy to build a list of test data with the static factory method`listOfSize`:

```java
import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;

List<Order> orders = listOfSize(5, // (1)
    sequence -> new OrderBuilder() // (2)
).build();                         //(3)
```

> (1) declaring the list should contain 5 elements
>
> (2) a default `Function<Integer, OrderBuilder>` takes list sequence (starts from 1) and returns a data builder, the function will be used to generate a default value for each element
>
> (3) building the list, it calls `OrderBuild.build()` by default  to generate `Order`



###### Customizing the element differs

Now each element has a default value,  you just customize the element differ in the current test case:

```java
import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;
import static com.github.hippoom.tdb.Location.IN_STORE
import static com.github.hippoom.tdb.Location.TAKE_AWAY

List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
    					theFirst(2, builder -> builder.is(TAKE_AWAY)) 		// (1)
						.number(3, builder -> builder.is(IN_STORE))   		// (2)
                        .theLast(2, builder -> builder.paid())   			// (3)
						.build();
```

> (1) declaring the first two elements apply a `Function<OrderBuilder, OrderBuilder>` that customizes the element
>
> (2) declaring the third element in the list applies a `Function<OrderBuilder, OrderBuilder>` 
>
> (3)  declaring the last two elements apply a `Function<OrderBuilder, OrderBuilder>` 



###### Customizing the multiple elements with `number()`

Sometimes you want to customize multiple elements in the middle of the list. The `number()` has a overloaded variation to help you:

```java
import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;
import static com.github.hippoom.tdb.Location.TAKE_AWAY

List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
    					.number(2, 4).apply(builder -> builder.is(TAKE_AWAY)) 		// (1)
						.build();
```
> (1) declaring the second and fourth element should apply a `Function<OrderBuilder, OrderBuilder>` that customizes the element



###### What if I want to customize a range of consecutive elements in the middle of the list

```java
import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;
import static com.github.hippoom.tdb.Location.TAKE_AWAY

List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
    					.range(2, 4).apply(builder -> builder.is(TAKE_AWAY)) 		// (1)
						.build();
```

> (1) declaring from the second(inclusive) to the fourth(inclusive) elements should apply a `Function<OrderBuilder, OrderBuilder>` that customizes the element

## 

###### What if the Builder has a different build method other than `build()`
```java
import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;

List<Order> orders = listOfSize(5, sequence -> new OrderBuilder())
    					.build(builder -> builder.anotherBuild());  //(1)
```
> (1) calls `anotherBuild()` instead of `build()` to generate the elements



## License

Licensed under Apache License (the "License"); You may obtain a copy of the License in the LICENSE file, or at [here](https://github.com/Hippoom/test-data-builder/blob/master/LICENSE).
