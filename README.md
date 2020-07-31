# The Mars Rover Kata
You are part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet. Develop an API that translates the commands sent from earth to instructions that are understood by the rover.

### Requirements
* You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
* The rover receives a character array of commands.
* Implement commands that move the rover forward/backward (f,b).
* Implement commands that turn the rover left/right (l,r).
* Implement wrapping from one edge of the grid to another. (planets are spheres after all)
* Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point, aborts the sequence and reports the obstacle.
* Be careful about edge cases and exceptions. We cannot afford to lose a mars rover, just because the developers overlooked a null pointer.

### Rules
* Feel free to use any libraries, maven or gradle, GitHub or bitbucket, etc.
* Try using the patterns or architecture that you think best fit the problem, however, feel free to use this kata to learn to use a pattern you never used before even if it does not fit, just tell us.
* No REST API or Spring, or database is required, but feel free to use one if you want.
* This kata is a good exercise to practice TDD, so we encourage you to do it this way!
<br><br>


----
## Rover Mars Rest API
This SpringBoot Application has the following [endpoints.](API.md) 


### Quality Assurance 
* Added javadoc for each class created.
* Passes CheckStyle code inspection with 0 errors.
* Has test coverage of 100% of methods/lines.


### Future features
* When creating a new Rover, check if there is any obstacle or other rover in 
the same position.
* Make the Rover print a map.
* Generate random map.
* Create an interface to interact with the Rover.
* Add security to all requests.
