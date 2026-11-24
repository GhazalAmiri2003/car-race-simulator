# Event-Driven Car Race Simulator

Java source-code prototype exploring discrete-event simulation, object-oriented modelling and racing strategies.

## Highlights
- Priority queue for chronologically ordered race events.
- Car, driver, track and environmental models.
- Acceleration, curves, crashes, pit stops and finish events.
- Fuel management and configurable driver/pit-stop strategies.
- Race setup and player budget concepts.

## Design
`RaceSimulator` coordinates simulation state and dispatches events from `EventQueue`. Vehicle and driver classes model race participants, while individual event classes represent transitions in the simulation.

## Repository status
This repository contains an incomplete source snapshot. Java package declarations do not match the flat repository layout and referenced classes (such as configuration and budget management classes) are not all included. A complete source tree is needed before a reliable build or runnable example can be provided.

Technology: Java, object-oriented programming, priority queues, discrete-event simulation.
