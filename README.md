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
## API Endpoints
This SpringBoot Application has the following endpoints: 
<br>

<!DOCTYPE HTML>
<!-- NewPage -->
<html lang="es">
<head>
<link rel="stylesheet" type="text/css" href="stylesheet.css" title="Style">
</head>
<body class="class-declaration">
<div class="flexBox">
<header role="banner" class="flexHeader">
<!-- ========= END OF TOP NAVBAR ========= -->
<div class="skipNav"><a id="skip.navbar.top">
<!--   -->
</a></div>
</nav>
</header>
<div class="flexContent">
<main role="main">
<!-- ========== METHOD SUMMARY =========== -->
<li class="blockList">
<section class="methodSummary"><a id="method.summary">
<!--   -->
</a>
<h2>Method Summary</h2>
<div class="memberSummary">
<div id="memberSummary_tabpanel" role="tabpanel">
<table aria-labelledby="t0">
<thead>
<tr>
<th class="colFirst" scope="col">Modifier and Type</th>
<th class="colSecond" scope="col">Method</th>
<th class="colLast" scope="col">Description</th>
</tr>
</thead>
<tbody>
<tr class="altColor" id="i0">
<td class="colFirst"><code><a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a></code></td>
<th class="colSecond" scope="row"><code><span class="memberNameLink"><a href="#deployRover(com.avalldeperas.marsroverkata.model.Rover)">deployRover</a></span>&#8203;(<a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a>&nbsp;rover)</code></th>
<td class="colLast">
<div class="block">Deploys a new Rover to Mars.</div>
</td>
</tr>
<tr class="rowColor" id="i1">
<td class="colFirst"><code>java.lang.String</code></td>
<th class="colSecond" scope="row"><code><span class="memberNameLink"><a href="#execute(com.avalldeperas.marsroverkata.model.CommandWrapper,java.lang.Long)">execute</a></span>&#8203;(<a href="../model/CommandWrapper.html" title="class in com.avalldeperas.marsroverkata.model">CommandWrapper</a>&nbsp;wrapper,
java.lang.Long&nbsp;id)</code></th>
<td class="colLast">
<div class="block">Executes commands for an specific Rover.</div>
</td>
</tr>
<tr class="altColor" id="i2">
<td class="colFirst"><code>org.springframework.http.ResponseEntity&lt;<a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a>&gt;</code></td>
<th class="colSecond" scope="row"><code><span class="memberNameLink"><a href="#findRover(java.lang.Long)">findRover</a></span>&#8203;(java.lang.Long&nbsp;id)</code></th>
<td class="colLast">
<div class="block">Receives a request and returns a Rover by a given id.</div>
</td>
</tr>
<tr class="rowColor" id="i3">
<td class="colFirst"><code>org.springframework.http.ResponseEntity&lt;java.util.List&lt;<a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a>&gt;&gt;</code></td>
<th class="colSecond" scope="row"><code><span class="memberNameLink"><a href="#listRovers()">listRovers</a></span>()</code></th>
<td class="colLast">
<div class="block">Receives a request and returns a list of Rovers.</div>
</td>
</tr>
<tr class="altColor" id="i4">
<td class="colFirst"><code>java.lang.String</code></td>
<th class="colSecond" scope="row"><code><span class="memberNameLink"><a href="#turnOffRover(java.lang.Long)">turnOffRover</a></span>&#8203;(java.lang.Long&nbsp;id)</code></th>
<td class="colLast">
<div class="block">Turns the Rover off.</div>
</td>
</tr>
<tr class="rowColor" id="i5">
<td class="colFirst"><code>java.lang.String</code></td>
<th class="colSecond" scope="row"><code><span class="memberNameLink"><a href="#turnOnRover(java.lang.Long)">turnOnRover</a></span>&#8203;(java.lang.Long&nbsp;id)</code></th>
<td class="colLast">
<div class="block">Turns the Rover on.</div>
</td>
</tr>
</tbody>
</table>
</div>
</div>
<div class="inheritedList">
</section>
</li>
</ul>
</section>
<section class="details">
<ul class="blockList">
<li class="blockList">
<section class="methodDetails"><a id="method.detail">
<!--   -->
</a>
<h2>Method Details</h2>
<ul class="blockList">
<li class="blockList">
<section class="detail">
<h3><a id="listRovers()">listRovers</a></h3>
<div class="memberSignature"><span class="annotations">@GetMapping("/list-rovers")
</span><span class="modifiers">public</span>&nbsp;<span class="returnType">org.springframework.http.ResponseEntity&lt;java.util.List&lt;<a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a>&gt;&gt;</span>&nbsp;<span class="memberName">listRovers</span>()</div>
<div class="block">Receives a request and returns a list of Rovers.</div>
<dl>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>the response containing a list of Rovers.</dd>
</dl>
</section>
</li>
<li class="blockList">
<section class="detail">
<h3><a id="findRover(java.lang.Long)">findRover</a></h3>
<div class="memberSignature"><span class="annotations">@GetMapping("/rover/{id}")
</span><span class="modifiers">public</span>&nbsp;<span class="returnType">org.springframework.http.ResponseEntity&lt;<a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a>&gt;</span>&nbsp;<span class="memberName">findRover</span>&#8203;(<span class="arguments">@PathVariable
java.lang.Long&nbsp;id)</span></div>
<div class="block">Receives a request and returns a Rover by a given id.</div>
<dl>
<dt><span class="paramLabel">Parameters:</span></dt>
<dd><code>id</code> - Rover's id.</dd>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>response that may contain the deployed rover.</dd>
</dl>
</section>
</li>
<li class="blockList">
<section class="detail">
<h3><a id="execute(com.avalldeperas.marsroverkata.model.CommandWrapper,java.lang.Long)">execute</a></h3>
<div class="memberSignature"><span class="annotations">@PostMapping("/execute-rover/{id}")
</span><span class="modifiers">public</span>&nbsp;<span class="returnType">java.lang.String</span>&nbsp;<span class="memberName">execute</span>&#8203;(<span class="arguments">@RequestBody
<a href="../model/CommandWrapper.html" title="class in com.avalldeperas.marsroverkata.model">CommandWrapper</a>&nbsp;wrapper,
@PathVariable
java.lang.Long&nbsp;id)</span></div>
<div class="block">Executes commands for an specific Rover.</div>
<dl>
<dt><span class="paramLabel">Parameters:</span></dt>
<dd><code>wrapper</code> - List of commands.</dd>
<dd><code>id</code> - The Rover's id.</dd>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>The log of the command execution.</dd>
</dl>
</section>
</li>
<li class="blockList">
<section class="detail">
<h3><a id="turnOnRover(java.lang.Long)">turnOnRover</a></h3>
<div class="memberSignature"><span class="annotations">@GetMapping("/turn-on-rover/{id}")
</span><span class="modifiers">public</span>&nbsp;<span class="returnType">java.lang.String</span>&nbsp;<span class="memberName">turnOnRover</span>&#8203;(<span class="arguments">@PathVariable
java.lang.Long&nbsp;id)</span></div>
<div class="block">Turns the Rover on.</div>
<dl>
<dt><span class="paramLabel">Parameters:</span></dt>
<dd><code>id</code> - Rover's id that will be turned on.</dd>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>the log of the turning on.</dd>
</dl>
</section>
</li>
<li class="blockList">
<section class="detail">
<h3><a id="turnOffRover(java.lang.Long)">turnOffRover</a></h3>
<div class="memberSignature"><span class="annotations">@GetMapping("/turn-off-rover/{id}")
</span><span class="modifiers">public</span>&nbsp;<span class="returnType">java.lang.String</span>&nbsp;<span class="memberName">turnOffRover</span>&#8203;(<span class="arguments">@PathVariable
java.lang.Long&nbsp;id)</span></div>
<div class="block">Turns the Rover off.</div>
<dl>
<dt><span class="paramLabel">Parameters:</span></dt>
<dd><code>id</code> - Rover's id that will be turned off.</dd>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>the log of the turning off.</dd>
</dl>
</section>
</li>
<li class="blockList">
<section class="detail">
<h3><a id="deployRover(com.avalldeperas.marsroverkata.model.Rover)">deployRover</a></h3>
<div class="memberSignature"><span class="annotations">@PostMapping("/deploy-rover")
</span><span class="modifiers">public</span>&nbsp;<span class="returnType"><a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a></span>&nbsp;<span class="memberName">deployRover</span>&#8203;(<span class="arguments">@RequestBody
<a href="../model/Rover.html" title="class in com.avalldeperas.marsroverkata.model">Rover</a>&nbsp;rover)</span></div>
<div class="block">Deploys a new Rover to Mars.</div>
<dl>
<dt><span class="paramLabel">Parameters:</span></dt>
<dd><code>rover</code> - The Rover to be deployed.</dd>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>The Rover deployed.</dd>
</dl>
</section>
</li>
</ul>
</section>
</li>
</ul>
</section>
</div>
<!-- ========= END OF CLASS DATA ========= -->
</main>
</div>
</div>
</body>
</html>

<br>

### Code Quality Assurance 
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
