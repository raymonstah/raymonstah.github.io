---
layout: post
title: 'Python: Determine if a string has all unique characters'
date: '2016-03-22 23:26:00'
tags:
- python
- ctci
- programming
---

###### Given a string, return True if it has all unique characters. Return False otherwise

For example: `raymond` should return `True`, but `French Fries` should return `False`.

There are at least three different ways you can do this, but in this tutorial we'll go over two ways.

1. Using a `dict` (hash table)
2. Sorting the string

###### Using a `dict`

1. First we want to create a dictionary like this: `mappings = {}`
2. Then we want to loop over the string and checking if the character is in `mappings`
3. If it is, then we know that this string is NOT unique. We may return `False`. Otherwise, we can mark it as `True` and continue.
4. When we're done looping through the string and have not found any duplicate characters, simply return `True`.

Here is the code that implements the logic above.

```python
def unique(string):
 mappings = {}
 for char in string:
  if char not in mappings:
   mappings[char] = True
  else:
   return False
 return True

```

This algorithm takes just `O(n)` time and space, where `n` is the number of characters in the string. This is because we loop over the string just once.

###### Sorting the string first

Here's a cool way to solve this problem, although not as efficient as our first solution.

1. We can sort the string first, calling by calling `sorted(string)` and storing it in a variable `sorted_string`
2. Iterate over `sorted_string` and compare neighbors. If one character is the same as its neighbor, then we can return `False` right away.
3. When we're done iterating, we know that all characters are unique, so return `True`.

Here's the code for that:

```python
def unique_sort(string):
 sorted_string = sorted(string)
 for i, letter in enumerate(sorted_string[:-1]):
  if sorted_string[i+1] == letter:
   return False
 return True
```

We do `sorted_string[:-1]` because we want to iterate all but the last character. Python's slicing features are very helpful.

This algorithm however, is bounded by the time it takes to sort the string, which is usually `O(n*log(n))`.

---
If you found this helpful, please share! Thank you very much.

For similar problems and even more practice, I highly recommend this book: [Cracking The Coding Interview](http://www.amazon.com/gp/product/0984782850/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0984782850&linkCode=as2&tag=raymondtaught-20&linkId=B5YLU3P62LR6PO25)
