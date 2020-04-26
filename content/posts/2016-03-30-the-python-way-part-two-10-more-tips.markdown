---
layout: post
title: 'The Python Way Part Two: 10 More Tips'
date: '2016-03-30 22:01:59'
tags:
- python
- programming
- functional-programming
---

Below are 10 more useful tips and tricks in Python. 
If you haven't checked out the first 10 tips, be sure to do so [here](http://raymondtaught.me/the-python-way-10-tips/).

Note: Let's assume we're all using Python 3.

##### 1. Ranges
Sometimes, it's easier to list out all of the elements you want in a list. Sometimes it's not. Let's say I want a list of the first 100 integers.

Instead of doing this:

```python
# Note: This doesn't compile
first_hundred = [0, 1, 2, 3, 4,...,99]
```
This is easily solved with `range()`:
```python
first_hundred = range(100)
```

Let's say you want a list of the alphabets. Without libraries, you can use the built in functions `ord()`, `chr()`, and of course, `range()`.
```python
alpha_index = range(ord('a'), ord('z') + 1)
alphabet = [chr(index) for index in alpha_index]
```

More examples with the alphabet soon.

##### 2. Zip
Suppose you had two lists, `names` and `ages`:
A person's age can be found with the corresponding index in `ages`.
So `names[0]` corresponds with `ages[0]`.

We may be tempted to do something like this:
```python
names = ["Alice", "Bob", "Chris", "Dan"]
ages = [21, 23, 10, 34]

for index, name in enumerate(names):
    print("{} is {} years old".format(name, ages[index]))
```
Introduce `zip()`. It allows us to iterate over multiple iterables simultaneously.

Here's the same example using `zip()`:
```python
names = ["Alice", "Bob", "Chris", "Dan"]
ages = [21, 23, 10, 34]

for name, age in zip(names, ages):
    print("{} is {} years old".format(name, age))
```
A bit cleaner, wouldn't you agree?

With `zip()`, we can also transpose/rotate a matrix, or group elements in a list.

##### 3. Lambda
Ah, lambdas..
In my opinion, these should **only** be used when combined with Python's functional programming functions (`map`, `filter`, or `reduce`) or when used as a parameter to another function. (More on this in a bit)

Formally, lambdas are known as anonymous functions. Informally, I think they're just another way of creating **short** functions. If you're creating a function that's more than a couple of lines long, or has complex logic, stick with creating functions with `def`.

Lambdas are for quick, short, and simple functions!

You can use the `lambda` keyword like so:
```python
addOne = lambda num: num + 1
print(addOne(2)) # 3

lessThanFive = lambda num: "Yes" if num < 5 else "No"
print(lessThanFive(10)) # No
```

Lambda functions can be more helpful when using inline functions, as we'll demonstrate in the examples below.
##### 4. Sorting
In Python, there are two built in sorting functions, `list.sort()` and `sorted()`. The main difference is that `sort()` sorts in place and only works on list, while `sorted()` returns a new iterable.

You can use them like this:
```python
numbers = [5, 2, 4, 2, 1, 6]
sorted_nums = sorted(numbers) # [1, 2, 2, 4, 5, 6]

# In place sort.
numbers.sort()
print(numbers) # [1, 2, 2, 4, 5, 6]
```

You can also pass in parameters to either of these sorting functions, such as a `key` and whether you want a `reversed` sort.

Example:
```python
people = [("Alice", 21), ("Bob", 23), ("Chris", 10), ("Dan", 34)]
people.sort(key=lambda person: person[1], reversed=True)
print(people) # [('Dan', 34), ('Bob', 23), ('Alice', 21), ('Chris', 10)]
```
Pretty cool huh? We specified the argument `reverse=True`, so it will sort the list of tuples by oldest first.
We specify the `key` to be in position `1` of the incoming argument, which we *named* `person`, a member of the iterable, `people`. 
Note the use of `lambda` here!

##### 5. Map
In the last tutorial, we went over list comprehensions and why they're so useful.

Here's an example of converting a list of strings into a list of integers using list comprehension:
```python
str_nums = ["1", "2", "3", "4", "5"]
numbers = [int(num) for num in str_nums]
print(numbers) # [1, 2, 3, 4, 5]
```

Okay, that's cool. Now here's how you can do the same thing using `map()`:
```python
str_nums = ["1", "2", "3", "4", "5"]
numbers = list(map(int, str_nums))
print(numbers) # [1, 2, 3, 4, 5]
```

Now why would we ever want to use map?

When whatever you're applying to a list is too complex for list comprehensions. You can either create a function separately to apply, or apply it using `lambda`.

