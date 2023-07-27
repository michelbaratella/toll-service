# toll-service

We want to emulate a toll station, so basically, we have car flow, just like we would have in a real road, and a set of toll receptors which will consume from a car’s line. 
The toll emulation must have at least these features:
o	A constant to define how many toll’s receptors are available.
o	A constant to define for long the emulation will run.
o	The toll’s receptors service time varies between 10 and 25 seconds (the time each attendant take to charge and let the car go.
At end of the emulation time a report like following example must be printed on the screen:

Toll Emulation Results
Duration:1 min
Charged cars:7
Highest line width:5
Current line width:4


Tips:
In a road toll the toll’s receptors work in parallel, compare the impact of the number of the toll receptors available over the emulation results, mainly the width of the current line at the end of the emulation.
