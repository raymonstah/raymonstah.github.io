---
layout: post
title: The Birthday Paradox
date: '2016-04-13 08:33:56'
tags:
- python
- math
- puzzles
- probability
- simulation
---

Suppose there's a party of N people. How many people do you think it takes to find a pair of people who have the same birthdays?

Well, the answer isn't what you expect, hence the title of this post: The Birthday Paradox.

You might guess that it would take quite a lot of people since there are so many different birthdays, 366 to be exact.

However, this isn't true!

In fact, it only takes *23* people in a room for a 50% chance of two people having the same birthday!

Not good enough? Well if you have just **70** people, there's a 99.9% chance of a matching birthday!

Not convinced? Let's run some simulations with Python.
#### Prove it with Python:

Assume we're using Python 3:

```python
import random

# 365 distinct birthdays including February 29th.
birthdays = 365

# Returns true if two people out of n_people have the same birthday.
def birthday(n_people):
    distinct_dates = set()
    for person in range(n_people):
        birthdate = random.randrange(birthdays + 1)
        if birthdate in distinct_dates:
            return True
        else:
            distinct_dates.add(birthdate)
    return False


def simulations(n_people, trials):
    successes = 0
    for trial in range(trials):
        if birthday(n_people):
            successes += 1
    return successes/trials

```

Pretty straightforward, one function, `birthday()` that determines if there's a match given `n_people`. Another function `simulations()` that returns the probability of a match given `n_people` and the number of trials to run.

Let's test it out!
```python
>>> simulations(23, 10000)
0.5048
>>> simulations(70, 10000)
0.9992
>>> simulations(366, 10000)
1.0
```

Our simulations seems to give us the result we expected!

Notice that if we run the simulation with over 365 birthdays, we will always get a probability of 1. That's because well, two people must have the same birthday if we take all possible birthdays + 1 random birthday.

This is known as the [**pigeonhole principle**](https://en.wikipedia.org/wiki/Pigeonhole_principle).

---
For more on probability, cool puzzles, and discrete math, check out this book: 
<a  href="http://www.amazon.com/gp/product/0201558025/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201558025&linkCode=as2&tag=raymondtaught-20&linkId=VYPGS3UB5GNPPMKI">Concrete Mathematics: A Foundation for Computer Science (2nd Edition)</a><img src="http://ir-na.amazon-adsystem.com/e/ir?t=raymondtaught-20&l=as2&o=1&a=0201558025" width="1" height="1" border="0" alt="" style="border:none !important; margin:0px !important;" />

It's written by the famous Computer Scientist, [Donald Knuth](https://en.wikipedia.org/wiki/Donald_Knuth) !
