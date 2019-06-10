import numpy as np
import matplotlib.pyplot as plt

means_deadEnds = []
means_rivers = []
means_length = []
means_turns = []

# Make a plot for each of the properties
fig = plt.figure()
#plt.suptitle("Properties for the Algorithms")


# Pull data from the 5 (6) files
i = 0
for filename in ('../tests/RecursiveBacktracking.txt', '../tests/Prim.txt', '../tests/Wilson.txt', '../tests/Random.txt', '../tests/BottomUp.txt', '../tests/RBBottomUp.txt'):
    with open(filename, 'r') as f:
        lines = [[int(num) for num in line.split(' ')] for line in f]
        #print(lines)

    means = np.mean(lines, axis=0)
    means_deadEnds.append(means[0])
    means_rivers.append(means[1])
    means_length.append(means[2])
    means_turns.append(means[3])
    i = i+1

# data to plot
n_groups = 6
index = np.arange(n_groups)
bar_width = 0.4
opacity = 0.8

# Dead ends
subplot1 = plt.subplot(221)
plot1 = plt.bar(index, means_deadEnds, bar_width, alpha=opacity, color=['magenta', 'purple', 'blue', 'cyan', 'lime', 'red'], edgecolor='black', label='Dead Ends')
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('Dead Ends')
plt.xticks(index, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))


# Rivers
plt.subplot(222)
plot2 = plt.bar(index, means_rivers, bar_width, alpha=opacity,color=['magenta', 'purple', 'blue', 'cyan', 'lime', 'red'], edgecolor='black', label='River Factor')
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('River Factor')
plt.xticks(index, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

# Length
plt.subplot(223)
plot3 = plt.bar(index, means_length, bar_width, alpha=opacity,color=['magenta', 'purple', 'blue', 'cyan', 'lime', 'red'],edgecolor='black', label='Length of the Solution')
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('Length of the Solution')
plt.xticks(index, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

# Turns
plt.subplot(224)
plot4 = plt.bar(index, means_turns, bar_width, alpha=opacity,color=['magenta', 'purple', 'blue', 'cyan', 'lime', 'red'],edgecolor='black',label='Turns on the Solution')
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('Turns on the Solution')
plt.xticks(index, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

plt.show()
fig.savefig('images/BachExpThirdIteration.png')