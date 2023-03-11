---
layout: post
title: Bitwise Operation Analogies
date: "2016-04-22 19:14:23"
tags:
  - binary
---

In binary, we have two options.

- Yes/No
- True/False
- 1/0
- Good/Bad
- On/Off

There are a bunch of operations that can be performed on binary. Here are some
of them, and some analogies that can help you understand them better.

---

## NOT

This one is pretty simple.

### Examples

- `NOT 0 = 1`
- `NOT True = False`
- `NOT 1001 = 0110`

### Analogy

You can think of `NOT` as simply taking the opposite!

---

## OR

When performing the OR operator on two objects, A, and B, either A or B must
have the truth value. Just remember that for `OR`, you can be greedy and have
**both** options.

### Examples

- `True OR False = True`
- `True OR True = True`
- `False OR False = False`
- `False OR True = True`

### Analogy

Your mother wants the dishes to be done. She will yell at you and your sibling
if they are not done by the end of the day. The work can be split! It doesn't
matter who does it, but as long as someone _does_ it, then there will be no
yelling. Both you _and_ your sibling can work together, and that's okay. The
only way your mother will yell is if neither of you guys do them.

---

## AND

When performing the AND operator on two objects, A, and B, both A and B must
have the truth value.

### Examples

- `1 AND 0 = 0`
- `1 AND 1 = 1`
- `0 AND 0 = 0`
- `0 AND 1 = 0`

Note that the truth value here is 1.

### Analogy

You and your significant other are planning a date. And it's not a date unless
both parties show up. If you show up, and the other doesn't, then it's not a
date. The only way it's considered a date if both people show up.

A flashlight works when two batteries are both entered the correct way. If one
or more of them are entered the wrong way, the flashlight won't turn on.

---

## XOR (eXclusive OR)

When performing the XOR operator on two objects, A, and B, only A or B must have
the truth value, but not both.

### Examples

- `1 XOR 0 = 1`
- `1 XOR 1 = 0`
- `0 XOR 0 = 0`
- `0 XOR 1 = 1`

### Analogy

If your waiter asks you if you want Hash Browns or French Fries as your side,
you can't say both! You must choose one or the other!

Two girls (or guys) like you. Too bad, you gotta choose one. **Exclusive** OR,
buddy.

---

It's written by the famous Computer Scientist,
[Donald Knuth](https://en.wikipedia.org/wiki/Donald_Knuth) !
