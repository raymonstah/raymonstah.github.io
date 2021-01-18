---
layout: post
title: 'Python: Hello World With Prime Numbers'
date: '2016-06-19 22:03:01'
tags:
- python
- programming
- puzzles
---

Please note that this is simply for fun and learning purposes.

You can achieve the same results like this:

```python
print("hello, world")
```

But this way is so much crazier.

```python
>>> ''.join([chr(x) for x in [int(x,16) for x in [hex(2 * 2 * 5 * 7 * 37 * 149 * 5417 * 148781 * 51939996061871)[i:(i+2)] for i in range(25)][2::2]]])
hello, world
```

How in the world does this print `"hello, world"`?

Let's break this down:

```python
>>> hex(2 * 2 * 5 * 7 * 37 * 149 * 5417 * 148781 * 51939996061871) 
'0x68656c6c6f2c20776f726c64'
```

We can store this hex string into a variable, `h`.

Notice that these two are the same expressions below are the same, and the second is easier to understand. We start at 2, and take every other element.

```python
>>> range(25)[2::2]
range(2, 25, 2)
# 2, 4, 6, 8, 10, .. 24
```

Okay cool, now we're creating a list of chunks of `h`, with each chunk being a 2 character substring.

```python
>>> [h[i:(i+2)] for i in range(25)[2::2]]
['68', '65', '6c', '6c', '6f', '2c', '20', '77', '6f', '72', '6c', '64']
```

Let's store this `list` into a variable `str_lst`.

```python
>>> [int(x,16) for x in str_lst]
[104, 101, 108, 108, 111, 44, 32, 119, 111, 114, 108, 100]
```

We converted our hex strings into integers using the `int()` function passing the second parameter `16` to let it know that we're working with `base16 (hex)`.

Let's store this list of numbers into a variable `num_list`.

Great, a bunch of numbers!
Now, simply convert each number to it's [ASCII](http://www.asciitable.com/) representation, resulting in the new list:

```python
>>> [chr(x) for x in num_list]
['h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd']
```

Just about done!
Simply join the list into a string.

```python
>>> ''.join([chr(x) for x in num_list])
'hello, world'
```

---

Thanks for following along. If you found this article helpful, be sure to check out my other tutorials on Python.
