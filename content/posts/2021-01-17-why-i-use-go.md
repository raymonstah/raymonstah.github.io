---
title: "Why I Use Go"
date: 2021-01-17T23:43:27-08:00
draft: false
---

## Why Go

I've been using Go for the past two years now, and I've come to really enjoy it.
As a daily Go developer, there are certain things about Go that makes it such an
enjoyable language, despite all of the things it's missing.

## Simple

> “It is not daily increase but daily decrease, hack away the unessential.
> The closer to the source, the less wastage there is.”
>
> Bruce Lee

Go is a simple language. One of the benefits of a simple language is that it's easy to learn, and it's easy to teach. I've found that experienced developers can pick it up in and start using it in a day.
Go doesn't have a lot of features that your favorite language
may have. It took away the nice-to-have features and stripped the language to the absolute necessary
requirements for a modern day language.

Go code is meant to be read. A lot of other languages may have a ton of syntantic sugar or one-liners that make complex problems seem easy. But in Go, I've found that the number of lines of code in often correlated with the complexity of the problem.

There are no fancy macros and magic annotations. It's just code, which makes it easy to follow and comprehend.
Errors are just values, and are always returned as the last value of a function. Go is so opinionated; they've taken away all choices from the programmer and you're left with a simple language.

Another example of a choiceless decision in Go is the tooling around it. `GoFmt` formats your code. The `go` cli is the tool of choice to run and build your progarms. `go test` is the idiomatic way to test your Go code. Go modules are how you handle dependencies. `pprof` is the tool of choice if you want to analyze your code's performance. All of the decisions are made for the developers already and makes writing idiomatic go trivial.

## Fast

Building Go programs are quick. Quick feedback cycles are important to me when I'm trying to validate an idea.
You hear more and more companies switching their backend language of choice from Ruby and Python to Go. It's a good balance between slow dynamic languages and fast lower level system languages. There's not a lot of bloat around the Go ecosystem. You can create super small Docker images, which makes it faster to transport to and from the cloud.

With Goroutines and channels built into the language, writing concurrent code is easier, coming from Java. And it's not just about writing concurrent code, it's about writing safe concurrent code. Go has a built in race detector, which can help find and mitigate race conditions in your programs.

## Cloud friendly

Go is everywhere these days. It's supported as a first class language for many cloud providers. You can build executables for different platforms with `GOOS` and `GOARCH`, which makes it easy distribute your code to any cloud platform out there.

## Summary

My favorite part of Go isn't its syntax or language features, but the entire ecosystem around the language. The tooling and lack of decisions a developer has to make around it is refreshing. There's usually an idiomatic way of doing things in the Go world. I don't have to pick a testing framework, or a build tool. These trivial problems can cause hours of discussion and decision fatigue in other languages, but not in Go. Its simplicity, quickness, and availability makes it a strong contender in a cloud native environment.

Remember, all programming languages including Go are just tools to help you solve problems. Go faces this by focusing on its simplicity and removing choices so that you can focus on your problem. It's my language of choice to get stuff done. With that said, there are a few other languages that I plan on adding to my toolbelt this year, namely Rust and Haskell.
