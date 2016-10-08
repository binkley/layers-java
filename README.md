# Layers

## `Layers`

High-level container of layers with single view of entries (key-value pairs,
computed after any new value is added for the key).

D&amp;D 5e is an example problem domain.  The rules are sufficiently complex to require several interesting techniques.

## `Layer`

Scratch key-value map, added to the owning layers with `accept()`, or
discarded with `reject()`.

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
