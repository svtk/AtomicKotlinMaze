# AtomicKotlinMaze

This project contains the game described in
the 'Object-Oriented Programming' section of the ['AtomicKotlin'](http://atomickotlin.com) book.
The concepts of OOP are explained using examples from this game.

The best way to get familiar with the game is to play it. 
There are 9 different mazes available (0.txt - 8.txt) in the `mazes` folder.
To play a different maze, change the path to its representation in `main.kt`.

You move your character, a robot (R), using your keyboard's arrow keys.
The goal is to eat all the food (.), find the exit (!) and don't get eaten by one of the monsters (M). Other letters denote teleports.
Press whitespace to avoid moving and stay in the same cell, which is helpful when there's a monster around.

To start the game, run the `main` function in the `src/main/kotlin/ui/main.kt` file
or run the following gradle command:

```
./gradlew build
./gradlew game
``` 
