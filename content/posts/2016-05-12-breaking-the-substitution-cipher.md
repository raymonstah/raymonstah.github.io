---
layout: post
title: Breaking the Substitution Cipher
date: "2016-05-12 00:13:31"
tags:
  - python
  - cryptography
---

##### The Substitution Cipher

> "Your cryptosystem should remain secure even if everything is known about it
> except the
> key" -[Kerckhoffs](https://en.wikipedia.org/wiki/Kerckhoffs's_principle)

Please note that this cipher should not be used in real applications as it can
easily be hacked, as you'll see shortly.

Assume we're working with the lowercase English alphabet, and using `Python 3`.

###### The algorithm

This version of the substitution cipher is simple. It's even called the [*simple
substitution cipher*](<https://en.wikipedia.org/wiki/Substitution_cipher>#
Simple_substitution).

1. Choose a `key` to be a random ordering of the alphabet. Note that there would
   be `26!` keys! That's a lot of keys if you're trying to brute force the
   cipher.
2. Now map the alphabet to the key using a `dict`.
3. Apply the mapping to each character of the `message`.
4. Return the `ciphertext`.
5. To decrypt: apply the reverse mapping to each character of the `ciphertext`
   to receive the original `message`.

###### To encrypt

```python
import string

def encrypt(key, message):
 alphabet = list(string.ascii_lowercase)
 key_mapping = dict(zip(alphabet, key))
 return ''.join([key_mapping.get(letter.lower(), letter) for letter in message])
```

###### To decrypt

The decryption method is literally the same as the encrypt method, but the
`key_mapping` is reversed. So we map the key to the alphabet rather than the
alphabet to the keys.

Putting it all together, we get this:

```python
import string

def encrypt(key, message):
 alphabet = list(string.ascii_lowercase)
 key_mapping = dict(zip(alphabet, key))
 return ''.join([key_mapping.get(letter.lower(), letter) for letter in message])

def decrypt(key, message):
 alphabet = list(string.ascii_lowercase)
 key_mapping = dict(zip(key, alphabet))
 return ''.join([key_mapping.get(letter.lower(), letter) for letter in message])
```

###### An example

```python
key = 'oaxsgfhkwuecvdrltjzpqibnym'
plaintext = "Hello this is a sample message."
ciphertext = encrypt(key, plaintext)
# Ciphertext: kgccr pkwz wz o zovlcg vgzzohg.

original = decrypt(key, ciphertext)
# Original: hello this is a sample message.
```

You should acknowledge the fact that the decryption of the encryption should
always give you back the original message.

###### Hacking the Substitution Cipher

