A small project to learn more about game theory and decision taking algorithms.

The goal is to implement generic decision algorithms on various board games.

## Features Plan

1. ~~Basic zero sum games (tic tac toe, connect four), with minimax~~
2. ~~Optimization for minimax: alphabeta~~
3. ~~Improve console interface to select move~~
4. Checkers, Reversi
5. Variation on checkers (flying king, variation of board's dimmensions)
6. Chess (with bounded depth)
7. Random zero sum games solving algorithm: expectiminimax
8. Backgammon
9. Non zero sum games
10. ScalaJS interface

## Run from Built Jar

This project can be run from the Jar uploaded in the [releases](https://github.com/lomination/games/releases) or in the [build workflow artifacts](https://github.com/lomination/games/actions).

To do so, download the jar and run it as a usual jar using:

```shell
java -jar games-assembly-VERSION.jar
```

The Jar contain all the dependency of this project, including the Scala standard library, therefore only a Java runtime environment is required (JRE-21 recommanded).

## Building from Sources on Linux (Tested on Ubuntu 26.04 LTS)

### With SDKMAN

To compile this project yourself, you need sbt and a JDK. Both can be installed with SDKMAN. Note that the installation process with SDKMAN requires `unzip` and `curl`.

First download SDKMAN.

```bash
curl -s "https://get.sdkman.io" | bash
```

Follow the instructions and then install a JDK. You can choose a different version of Java if prefered.

```bash
sdk install java 21.0.2-tem
```

Then install `sbt`.

```bash
sdk install sbt
```

You can then compile and run the project with:

```bash
sbt run
```

### With Coursier

Alternatively, you can install the dependencies with Couriser, the Scala dedicated installation tool.

Simply follow the promts to proceed to the installation.

```bash
curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz | gzip -d > cs && chmod +x cs && ./cs setup --jvm temurin:21
```

After that, you should be able to run the project with:

```bash
sbt run
```

## Contribution

Please feel free to contribute to this project by reporting bugs through the GitHub issues or by creating pull requests.

## Licensing

This project is licensed under the GPL-3.0 license.
