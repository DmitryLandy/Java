# UMGC-Projects
---
## CONTENTS

### DatabaseOfProperties.java

This program creates a database of property listings (Properties.txt) that include 
the Parcel ID, property address, number of bedrooms, square footage, property price,
and property status. This database is output in the IDE output window. A GUI is presented 
to the user, who can find, insert, and delete properties in the database. 
Additionally, property status can also be changed. After the program is exited,
the transactions from the program as well as the final modified database are
output in the IDE output window.

### HousePieces.java

This file imports java 2D graphic classes in order to create various
shapes of different colors and combine them together to create a representation
of a house. This class will be used in the "House.java" constructor to add it
to the JFrame.

### Multithreading.java

This program will create 3 separate threads and time them to run
in consecutive order each iteration (1, 2, then 3).

### DemandPaging.java

This program perform various page-replacement algorithms. The user 
is promted with a menu when starting the program that allows them to do the following:
```
0 - Exit the program
1 – Read Reference String
2 – Generate Reference String
3 – Display Current Reference String
4 – Simulate FIFO (first in first out)
5 – Simulate OPT (optimal)
6 – Simulate LRU (least recently used)
7 – Simulate LFU (least frequently used)
```
This program requires an argument from the command line for setting the amount of 
physical frames. After getting the to menu, the user will need to create a reference
string manually or automatically, otherwise the simulations will give an error. 

After the reference string is created, the user can simulate the various page-replacement
algorithms. 
