# Layers

_An experiment in style and technique_.

This software is in the Public Domain.  Please see [LICENSE.md](LICENSE.md).

[![License](https://img.shields.io/badge/license-PD-blue.svg)](http://unlicense.org)
[![Build Status](https://circleci.com/gh/binkley/layers-java.svg?style=shield&circle-token=d86febce0a23cfc4f2aca122d5f5d78b9d177e47)](https://circleci.com/gh/binkley/layers-java)
[![Issues](https://img.shields.io/github/issues/binkley/layers-java.svg)](https://github.com/binkley/layers-java/issues)

## Styles

Throughout focus is given to these styles, though practicality or taste 
occasionally intrudes:

* Immutability
* Avoidance of `null`
* Functional, especially Java 8 features
* Limited API surface area

## Techniques

These include:

* Instance factory methods, chaining layer construction
* When possible, use constructors for simple lambdas
* Curry more complex constructors and functions: aim for uniform syntax
* Streams as preferred model for collections
* Log-structured history
* Cache of current values

## Drawbacks

* Data structures with cyclic references
* Lack of setters complicates coding style

## `Layers`

[`Layers`](layers-lib/src/main/java/hm/binkley/layers/Layers.java) is a
high-level container of layers with single view of entries (key-value pairs,
computed after any new value is added for the key).

D&amp;D 5e is an example problem domain.  The rules are sufficiently complex to require several interesting techniques.

## `Layer`

Base key-value map, added to the owning layers with `saveAndNext()`; use `ScratchLayer::new` for a blank map factory.

## Rules

* Key rules can see the entire layers to accommodate complex rules, e.g.,
  ability scores sum values for STR key

* If a key has no rule defined, default rule is to return the most recent
  value for the key (e.g., "Character Name")
  
* If a key has more than rule defined, the most recent rule is applied (e.g.,
  "Belt of Giant Strength" sets STR and trumps usual STR calculation)
  
* Adding a new value for a key without adding a new rule leaves the most
  recent rule in place (e.g., gaining STR does not undo effect of "Belt of
  Giant Strength")

* Removing a layer which introduces a rule removes the rule as well.  Previous
  rules (or the default) now apply