Here's where you would combine the use of `lambda` and `map()`:
```python
numbers = [1, 2, 3, 4, 5]
results = list(map(lambda number: number* 5, numbers))
print(results) # [5, 10, 15, 20, 25]
```

If the function is too complex for lambda, we can create it normally like this:
```python
def crazyComplexFunction(x):
	one = 1
	two = 2
	return one + two + x * x * one * two

numbers = [1, 2, 3, 4, 5]
results = list(map(crazyComplexFunction, numbers))
print(results) # [5, 11, 21, 35, 53]
```
Also, sometimes users will experience scoping issues since they will be creating an extra variable when using list comprehensions. This can cause one to overwrite the contents of a previous variable when the variable names are the same. Notice that we didn't have to create a temporary variable  when using `map()`.

##### 6. Filter
Again, `filter` can be solved by doing list comprehensions. But for people coming from a functional programming background, keywords like `map` and `filter` are intuitive for them.

Here's an example using `filter` to take all multiples of 3 up to 100:
```python
>>> list(filter(lambda num: num % 3 == 0, range(100)))
[0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45, 48, 51, 54, 57, 60, 63, 66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 96, 99]
```

Here's the same example using list comprehensions:
```python
>>> [num for num in range(100) if num % 3 == 0]
[0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45, 48, 51, 54, 57, 60, 63, 66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 96, 99]
```

I believe Python programmers have been debating on `map` and `filter` functions versus list comprehensions for some time now. The one you decide to use is ultimately up to you.
##### 7. With
If you're coming from a Java or C background, you're probably used to opening **and** closing files. Sometimes you open files and forget to close them, and thus left with open file pointers, which can lead to accidental read/writes.

Introduce `with`, which allows Python users to safely open and read/write to a file without worrying about closing it later. This *also* closes the file if any exceptions are thrown.

Use it like so:
```python
with open("file.txt") as f:
    data = f.read()
```
Pretty straightforward.
##### 8. Split/Join
You can use `split()` and `join()` when converting strings to lists or vice versa.

With `split()`, you can pass a string to it letting it know what to split the string on. By default, it will split on empty spaces. `split()` returns a list of the string, separating each part by the delimiter.

Here's some examples:
```python
>>> "Today was a good day".split()
['Today', 'was', 'a', 'good', 'day']

>>> "1,2,3,4,5".split(",")
['1', '2', '3', '4', '5']
```
Want that list of alphabet again? Instead of using split, you can use the `list()` function to convert a string into a list of characters.
```python
import string
alphabet = list(string.ascii_lowercase)
# ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
```

`.join()` allows you to combine elements of a list into a string.
Here we are specifying to join each element of the list with a space.
```python
>>> ' '.join(['Today', 'was', 'a', 'good', 'day'])
'Today was a good day'
```

You can join lists using any characters, or none at all.

Note: using `.join()` may be faster than string concatenations  when you have **a lot** of strings to join together. Otherwise, for the sake of readability, stick with string concatenations.


##### 9. Reserved keywords
Beginners often make the mistakes of naming their variables after a reserved keyword! This doesn't just apply to Python, and can be a struggle in any programming language when trying to debug a variable that has the same name as a keyword.

As an example of why we shouldn't use reserved keywords:
```python
>>> list = [1, 2, 3]
>>> another_list = list(range(10))
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'list' object is not callable
```
Since we used `list` as a variable, when we try to convert the range iterable to a `list`, we get an error!

Obviously, in a small example like this, it is easy to debug, but in a large application, finding the source of the problem can take time, since it doesn't tell us *where* to look, especially if we used the reserved word in a different context or module.

For a list of words *not* to use in Python, you can run these commands.
```python
>>> import builtins
>>> dir(builtins)
```
Avoid them!

##### 10. Binary in Python
If you're a Computer Science student, you'll be using binary a lot.
Here are two functions `bin()` and `int()`
You can tell Python that a number is binary using the following syntax: `0b` followed by the binary string.

Here are some examples:
```python
# Convert 256 to binary
print(bin(256)) # '0b100000000'

# Convert 10101010 to an integer
print(int(0b10101010)) # 170
```

Note: you can pass a parameter to `int` letting it know the base you're working with.
```python
# Convert Hex to integer
print(int("FFFF", 16)) # 65535
```

---

Thanks for reading! If this was helpful for you, please share it with someone. Best of luck learning Python!

I'll also recommend this book: [Effective Python](http://www.amazon.com/gp/product/0134034287/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134034287&linkCode=as2&tag=raymondtaught-20&linkId=OW7R33IRZCF7MFQS)