Now this is where it gets interesting. We'll be using an algorithm described by
a Stanford professor,
[Persi Diaconis](http://statweb.stanford.edu/~cgates/PERSI/papers/MCMCRev.pdf),
to break the cipher.

The algorithm works like this:

1. Read a book.
2. Count the two-letter frequencies and make a table out of it. For example, how
   many words have an `'a'` followed by an `'a'`.
3. In the end, we will have a table of each bigram along with its occurrence in
   the book, which we'll assume is a good representation of the type of words
   our message will use.
4. This will give us a scoring function that we can pass messages to, to see how
   well they do. The higher the score, the more likely that it's the message
   that we're trying to decrypt.
5. Start with a random key, and get the score for that key. Swap two elements of
   that key, and get the score for the new key. If it results in a higher score,
   then that new key will be kept. If it's a lower score, we still might keep
   the new key on a biased probability. This is to make sure we don't get stuck
   in a local maximum. This is also known as the
   [hill-climbing technique](https://en.wikipedia.org/wiki/Hill_climbing).
6. Repeat step 5 until we are happy with our results.

For the book that we'll be "reading", we'll use _War and Peace_, which you can
find available online for free.

Let's decipher this Chance verse:

```python
qkne tknz crjn arx zrb, l qloo yklnow zrbx eijn
l qloo alnow tknlx pbnytlrey, l qloo anno zrbx uile
er ren cie sbwmn
tknz wre't, tknz wre't derq
tknz wre't derq
arrt re tkn wnvlo'y encd 'tlo lt wxlatnw uiemini
l'j jrvlem ioo jz aijloz axrj ckitkij tr gijhli
txnit tkn wnjrey sbyt oldn uij
l jnie l abcd qltk zrbx axlnewy, hbt wije, mlei
l hnne tkly qiz ylecn ixtkbx qiy ietnitnx
erq tknz qieei klt jn qltk tkn qrr qiu, tkn hij
txzei yeiu ukrtry ra aijloli
jz wibmktnx orrd sbyt oldn yli, zrb cie't ynn knx
zrb cie anno tkn ozxlcy, tkn yulxlt crjlem le hxiloon
tbhjie ra tkn bewnxmxrbew, crjn iew aroorq tkn txilo
l jiwn ybewiz ciewz, l'j envnx mrlem tr knoo
l jnt diezn qnyt, l'j envnx mrlem tr ailo
kn yilw ont'y wr i mrrw iyy srh qltk ckiecn tkxnn
l knix zrb mrtti ynoo lt tr yeitck tkn mxijjz
ont'y jidn lt yr axnn iew tkn hixy yr kixw
tkit tknxn ile't ren mryk wixe uixt zrb cie't tqnnt
tkly ly jz uixt, erhrwz noyn yunid
tkly ly jz uixt, erhrwz noyn yunid
tkly oltton olmkt ra jlen
morxz hn tr mrw, znik
l'jji jidn ybxn tkit tknz mr qknxn tknz cie't mr
la tknz wre't qieei xlwn l'jji ytloo mlvn tknj xilecrity
derq qkit mrw yilw qkne kn jiwn tkn alxyt xilehrq
sbyt tkxrq tkly it tkn new la l'j trr oitn arx tkn letxr
bmk, l'j sbyt kivlem abe qltk lt
zrb derq tkit i elmmi qiy oryt
l oibmk le jz kniw
cibyn l hnt tkit jz nf orrdlem hicd oldn i ulooix ra yiot
bmk, cibyn tknz'oo aolu tkn ycxlut re zrbx iyy oldn qnyonz iew yuldn
zrb cieert jnyy qltk tkn olmkt
orrd it olo ckier axrj 79tk

```

Here's the full implementation in Python:

```python
from math import log, exp
from random import shuffle, random, randint
from string import ascii_lowercase as LETTERS

def get_score_function(corpus):

    """
    Compute the bigrams of the given corpus text, and returns a scoring function
    Pass a string into the scoring function to get back a "score"
    """

    def bigrams(text):
        # Gets a list of consecutive two letter pairs
        for first, second in zip(text, text[1:]):
            yield first+second

    total_bigrams = 0

    counts = {}
    # Count of occurrences of each bigram in the corpus.
    for pair in bigrams(corpus):
        counts[pair] = counts.get(pair, 0) + 1
        total_bigrams += 1

    # Scale it for easy math.
    for bigram, count in counts.items():
        counts[bigram] = log(count / total_bigrams)


    # For the bigrams that don't appear in the plaintext
    # This gives a negative weight to the total score.
    bigram_penalty = log(1 / total_bigrams)

    def score_function(plaintext):
        return sum(counts.get(ngram, bigram_penalty)
            for ngram in bigrams(cleanup(' '.join(plaintext.split()))))
    return score_function


def encrypt(key, message):
    key_mapping = dict(zip(LETTERS, key))
    return ''.join([key_mapping.get(letter.lower(), letter) for letter in message])

def decrypt(message, key):
    key_mapping = dict(zip(key, LETTERS))
    return ''.join([key_mapping.get(letter.lower(), letter) for letter in message])

def find_key(ciphertext, score_function):

    """
    Attempts to find a key based on Diaconnis method.
    Return a 4 tuple of:
        - The best score that we got (most likely to be the original message)
        - The key that gives us the original message
        - The original message itself (or what we think it is)
        - The number of iterations it took
    """

    # A random ordering of the letters as a starting point.
    initial = list(LETTERS)
    shuffle(initial)

    best_key, best_score = initial, score_function(decrypt(ciphertext, initial))

    # Generates a random swap of our key
    def get_new_key(k):
        key = list(k)
        x, y = randint(0, len(k) -1), randint(0, len(k) -1)
        key[x], key[y] = key[y], key[x]
        return key


    for iterations in range(10000):

        key = get_new_key(best_key)

        score = score_function(decrypt(ciphertext, key))
        # Found a better candidate, OR biased coin flip comes out as "head"
        if score > best_score or random() <= exp(score - best_score):
            best_score, best_key = score, key

    # Convert back into a string
    best_key = ''.join(best_key)

    return best_score, best_key, decrypt(ciphertext, best_key), iterations


# Remove non letters and non spaces (i.e punctuation from a string).
def cleanup(str):
    return ''.join([char if char.isalpha() or char == ' ' else '' for char in str])

corpus = ''
with open('war-and-peace.txt') as f:
    corpus = cleanup(' '.join(f.read().split()))

score = get_score_function(corpus)

ciphertext = "\nqkne tknz crjn arx zrb, l qloo yklnow zrbx eijn\nl qloo alnow tknlx pbnytlrey, l qloo anno zrbx uile\ner ren cie sbwmn\ntknz wre't, tknz wre't derq\ntknz wre't derq\narrt re tkn wnvlo'y encd 'tlo lt wxlatnw uiemini\nl'j jrvlem ioo jz aijloz axrj ckitkij tr gijhli\ntxnit tkn wnjrey sbyt oldn uij\nl jnie l abcd qltk zrbx axlnewy, hbt wije, mlei\nl hnne tkly qiz ylecn ixtkbx qiy ietnitnx\nerq tknz qieei klt jn qltk tkn qrr qiu, tkn hij\ntxzei yeiu ukrtry ra aijloli\njz wibmktnx orrd sbyt oldn yli, zrb cie't ynn knx\nzrb cie anno tkn ozxlcy, tkn yulxlt crjlem le hxiloon\ntbhjie ra tkn bewnxmxrbew, crjn iew aroorq tkn txilo\nl jiwn ybewiz ciewz, l'j envnx mrlem tr knoo\nl jnt diezn qnyt, l'j envnx mrlem tr ailo\nkn yilw ont'y wr i mrrw iyy srh qltk ckiecn tkxnn\nl knix zrb mrtti ynoo lt tr yeitck tkn mxijjz\nont'y jidn lt yr axnn iew tkn hixy yr kixw\ntkit tknxn ile't ren mryk wixe uixt zrb cie't tqnnt\ntkly ly jz uixt, erhrwz noyn yunid\ntkly ly jz uixt, erhrwz noyn yunid\ntkly oltton olmkt ra jlen\nmorxz hn tr mrw, znik\nl'jji jidn ybxn tkit tknz mr qknxn tknz cie't mr\nla tknz wre't qieei xlwn l'jji ytloo mlvn tknj xilecrity\nderq qkit mrw yilw qkne kn jiwn tkn alxyt xilehrq\nsbyt tkxrq tkly it tkn new la l'j trr oitn arx tkn letxr\nbmk, l'j sbyt kivlem abe qltk lt\nzrb derq tkit i elmmi qiy oryt\nl oibmk le jz kniw\ncibyn l hnt tkit jz nf orrdlem hicd oldn i ulooix ra yiot\nbmk, cibyn tknz'oo aolu tkn ycxlut re zrbx iyy oldn qnyonz iew yuldn\nzrb cieert jnyy qltk tkn olmkt\norrd it olo ckier axrj 79tk\n"


# Print out our results.
print(find_key(ciphertext, score))

```

Notes:

- The longer the cipher-text, the easier it is to break.
- Our cipher only encrypts alphabet characters. Everything else is left alone.

---

If you're interested in learning more about ciphers and how to break them, check
out this book:
[Hacking Secret Ciphers With Python](http://www.amazon.com/gp/product/1482614375/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1482614375&linkCode=as2&tag=raymondtaught-20&linkId=E5OAOVGJGVFGKTP4)
