---
layout: post
title: Making a Biased Penny Fair
date: '2016-04-07 01:18:00'
tags:
- python
- math
- puzzles
- probability
- simulation
---

#### Definitions:
Fair penny: This type of penny has a 50/50 chance of landing on heads or tails. It's *fair*.

Biased penny: A penny that's not fair.

Assumptions:

1. Each flip is independent of each other.
2. The bias level of getting heads cannot be 1 or 0. (i.e. Always getting heads or never getting heads)

----

Now how can we make a biased penny fair? The solution is actually very elegant and simple:
#### Flip it twice.

If you flip a penny twice, here are your outcomes:

1. Heads, Heads
2. Heads, Tails
3. Tails, Heads
4. Tails, Tails

Suppose your coin was biased, say it had a 60% chance of landing on Heads, and 40% of landing on Tails. (We'll assume it's impossible to land on its side)

Then the probability of each event:

1. `Pr[HH] = 0.6 * 0.6 = 0.36`
2. `Pr[HT] = 0.6 * 0.4 = 0.24`
3. `Pr[TH] = 0.4 * 0.6 = 0.24`
4. `Pr[TT] = 0.4 * 0.4 = 0.16`

Notice that `Pr[HT] == Pr[TH]`, in other words the probability of a double coin flip landing on Heads followed by Tails is the **same** as the probability of a flip landing on Tails followed by a flip landing on Heads.

We'll use this fact to make the penny fair.

Now we can make this penny fair by flipping it *twice*, then viewing its outcome.

* If the outcome was `HH` or `TT`, then we'll discard these results.
* If the outcome was `HT` or `TH`, then we'll take the result of our first flip.

#### Using Python to show this:
```python
from collections import namedtuple
import random

Coin = namedtuple('Coin', ['Heads', 'Tails'])
coin = Coin(Heads=.60, Tails=.40) # Our biased coin.

def flip(prob_head):
	return 'Heads' if random.random() < prob_head else 'Tails'

# Confirm that our coin is biased.
simulations = 1000000
flips = [flip(coin.Heads) for _ in range(simulations)]

pHead = len([flip for flip in flips if flip == 'Heads'])
print("Probability of Head with Biased Coin", pHead / len(flips))
# Probability of Head with Biased Coin 0.60039

# Let's make our coin fair!
flips = []
for _ in range(simulations):
	first, second = flip(coin.Heads), flip(coin.Heads)
	if first != second:
		flips.append(first)

pHead = len([flip for flip in flips if flip == 'Heads'])
print("Probability of Head with Biased Coin", pHead / len(flips))
# Probability of Head with Biased Coin 0.49952640042293545
```

Pretty cool huh?
