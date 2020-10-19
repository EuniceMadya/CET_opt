import matplotlib.pyplot as plt
import os

TOTAL_NODE_NUM: int = 0
MIN_ANCHOR_NUM: int = 10000
MAX_ANCHOR_NUM: int = 0
MAX_TIME: float = 0.0

ALGORITHMS: dict = {"BFS": "BFS", "DFS": "DFS", "Anchor":"Anchor"}
CONCATENATE: dict = {"BFS":"B", "DFS": "D"}
LEVEL_NUM: dict = {"Single": "Single", "Double":"Double"}
THREADING_TYPE: dict = {"Sequential": "Seq", "Concurrent": "Con"}
SPLIT_TYPE: dict = {"LD": "LargestDegree", "Random": "Random", "Distro": "EquallyDistribute"}

#filename: nodenum-frequency-ALGORITHMS(-LEVEL_NUM-THREADING_TYPE-SPLIT_TYPE-DCONCATENATE).txt

def list_options(num_nodes: int):

    file_list = os.listdir(str(num_nodes))
    option_string = "0. Exit\n"
    options = []
    selections = []
    actual_names = []
    counter = 1
    file_list.sort()
    for filename in file_list:
        if filename.startswith("CSR"):
            continue
        filename.rstrip()
        actual_names.append(filename)
        filename = filename.replace(".txt", "")
        name = filename[len(str(num_nodes)) + 6 : ]
        option_string = option_string + str(counter) + ". "+ name + "\n"
        options.append(name)

        counter = counter + 1

    selection = "-1"
    while 1:
        print("Please choose the ones you want to compare with")
        print(option_string)
        selection = int(input())
        if selection == 0:
            return
        selections.append(options[selection - 1])
        print("\nCurrent selections: ")
        print(selection_to_string(selections))
        process_selection(actual_names[selection - 1], options[selection - 1])



def selection_to_string(selections: list):
    string_output = ""
    for selection in selections:
        string_output = string_output + selection + "\n"

    return string_output



def process_selection(filename: str, name: str):
    file = open(str(TOTAL_NODE_NUM) + "/" + filename, "r")
    data = []
    for line in file:
        data.append(line.strip())
    file.close()

    if name.startswith("DFS") or name.startswith("BFS"):
        add_DFSorBFS("DFS" if name.startswith("DFS") else "BFS", data[0])
    else:
        add_anchor(name, data)

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


def add_anchor(algo: str, data: list):
    global MIN_ANCHOR_NUM
    global MAX_ANCHOR_NUM
    global MAX_TIME
    x = []
    y = []
    if(int(data[0].split(",")[0]) < MIN_ANCHOR_NUM):
        MIN_ANCHOR_NUM = int(data[0].split(",")[0])

    if (int(data[len(data) - 1].split(",")[0]) > MAX_ANCHOR_NUM):
        MAX_ANCHOR_NUM = int(data[len(data) - 1].split(",")[0])

    for i in range(len(data)):
        single_data = data[i].split(",")
        x.append(int(single_data[0]))
        y.append(float(single_data[1]))
        if (float(single_data[1]) > MAX_TIME):
            MAX_TIME = float(single_data[1])

    plt.plot(x, y, label=algo)
    plt.legend()


TOTAL_NODE_NUM = int(input("Choose the graph you want to generate from: "))
list_options(TOTAL_NODE_NUM)

plt.axis([MIN_ANCHOR_NUM - 5, MAX_ANCHOR_NUM + 5, 0, MAX_TIME + MAX_TIME/10])
plt.grid(linestyle='--')
plt.title("Algorithm comparison for " + str(TOTAL_NODE_NUM) + " nodes")
ax = plt.gca()
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
plt.xlabel('Anchor Num')
plt.ylabel('time(s)')
plt.show()
