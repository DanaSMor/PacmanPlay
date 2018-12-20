PACMAN
=========
This project represent a game of pacmans in Ariel University area.

Written by: Or Avital And Dana Morhaim


General info
--------
This projace is based on GPS project, wich can be found at 
<a href="https://github.com/oravital7/Ex2">https://github.com/oravital7/Ex2</a>.


Routes Algorithm - How does it work
--------
The algorithm for calculating the roads for each Pacman takes in considiration 
the time that each pacman already gone, and the time it will take him to get to the next fruit.

In every iteration the algorithm choose the pacman with the higher priority - wich based on the factors above.
With that approach, every pacman is given a fair shot to eat Fruits.

How to play
--------
The player can either import a CSV file or insert by hand pacmans and fruits, and then press the play button

<img src="./Icon/gameBeforePlay.PNG" width="350" height="300">

The program calculates the fastest routes for each pacman, draws a line of the path, and write the total score of the game

<img src="./Icon/gameWithScore.PNG" width="350" height="300">
<img src="./Icon/gameWithoutScore.PNG" width="350" height="300">


Features
--------
This game includes the following awesome features:

- CSV import - you can save your game as a CSV file
<img src="./Icon/gamCsv.PNG" width="350" height="300">

- KML import - you can save your game as a KML file, and watch the routes of pacmans in real life
<img src="./Icon/gameInKml.PNG" width="350" height="300">

Class Diagram
--------
<img src="./Icon/ClassDiagram.jpg" width="650" height="600">

&nbsp;
&nbsp;

General info
--------------
Read more in Wiki:
- Geographic coordinate system: https://en.wikipedia.org/wiki/Geographic_coordinate_system
- CSV files: https://en.wikipedia.org/wiki/Comma-separated_values
- KML files: https://en.wikipedia.org/wiki/Keyhole_Markup_Language


