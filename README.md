# GoatVerse

[![Build](https://github.com/wanderley/goatverse/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/wanderley/goatverse/actions/workflows/ci.yml)

This is a (toy) state management for writing multiplayer games in Clojure/ClojureScript.  It is inspired on [Flux](https://facebook.github.io/flux/), [re-frame](https://github.com/day8/re-frame) and other nice stuff.

Examples:
- [μSnake](https://github.com/wanderley/musnake), a multiplayer snake game.

## Setup and Development

To get an interactive development environment run:

    lein figwheel dev cards

and open two pages on your browser: [index.html](http://localhost:3449/index.html) and [cards.html](http://localhost:3449/cards).  The first one is the example page and the other is a reference card for each example.  Lein will auto compile and send all changes to the browser without the need to reload.  After the compilation process is complete, you will get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not get live reloading, nor a REPL.

## Development and Run

To get a standalone verison of the application run:

    lein uberjar

To execute the webserver run:

    java -jar target/goatverse-standalone.jar 8080
