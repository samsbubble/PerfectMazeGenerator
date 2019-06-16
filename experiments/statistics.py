import numpy as np
import pylab as plt

with open("../tests/property/Recursive Backtracking Algorithm.txt", 'r') as f:
    linesRB = [[int(num) for num in line.split(' ')] for line in f]

max_values_RB = np.max(linesRB, axis=0)
min_values_RB = np.min(linesRB, axis=0)
avg_values_RB = np.mean(linesRB, axis=0)
RB = np.r_[[min_values_RB], [avg_values_RB], [max_values_RB]]
print("RB = " + repr(RB))

print("RB% : Dead ends = " + repr((avg_values_RB[0]/2500)*100) +
           " River = " + repr((avg_values_RB[1]/2500)*100) +
           " Length = " + repr((avg_values_RB[2]/2500)*100) +
           " Turns = " + repr((avg_values_RB[3]/avg_values_RB[2])*100))


with open("../tests/property/Prim's Algorithm.txt", 'r') as f:
    linesPrim = [[int(num) for num in line.split(' ')] for line in f]

max_values_Prim = np.max(linesPrim, axis=0)
min_values_Prim = np.min(linesPrim, axis=0)
avg_values_Prim = np.mean(linesPrim, axis=0)
Prim = np.r_[[min_values_Prim], [avg_values_Prim], [max_values_Prim]]
print("Prim = " + repr(Prim))

print("Prim% : Dead ends = " + repr((avg_values_Prim[0]/2500)*100) +
           " River = " + repr((avg_values_Prim[1]/2500)*100) +
           " Length = " + repr((avg_values_Prim[2]/2500)*100) +
           " Turns = " + repr((avg_values_Prim[3]/avg_values_Prim[2])*100))


with open("../tests/property/Wilson's Algorithm.txt", 'r') as f:
    linesWilson = [[int(num) for num in line.split(' ')] for line in f]

max_values_Wilson = np.max(linesWilson, axis=0)
min_values_Wilson = np.min(linesWilson, axis=0)
avg_values_Wilson = np.mean(linesWilson, axis=0)
Wilson = np.r_[[min_values_Wilson], [avg_values_Wilson], [max_values_Wilson]]
print("Wilson = " + repr(Wilson))

print("Wilson% : Dead ends = " + repr((avg_values_Wilson[0]/2500)*100) +
           " River = " + repr((avg_values_Wilson[1]/2500)*100) +
           " Length = " + repr((avg_values_Wilson[2]/2500)*100) +
           " Turns = " + repr((avg_values_Wilson[3]/avg_values_Wilson[2])*100))


with open("../tests/property/Random Choice.txt", 'r') as f:
    linesRandom = [[int(num) for num in line.split(' ')] for line in f]

max_values_Random = np.max(linesRandom, axis=0)
min_values_Random = np.min(linesRandom, axis=0)
avg_values_Random = np.mean(linesRandom, axis=0)
Random = np.r_[[min_values_Random], [avg_values_Random], [max_values_Random]]
print("Random = " + repr(Random))

print("Random% : Dead ends = " + repr((avg_values_Random[0]/2500)*100) +
           " River = " + repr((avg_values_Random[1]/2500)*100) +
           " Length = " + repr((avg_values_Random[2]/2500)*100) +
           " Turns = " + repr((avg_values_Random[3]/avg_values_Random[2])*100))


with open("../tests/property/Bottom Up.txt", 'r') as f:
    linesBUP = [[int(num) for num in line.split(' ')] for line in f]

max_values_BUP = np.max(linesBUP, axis=0)
min_values_BUP = np.min(linesBUP, axis=0)
avg_values_BUP = np.mean(linesBUP, axis=0)
BUP = np.r_[[min_values_BUP], [avg_values_BUP], [max_values_BUP]]
print("Bottom Up = " + repr(BUP))

print("Bottom Up% : Dead ends = " + repr((avg_values_BUP[0]/2500)*100) +
           " River = " + repr((avg_values_BUP[1]/2500)*100) +
           " Length = " + repr((avg_values_BUP[2]/2500)*100) +
           " Turns = " + repr((avg_values_BUP[3]/avg_values_BUP[2])*100))



with open("../tests/property/RB Bottom Up.txt", 'r') as f:
    linesRBBUP = [[int(num) for num in line.split(' ')] for line in f]

max_values_RBBUP = np.max(linesRBBUP, axis=0)
min_values_RBBUP = np.min(linesRBBUP, axis=0)
avg_values_RBBUP = np.mean(linesRBBUP, axis=0)
RBBUP = np.r_[[min_values_RBBUP], [avg_values_RBBUP], [max_values_RBBUP]]
print("RBBUP = " + repr(RBBUP))

dead_ends = [RB[:, 0], Prim[:, 0], Wilson[:, 0], Random[:, 0], BUP[:, 0], RBBUP[:, 0]]
rivers = [RB[:, 1], Prim[:, 1], Wilson[:, 1], Random[:, 1], BUP[:, 1], RBBUP[:, 1]]
length = [RB[:, 2], Prim[:, 2], Wilson[:, 2], Random[:, 2], BUP[:, 2], RBBUP[:, 2]]
turns = [RB[:, 3], Prim[:, 3], Wilson[:, 3], Random[:, 3], BUP[:, 3], RBBUP[:, 3]]

print("RB Bottom Up% : Dead ends = " + repr((avg_values_RBBUP[0]/2500)*100) +
           " River = " + repr((avg_values_RBBUP[1]/2500)*100) +
           " Length = " + repr((avg_values_RBBUP[2]/2500)*100) +
           " Turns = " + repr((avg_values_RBBUP[3]/avg_values_RBBUP[2])*100))

fig = plt.figure()
fig.set_size_inches(10, 7)

n_groups = 6
index = np.arange(n_groups)
bar_width = 0.4
opacity = 0.8




# Dead ends
subplot1 = plt.subplot(221)
plot1 = plt.boxplot(dead_ends, vert=0)
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('Dead Ends')
plt.yticks(index+1, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

# ('RB\n' + repr(means_deadEnds[0]), 'P\n' + repr(means_deadEnds[1]), 'W\n' + repr(means_deadEnds[2]), 'RC\n' + repr(means_deadEnds[3]), 'BU\n' + repr(means_deadEnds[4])))

# Rivers
plt.subplot(222)
plot2 = plt.boxplot(rivers, vert=0)
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('River Factor')
plt.yticks(index+1, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

# Length
plt.subplot(223)
plot3 = plt.boxplot(length, vert=0)
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('Length of the Solution')
plt.yticks(index+1, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

# Turns
plt.subplot(224)
plot4 = plt.boxplot(turns, vert=0)
plt.xlabel('Algorithm')
plt.ylabel('Cell')
plt.title('Turns on the Solution')
plt.yticks(index+1, ('RB', 'P', 'W', 'RC', 'BU', 'RBBU'))

plt.show()
fig.savefig('images/statistics.png')