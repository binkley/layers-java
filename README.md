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
* When possible, use constructors as simple lambdas
* Curry more complex constructors and functions: aim for uniform syntax
* Convenience functions to capture common use patterns
* Streams as preferred model for collections
* Log-structured history
* Cache of current values

## Drawbacks

* Data structures with cyclic references
* Lack of setters complicates coding style
* Density of code a challenge

## Caching

1. Walk layers from oldest to newest.
2. If a key is already cached, do not recompute.
3. For a given new key, find it's most recent (applicable) rule.
4. For a given new key, find all its values, for example, to sum them.
5. Some rules may be complex, and be interested in the all layers.

## `Layers`

[`Layers`](layers-lib/src/main/java/hm/binkley/layers/Layers.java) is a
high-level container of layers with single view of entries (key-value pairs,
computed after any new value is added for the key).

D&amp;D 5e is an example problem domain.  The rules are sufficiently complex to require several interesting techniques.

## `Layer`

Base key-value map, added to the owning layers with `saveAndNext()`; use `ScratchLayer::new` for a blank map factory.

## Rules

* Key rules can see the entire layers to accommodate complex rules, e.g.,
  ability scores sum values for `STR` key

* All keys must have a rule defined.  If a key has no values and the rule
  requires some, the rule must have a default value.
  
* If a key has more than rule defined, the most recent rule is applied (e.g.,
  "Belt of Giant Strength" sets STR and trumps usual STR calculation)
  
* Adding a new value for a key without adding a new rule leaves the most
  recent rule in place (e.g., gaining STR does not undo effect of "Belt of
  Giant Strength")

* Removing a layer which introduces a rule removes the rule as well.  Previous
  rules (or the default) now apply
