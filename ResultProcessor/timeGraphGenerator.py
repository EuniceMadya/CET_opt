import matplotlib.pyplot as plt


TOTAL_NODE_NUM: int = 0
MIN_ANCHOR_NUM: int = 0
MAX_ANCHOR_NUM: int = 0
MAX_TIME: float = 0.0


def add_algo(algo: str, data: list):
    if algo == "DFS" or algo == "BFS":
        add_DFSorBFS(algo, data[0])
    else:
        add_seq(data)


def add_seq(data: list):
    global MAX_TIME
    dataset: dict = {}

    selection: str = ""
    for i in range(len(data)):
        if len(data[i].split(",")) == 1:  # and len(data[i]) != 0:
            selection = data[i]
            dataset[selection]: dict = {"x": [], "y": []}
            continue
        single_data = data[i].split(",")
        dataset[selection]["x"].append(int(single_data[0]))
        dataset[selection]["y"].append(float(single_data[1]))
        if (float(single_data[1]) > MAX_TIME):
            MAX_TIME = float(single_data[1])

    for key in dataset:
        plt.plot(dataset[key]["x"], dataset[key]["y"], label="Hybrid-" + key)
        plt.legend()


def add_DFSorBFS(algo: str, data: str):
    global MAX_TIME
    x = []
    y = []
    data_list = data.split(",")
    for i in range(0, MAX_ANCHOR_NUM + 5, 5):
        x.append(i)
        y.append(float(data_list[0]))
        if (float(data_list[0]) > MAX_TIME):
            MAX_TIME = float(data_list[0])
    plt.plot(x, y, label=algo)
    plt.legend()


fileName = input("Enter file name to generate graph.\n")

file = open(fileName, "r")

content = []
for line in file:
    if len(line.strip()) == 0 or "#" in line:
        continue
    content.append(line.strip())
file.close()

TOTAL_NODE_NUM = int(content[0])
MIN_ANCHOR_NUM = int(content[1])
MAX_ANCHOR_NUM = int(content[2])

for i in range(3, len(content)):
    if "BFS" in content[i] or "600-f1.0-DFS.txt" in content[i]:
        add_algo(content[i].replace("Algo:", ""), content[i + 1: i + 2])
        i = i + 1
    if "Hybrid" in content[i]:
        add_algo(content[i].replace("Algo:", ""), content[i + 1:])

plt.axis([MIN_ANCHOR_NUM - 5, MAX_ANCHOR_NUM, 0, MAX_TIME + MAX_TIME/10])
plt.grid(linestyle='--')
plt.title("Algorithm comparison for " + str(TOTAL_NODE_NUM) + " nodes")
ax = plt.gca()
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
plt.xlabel('Anchor Num')
plt.ylabel('time(s)')
plt.show()
