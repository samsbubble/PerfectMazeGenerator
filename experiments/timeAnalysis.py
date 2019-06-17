import numpy as np
import matplotlib.pyplot as plt

# Script creating a plot showing the time measurements compared to the theoretical time complexity curves.

space = np.array([10,   15,  20,  25,  30,  35,  40,  50,  60,  70,  80,  90, 100, 120, 140, 160, 180, 200, 220, 250, 280, 320, 360, 400, 450])
space = space**2

with open("../tests/time/Recursive Backtracking Algorithm Time Test.txt", 'r') as f:
    timeRB = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/time/Prim's Algorithm Time Test.txt", 'r') as f:
    timePrim = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/time/Wilson's Algorithm Time Test.txt", 'r') as f:
    timeWilson = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/time/Random Choice Time Test.txt", 'r') as f:
    timeRandom = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/time/Bottom Up Time Test.txt", 'r') as f:
    timeBottomUp = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/time/RB Bottom Up Time Test.txt", 'r') as f:
    timeRBBottomUp = [[float(line.rstrip('\n'))] for line in f]

fig, ax = plt.subplots()
fig.set_size_inches(9, 8)

rb = plt.loglog(space, timeRB, 'r-*', label='Recursive Backtracking')
prim = plt.loglog(space, timePrim, 'b-*', label='Prim')
wilson = plt.loglog(space, timeWilson, 'g-*', label='Wilson')
random = plt.loglog(space, timeRandom, 'c-*', label='Random Choice')
bottomUp = plt.loglog(space, timeBottomUp, 'm-*', label='Bottom Up')
rbBottomUp = plt.loglog(space, timeRBBottomUp, 'y-*', label='RB Bottom Up')
teoreticaln = plt.loglog(space, 10**(-6.4)*space, 'k-', label='Teoretical O(n)',linewidth=3.0)
teoreticaln2 = plt.loglog(space, 10**(-8.3)*(space**2), 'k--', label='Teoretical O(n^2)', linewidth=3.0)
teoreticaln3 = plt.loglog(space, 10**(-10.3)*(space**3), 'k:', label='Teoretical O(n^3)', linewidth=3.0)

plt.ylabel('Time pr generation (average)')
plt.xlabel('Size of maze (number of cells)')
plt.title('Time pr generation for different sizes of mazes')
plt.legend(loc='upper left')
plt.show()
fig.savefig('images/runTimeOverSpace.png')
