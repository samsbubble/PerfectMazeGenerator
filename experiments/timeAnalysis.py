import matplotlib.pyplot as plt

space = [10, 20, 30, 40, 50, 60, 70, 80, 100, 120, 140, 160, 180, 200, 220, 260, 290, 320]
#space = [10, 20, 40, 80, 160, 320]

with open('../tests/RBTimeTest.txt', 'r') as f:
    timeRB = [[float(line.rstrip('\n'))] for line in f]

with open('../tests/PrimTimeTest.txt', 'r') as f:
    timePrim = [[float(line.rstrip('\n'))] for line in f]

with open('../tests/WilsonTimeTest.txt', 'r') as f:
    timeWilson = [[float(line.rstrip('\n'))] for line in f]

with open('../tests/RandomChoiceTimeTest.txt', 'r') as f:
    timeRandom = [[float(line.rstrip('\n'))] for line in f]

with open('../tests/BottomUpTimeTest.txt', 'r') as f:
    timeBottomUp = [[float(line.rstrip('\n'))] for line in f]

with open('../tests/RBBottomUpTimeTest.txt', 'r') as f:
    timeRBBottomUp = [[float(line.rstrip('\n'))] for line in f]

fig = plt.figure()
fig.set_size_inches(9, 5)

rb = plt.semilogy(space, timeRB, 'r-*', label='Recursive Backtracking')
prim = plt.semilogy(space, timePrim, 'b-*', label='Prim')
wilson = plt.semilogy(space, timeWilson, 'g-*', label='Wilson')
random = plt.semilogy(space, timeRandom, 'c-*', label='Random Choice')
bottomUp = plt.semilogy(space, timeBottomUp, 'm-*', label='Bottom Up')
rbBottomUp = plt.semilogy(space, timeRBBottomUp, 'y-*', label='RB Bottom Up')


plt.ylabel('Time pr run (average)')
plt.xlabel('Size of maze')
plt.title('Time pr run for different sizes of mazes')
plt.legend(loc='lower right')
plt.show()
fig.savefig('images/runTimeOverSpace.png')
