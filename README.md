# Ski Resort Simulation

A Java application that simulates a full day at a ski resort. The resort is modeled as a directed graph of ski runs and lifts, while athletes arrive, choose routes, queue for lifts, and complete rides in a time-ordered simulation.

This project was developed as an object-oriented programming assignment and extended with route planning, simulation statistics, and generated visual reports.

## Highlights

- **Discrete-event simulation:** processes athlete arrivals, lift pickups, queueing, and ride completions in chronological order.
- **Graph-based route planning:** uses breadth-first search (BFS) to find a shortest route to an athlete's selected ski run.
- **Multiple athlete behaviors:** supports different route selection strategies, including greedy, collector, and planner athletes, alongside randomized decisions.
- **Reproducible runs:** a seed controls random choices so the same input and seed can reproduce a simulation.
- **Statistics and history:** observers record athlete ride histories, and resort edges report usage statistics.
- **LaTeX reports:** generates plots for the resort map, edge parameters, edge statistics, and individual athlete histories.
- **Input-driven setup:** reads resort and athlete data from text files.

## Technologies

- Java 21
- Maven
- JUnit 5 for tests
- LaTeX output for generated visualizations

## Run

Build the application with JDK 21 or newer and Maven:

```bash
mvn package
```

Run the JUnit test suite with:

```bash
mvn test
```

Run with an integer seed and an existing directory for generated plot files. The program reads resort and athlete data from standard input, so you can use one of the sample files:

```bash
java -cp target/classes Main 42 output < input/przykladowe-dane-1.txt
```

The program expects two arguments: an integer seed and a directory path. The LaTeX files are written to the supplied directory; compile them with a LaTeX toolchain to render the plots.

## Design

The code is organized around the resort graph, athlete behavior, and simulation events. Factory classes construct domain objects from input data; athlete observers collect histories and logs; specialized athlete classes implement different decision policies. The simulation advances by repeatedly taking the earliest event from a custom priority queue and scheduling the events it produces.

## Project notes

- `src/vislib` contains **vislib, a visualization library supplied with the original assignment**. It is used to generate the LaTeX map and diagram output and is not presented as original project work.
- **This README was generated with AI** from the repository contents.

