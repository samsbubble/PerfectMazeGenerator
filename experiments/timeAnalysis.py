import matplotlib.pyplot as plt
import numpy as np

space = np.array([10,   15,  20,  25,  30,  35,  40,  50,  60,  70,  80,  90, 100, 120, 140, 160, 180, 200, 220, 250, 280, 320, 360, 400, 450])
space = space**2

with open("../tests/Recursive Backtracking Algorithm Time Test.txt", 'r') as f:
    timeRB = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/Prim's Algorithm Time Test.txt", 'r') as f:
    timePrim = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/Wilson's Algorithm Time Test.txt", 'r') as f:
    timeWilson = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/Random Choice Time Test.txt", 'r') as f:
    timeRandom = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/Bottom Up Time Test.txt", 'r') as f:
    timeBottomUp = [[float(line.rstrip('\n'))] for line in f]

with open("../tests/RB Bottom Up Time Test.txt", 'r') as f:
    timeRBBottomUp = [[float(line.rstrip('\n'))] for line in f]

fig = plt.figure()
fig.set_size_inches(10, 7.5)

rb = plt.loglog(space, timeRB, 'r-*', label='Recursive Backtracking')
prim = plt.loglog(space, timePrim, 'b-*', label='Prim')
wilson = plt.loglog(space, timeWilson, 'g-*', label='Wilson')
random = plt.loglog(space, timeRandom, 'c-*', label='Random Choice')
bottomUp = plt.loglog(space, timeBottomUp, 'm-*', label='Bottom Up')
rbBottomUp = plt.loglog(space, timeRBBottomUp, 'y-*', label='RB Bottom Up')
teoretical = plt.loglog(space, 10**(-6)*space, 'k-', label='Teoretical (RB, P, )',linewidth=3.0)
teoryWilson = plt.loglog(space, 10**(-6)*(space**2), 'k--', label='Teoretical Wilson', linewidth=3.0)


plt.ylabel('Time pr run (average)')
plt.xlabel('Size of maze')
plt.title('Time pr run for different sizes of mazes')
plt.legend(loc='upper left')
plt.show()
fig.savefig('images/runTimeOverSpace.png')
