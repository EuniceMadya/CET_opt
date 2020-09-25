import matplotlib.pyplot as plt

totalNodeNum: int = 0
totalAnchorNum: int = 0
maxTime: float = 0.0


def addAlgo(algo: str, data: list):
    if algo == "DFS" or algo == "BFS":
        addDFSorBFS(algo, data[0])
    else:
        addSeq(data)


def addSeq(data: list):
    global totalAnchorNum
    global totalNodeNum
    global maxTime
    dataSet: dict = {}

    selection: str = ""
    for i in range(len(data)):
        if len(data[i].split(",")) == 1:
            selection = data[i]
            dataSet[selection]: dict = {"x": [], "y": []}
            continue
        singleData = data[i].replace("\n", "").split(",")
        dataSet[selection]["x"].append(int(singleData[0]))
        dataSet[selection]["y"].append(float(singleData[1]))
        if (float(singleData[1]) > maxTime):
            maxTime = float(singleData[1])

    for key in dataSet:
        plt.plot(dataSet[key]["x"], dataSet[key]["y"], label="Hybrid-" + key)
        plt.legend()


def addDFSorBFS(algo: str, data: str):
    global totalAnchorNum
    global totalNodeNum
    global maxTime
    x = []
    y = []
    dataList = data.split(",")
    for i in range(0, totalAnchorNum + 5, 5):
        x.append(i)
        y.append(float(dataList[0]))
        if (float(dataList[0]) > maxTime):
            maxTime = float(dataList[0])
    plt.plot(x, y, label=algo)
    plt.legend()


fileName = input("Enter file name to generate graph.\n")

file = open(fileName, "r")

content = []
for line in file:
    content.append(line)
file.close()

totalNodeNum = int(content[0])
totalAnchorNum = int(content[1])
for i in range(2, len(content)):
    if "BFS" in content[i] or "DFS" in content[i]:
        addAlgo(content[i].replace("\n", "").replace("Algo:", ""), content[i + 1: i + 2])
        i = i + 1
    if "Hybrid" in content[i]:
        addAlgo(content[i].replace("\n", "").replace("Algo:", ""), content[i + 1:])


plt.axis([0, totalAnchorNum, 0, maxTime + 10])
plt.grid(linestyle='--')
plt.title("Algorithm comparison for " + str(totalNodeNum) + " nodes")
ax = plt.gca()
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
plt.xlabel('Anchor Num')
plt.ylabel('time(s)')
plt.show()