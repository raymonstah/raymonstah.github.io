---
layout: post
title: 'The Python Way: 10 Tips'
date: '2016-03-23 08:10:08'
tags:
- python
- programming
---

Below are 10 useful tips and tricks in Python. Some of them highlight mistakes that are commonly made by beginners to the language.

Note: Let's assume we're all using Python 3.

##### 1. List Comprehensions
Suppose you have a list:
`bag = [1, 2, 3, 4, 5]`

Now you want to double each element in the list, so that it looks like this:
`[2, 4, 6, 8, 10]`

Most beginners, coming from traditional languages will do something like this:
```python
bag = [1, 2, 3, 4, 5]
for i in range(len(bag)):
    bag[i] = bag[i] * 2
```
But there's a better way:
```python
bag = [elem * 2 for elem in bag]
```
Cleaner, right? This is called list comprehensions in Python. 

For even more on list comprehensions, check out [Trey Hunner's tutorial](http://treyhunner.com/2015/12/python-list-comprehensions-now-in-color/).

##### 2. Iterating through a list
Again, suppose you have a list.
Try to **avoid** doing this if you can:
```python
bag = [1, 2, 3, 4, 5]
for i in range(len(bag)):
    print(bag[i])
```

Rather, you **should** do this:
```python
bag = [1, 2, 3, 4, 5]
for i in bag:
    print(i)
```
Since `x` is a list, you can iterate over the elements of it. Most of the time, you won't need the index of the elements anyways, but in case you do, there's the `enumerate` function available. It works like this:

```python
bag = [1, 2, 3, 4, 5]
for index, element in enumerate(bag):
    print(index, element)
```
Pretty straightforward.
##### 3. Swapping Elements
This is the way that you're probably used to if you're coming from a traditional programming language like Java or C.
```python
a = 5
b = 10

# Swap a and b
tmp = a
a = b
b = tmp
```
But naturally, in Python there's usually a better way!
```python
a = 5
b = 10
# Swap a and b
a, b = b, a
```
Nifty, huh?

##### 4. Initializing a List
Suppose you needed a list of `10` integers set to `0`. You may be tempted to do something like this:
```python
bag = []
for _ in range(10):
    bag.append(0)
```
Do this instead:
```python
bag = [0] * 10
```
Ah, how elegant.

**Warning**: This will create a shallow copy if you're working with a list within a list.

Example:
```python
bag_of_bags = [[0]] * 5 # [[0], [0], [0], [0], [0]]
bag_of_bags[0][0] = 1 # [[1], [1], [1], [1], [1]]
```
Oops! It changed all of the lists within `x`. We most likely intended to change the first list.

Do this instead:
```python
bag_of_bags = [[0] for _ in range(5)]
# [[0], [0], [0], [0], [0]]

bag_of_bags[0][0] = 1
# [[1], [0], [0], [0], [0]]
```
Also note that:
>"Premature optimization is the root of all evil"

Ask yourself, do you *really* need to initialize a list?

##### 5. String building
Often, you're going to need to print strings.
Avoid doing this if you have lots of variables to print:
```python
name = "Raymond"
age = 22
born_in = "Oakland, CA"
string = "Hello my name is " + name + "and I'm " + str(age) + " years old. I was born in " + born_in + "."
print(string)
```

Ugh, see how messy that was?
Instead, you can use `.format()` to format your strings in a nice, cleaner way.
**Do** *this* instead.
```python
name = "Raymond"
age = 22
born_in = "Oakland, CA"
string = "Hello my name is {0} and I'm {1} years old. I was born in {2}.".format(name, age, born_in)
print(string)
```
Much better!

##### 6. Returning `tuples`
Python allows you to return multiple elements in a function. This makes life a lot easier. However, this is common **mistake** when unpacking the tuple:

```python
def binary():
    return 0, 1

result = binary()
zero = result[0]
one = result[1]
```

This is unnecessary, you **should** do this instead:

```python
def binary():
    return 0, 1

zero, one = binary()
```
Use an underscore, `_` if you don't need all of the elements returned, like so:
```python
zero, _ = binary()
```
Efficient!
##### 7. Accessing `Dicts`
Often, you'll be updating and adding a `key, pair` into `dicts`.

You may be tempted to do something like this to avoid the `KeyError` when trying to access a key that doesn't exist in `dict`:
```python
countr = {}
bag = [2, 3, 1, 2, 5, 6, 7, 9, 2, 7]
for i in bag:
    if i in countr:
        countr[i] += 1
    else:
        countr[i] = 1

for i in range(10):
    if i in countr:
        print("Count of {}: {}".format(i, countr[i]))
    else:
        print("Count of {}: {}".format(i, 0))
```

But! There's a better way, using `get()`.

```python
countr = {}
bag = [2, 3, 1, 2, 5, 6, 7, 9, 2, 7]
for i in bag:
    countr[i] = countr.get(i, 0) + 1

for i in range(10):
    print("Count of {}: {}".format(i, countr.get(i, 0)))
```
You may also be interested in `setdefault()`.

Note: Here's an even simpler although slightly more costly way of doing it:
```python
bag = [2, 3, 1, 2, 5, 6, 7, 9, 2, 7] 
countr = dict([(num, bag.count(num)) for num in bag])

for i in range(10):
    print("Count of {}: {}".format(i, countr.get(i, 0)))
```
You can also do this using `dict` comprehensions.
```python
countr = {num: bag.count(num) for num in bag}
```
Both of these ways are more costly because they have to iterate the list every time `count` is called.


##### 8. Using libraries
Chances are, there's already a library that you can import which does exactly what you want.

For example, in the previous example, we created a function to count the occurrences of numbers in a list. Well, there's already a library out there that can do this.
```python
from collections import Counter
bag = [2, 3, 1, 2, 5, 6, 7, 9, 2, 7] 
countr = Counter(bag)

for i in range(10):
    print("Count of {}: {}".format(i, countr[i]))
```
Some reasons for using libraries:

* Code is correct and tested
* Their algorithm is more than likely to be optimized and thus, runs faster.
* Abstraction: its intentions are clear and well documented, which allows you to focus on what hasn't been done yet.
* Lastly, it's there so you don't have to reinvent the wheel. 

##### 9. Slicing/Stepping through a list

You can specify the `start` postition and the `stop` position like this: `list[start:stop:step]`
So, let's say you want the first 5 elements of a list:
```python
bag = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
for elem in bag[:5]:
    print(elem)
```
This is known as *slicing*. Since we specified the `stop` position of 5, we will take 5 elements from the list before stopping.

How about last 5 elements in the list?
```python
bag = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
for elem in bag[-5:]:
    print(elem)
```
Didn't see that one coming huh? The `-5` means start from the end of the list and take 5 elements.

Suppose you wanted to do something to *every other* element in a list
Here's what you **might** do:
```python
bag = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
for index, elem in enumerate(bag):
    if index % 2 == 0:
        print(elem)
```

Here's how it **should** be done:
```python
bag = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
for elem in bag[::2]:
    print(elem)

# or equivalently for ranges
bag = list(range(0,10,2))
print(bag)
```
This is known as *stepping* through a list. `list[::2]` means that you are going through this list and taking one element for every two steps.
Some other cool things you can do with steps are `list[::-1]` which will reverse a list.

##### 10. Tabs vs Spaces

Long version: Mixing spaces and tabs in Python will lead to a disaster. You will see a `IndentationError: unexpected indent`. Regardless of your choice of tabs or space, you should be consistent with it throughout your files and projects.

One more reason to use spaces rather than tabs is that not all editors treat tabs the same. Depending on the editor you use, tabs may be treated as 2 to 8 spaces.

One thing you can do when writing code is to specify to treat tabs as spaces. This way, you can automatically specify how many spaces a tab is worth. 4 spaces is the norm for most Python users.

TLDR: Use spaces, or treat tabs as spaces for the best of both worlds.

---

I'd like to thank Reddit users from [/r/learnpython](https://www.reddit.com/r/learnpython/):

1. /u/treyhunner
2. /u/bbatwork
3. /u/symmitchry
4. /u/dunkler_wanderer

For helping me make some of these tips ever better! 


Thanks for reading! If this was helpful for you, please share it with someone. Best of luck learning Python!

Edit: For *more* tips, click [here](http://raymondtaught.me/the-python-way-part-two-10-more-tips/)!

I'll also recommend this book: [Effective Python](http://www.amazon.com/gp/product/0134034287/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134034287&linkCode=as2&tag=raymondtaught-20&linkId=OW7R33IRZCF7MFQS)

