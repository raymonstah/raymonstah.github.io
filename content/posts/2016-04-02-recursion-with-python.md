---
layout: post
title: Recursion with Python
date: "2016-04-02 21:01:28"
tags:
  - python
  - programming
  - functional-programming
  - recursion
---

### What is Recursion?

> If you don't know recursion, read this sentence again.

1. How to Learn Recursion
2. See Step 1

Some real world examples of recursion:

1. An onion. Peel one layer of an onion, what do you have? An onion.
2. [Fractals](https://en.wikipedia.org/wiki/Mandelbrot_set), and those found in nature.

Recursive programming involves a function calling itself. Note that Python has a maximum recursion stack of `1,000`, but if you ever need to increase it for learning purposes, you can do so like this:

```python
import sys
sys.setrecursionlimit(10000)
```

Also, note that anything that can be done recursively can also be done iteratively, and vice versa. Some algorithms should naturally be solved using recursion, like [Towers of Hanoi](https://en.wikipedia.org/wiki/Tower_of_Hanoi), while others are better suited for iteration.

You should always try to write your code iteratively in Python since recursion isn't optimized and is often slower than iteration. The examples that I'm going to show you are strictly for educational purposes.

### Fibonacci

A lot of times, the fibonacci sequence is shown to beginners as an example to recursion.

```python
def fibonacci(n):
    if n == 0 or n == 1:
        return n
    return fibonacci(n-1) + fibonacci(n-2)
```

In mathematics, recursion is the natural way to solve the fibonacci problem. The fibonacci mapping of an index, `n` is always the sum of the two previous fibonacci numbers.

I believe this is a bad example for a couple of reasons:

1. It's not practical. Try `fibonacci(20)`. It'll blow up because it has to recompute the same sequences multiple times, coming out to a slow `O(2^n)` exponential runtime.
2. There's already a constant time formula to solve the fibonacci problem. Why would we use recursion or even an iterative approach?

Here are some better examples used to demonstrate recursion.

### Length of a List

Let's say you want to find the length of a list, `some_list`. In recursion, there is often a base case, and a recursive case.

- Our base case here is that if `some_list` is empty, then the count of that list is `0`.
- Otherwise, we add `1` and make a _recursive_ call to the function, passing the parameter as all but the first element of `some_list`.

```python
def findLengthOfList(some_list):
    return 0 if some_list == [] else 1 + findLengthOfList(some_list[1:])

print(findLengthOfList([1, 2, 3, 4, 5])) # 5
```

I mean obviously, in a real program you should use `len(some_list)`, but remember, this is for educational purposes!

### Max of a List

- Our base case here is to return `max_given` if the list is empty.
- The recursive step is to pass the _body_ (all but the first) of the list to our function again, along with the `max_so_far` which we computed.

```python
def findMaxOfList(some_list, max_given):
    if some_list == []:
        return max_given
    max_so_far = max_given if max_given > some_list[0] else some_list[0]
    return findMaxOfList(some_list[1:], max_so_far)

print(findMaxOfList([1, 3, 6, 2, 3, -10], -float("Inf"))) # 6
```

Likewise, you can always use `max(some_list)` instead of rolling your own function.

### Reversing a List

Once again, we can break the algorithm into a base case and a recursive case.

- If we receive an empty list, or a list of a single element, then it is already "reversed". Simply return that list.
- If we receive a list of 2 or more elements, we can return a new list, consisting of the last element of `some_list` followed by a recursive call with the parameter as all but the last element of `some_list`.

```python
def reverse(some_list):
    if len(some_list) < 2:
        return some_list
    return [some_list[-1]] + reverse(some_list[:-1])

print(reverse(list(range(10)))) # [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
```

Don't roll your own `reverse` function in production! Use the built in `reversed()` function instead.

### Sorting a List using Quick sort

This algorithm is super elegant when solved by recursion, even though it is slower than the iterative version of quick sort.

It works like this:

- Base case: if we receive an empty list, then it is already sorted.
- Take the first element of a list, `first`.
- Partition the rest of the list by those that are greater than `first`, and those that are less than `first`, which are computed recursively.
- Finally, we return a newly created list which `less_than_first` comes first, followed by `first`, and lastly, we append `greater_than_first`.

```python
def quicksort(some_list):
    if some_list == []:
        return []
    first = some_list[0]
    less_than_first = quicksort([elem for elem in some_list[1:] if elem < first])
    greater_than_first = quicksort([elem for elem in some_list[1:] if elem >= first])
    return less_than_first + [first] + greater_than_first

print(quicksort([4, 3, 1, 2, 5])) # [1, 2, 3, 4, 5]
```

Don't forget about `sorted()` or `.sort()` if you want to apply this to real code!

---

Thanks for reading! Please ask questions or share your thoughts!

For programming languages that optimize recursion, check out Functional Programming languages like Haskell, or a Lisp.

If you'd like to learn more about recursion and functional programming, check out this book:
[The Little Schemer - 4th Edition](http://www.amazon.com/gp/product/0262560992/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0262560992&linkCode=as2&tag=raymondtaught-20&linkId=AZJB4SGTR4DHRK57)
