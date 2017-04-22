# Scala Exercises

[![Build Status](https://travis-ci.org/aborg0/scala-exercises.svg?branch=no_solution)](https://travis-ci.org/aborg0/scala-exercises)

This repository contains some exercises to help getting familiar with various contexts in the context of Scala.

## Topics covered

### Tail calls
A way to solve problems with constant stack size with recursion. The goal is to create recursive methods in a way where all recursive calls are just before returning from the methods.

### Trampolining
Another way to use constant stack is by using trampolines and creating objects on the heap with representing the computations. (Using the free monad of Function0.)

## Tasks
Currently the following exercises are present:
1. Implement in a tail recursive way and also using scalaz's Trampoline the longest repeated characters problem (find the first position, the character and the length of the repeat of the longest same character sequence in a CharSequence/Iterator[Char]/IEnumerable/TraversableOnce[Char]). (Has tests and example recursive implementation.)
1. Implement in a tail recursive way and also using scalaz's Trampoline the `tail n` UNIX command for ASCII `java.io.InputStream`s with `\n` line separator. (Has tests and an example recursive implementation.) 