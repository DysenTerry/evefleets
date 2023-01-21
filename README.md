# evefleets
The EveFleets application is a third party mapping tool I wrote for the game EVE Online so that fleet commanders could manage their fleets. Eve Fleets loads the exported game data and displays it graphically on the screen. When you hover over a star it will bring up the types of ores and ice found in that system. It allows you to zoom and drag the map canvas and search for stars by name. You can also add fleet markers and drag and drop them in different locations on the map. It is a multi-player tool that uses APIs written in PHP and uses a MySQL database in the backend. This allows players around the world to see enemy fleets in real time. This application uses a complex data structure called a star node that allow for quick retrieval of that star's information by using the x/y cursor location.

I wrote Eve Fleets in Java several years ago and it needs a rewrite due to changes in the exported data and Java libraries.

![Xcipher](https://github.com/TAllenLucas/evefleets/blob/Master/eve.jpg?raw=true)
