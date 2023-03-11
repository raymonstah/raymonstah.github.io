---
layout: post
title: "Data Structures: Hash Tables"
date: "2016-06-10 18:47:06"
tags:
  - python
  - data-structures
---

## Hash Tables

Also known as maps or dicts, depending on the programming language you're
working with.

Hash Tables are a very common data structure used to quickly access data. On
average, searching, insertion, and deletion all take `O(1)` constant time. This
is very fast!

## Analogies

A hash table works like a book index. Instead of flipping page by page to find
the topic you're looking for, you can instead check the index which tells you
the exact page the topic exists on.

Another analogy to hash tables is when your professor hands back paperwork to
the class. A lazy professor would hand them back in a large stack unsorted. A
more efficient professor would divide the stacks, and sort them by last names.
By doing it this way, students can quickly find their papers by avoiding the
stacks whose last name aren't theirs.

## How it actually works

The underlying data structure of a hash table is simply an array. On top of the
array is the hash function, which is what makes everything very fast. The hash
function will tell you where to _place_ the elements you're storing. Since
arrays are a fixed size, we also need a resize function when we decide that our
array is getting too full. This will help spread out elements more evenly.

## Dealing With Collisions

A collision is when two elements get hashed into the same spot. There are many
different ways to deal with collisions, but the most common way is to chain
them, which means that we allow multiple elements into a single slot of the
array. We would now just have to search a single slot of an array rather than
the whole thing. You can read more about collisions
[here](<https://en.wikipedia.org/wiki/Hash_table># Collision_resolution).

## Bad Hash Functions

A very basic (and useless) hash function will tell you to place every element
into the first slot. This will lead to collisions. This hash function is
specifically bad because it essentially turns your hash table into a linked
list. Every insertion will lead to a collision, and we will also waste the
unused space of the array!

Another useless hash function is random hashing. Yes, it'll spread your elements
evenly throughout the array to minimize collisions, but you'll never be able to
retrieve the elements again. If you hash the same element twice, you will likely
get different results. This is bad.

## A Good Hash Function

A good hash function will ideally spread the elements out evenly across the
array, and avoid collisions while doing so. It will also be deterministic, so if
you hash the same item twice, it will end up in the same bucket.

## Hash Table Applications

In Python, hash tables are called `dicts`, dictionary for short. We can
initialize an empty dict with `{}`

Lots of problems involve hash tables, or can benefit from them. If the problem
requires fast lookup, insertions, and deletions, using a hash table will most
likely be optimal. Let's solve some problems with them!

### 1. Determine if a string has unique characters. (case sensitive)

```python
def unique(string):
    letter_mapping = {}
    for char in string:
        if char not in letter_mapping:
            letter_mapping[char] = True
        else:
            return False
    return True
```

Test Cases:

```python
>>> unique("Raymond")
True
>>> unique("rRaAyYmMoOnNdD")
True
>>> unique("aabc")
False

```

---

### 2. Write a method to decide if one string is the permutation of another. (also case sensitive)\*\*

```python
def permutation(a, b):
    mapping_a, mapping_b = {}, {}
    for char in a:
        mapping_a[char] = 1 if char not in mapping_a else + 1
    for char in b:
        mapping_b[char] = 1 if char not in mapping_b else + 1
    return mapping_a == mapping_b
```

Test Cases:

```python
>>> permutation("Tom Marvolo Riddle", "I Am Lord Voldemort")
False
>>> permutation("silent", "listen")
True
>>> permutation("eleven plus two", "twelve plus one")
True
>>> permutation("debit card", "bad credit")
True
>>> permutation("nujabes", "Seba Jun")
False
```

Notice that in some of the cases, like nujabes, our algorithm said that it
wasn't a permutation of the late artist's name, Seba Jun. That's because it
treats lowercase and uppercase letters differently, and also counted spaces.

**Exercise:** Fix the algorithms so that it ignores spaces and is
case-insensitive.

---

Thanks y'all. If you haven't already, this book,
[Introduction to Algorithms, 3rd Edition](https://www.amazon.com/gp/product/0262033844/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0262033844&linkCode=as2&tag=raymondtaught-20&linkId=652d94806b687ba3079503b820cbc461)
is THE algorithms and data structure book to get. You will most likely use it in
your algorithms / data structure class or reference back to it when you forgot
what the heck a Fibonacci Heap is.
