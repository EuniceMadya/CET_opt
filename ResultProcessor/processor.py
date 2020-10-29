import matplotlib.pyplot as plt
import os
from itertools import takewhile

TOTAL_NODE_NUM: int = 0
MIN_ANCHOR_NUM: int = 10000
MAX_ANCHOR_NUM: int = 0
MAX_TIME: float = 0.0

ALGORITHMS: dict = {"BFS": "BFS", "DFS": "DFS", "Anchor":"Anchor"}
CONCATENATE: dict = {"BFS":"B", "DFS": "D"}
LEVEL_NUM: dict = {"Single": "Single", "Double":"Double"}
THREADING_TYPE: dict = {"Seq": "Sequential", "Con": "Concurrent"}
SPLIT_TYPE: dict = {"LD": "LargestDegree", "Random": "Random", "EquallyDistribute": "EquallyDistribute"}

#filename: nodenum-frequency-ALGORITHMS(-LEVEL_NUM-THREADING_TYPE-SPLIT_TYPE-DCONCATENATE).txt

def list_options(num_nodes: int):
    global RES

    file_list = os.listdir(str(num_nodes))
    option_string = "0. Exit\n"
    prefix = []
    selections = []
    selection_id = []
    actual_names = []
    counter = 1
    file_list.sort()
    for filename in file_list:
        if (not filename.startswith(str(num_nodes))):
            continue
        filename.rstrip()
        actual_names.append(filename)
        filename = filename.replace(".txt", "")
        name = filename[len(str(num_nodes)) + 6 : ]
        option_string = option_string + str(counter) + ". "+ name + "\n"
        prefix.append(name)
        counter = counter + 1


    selection = "-1"
    while 1:
        print("Please choose the ones you want to compare with")
        print(option_string)
        selection = int(input())
        if selection == 0:
            break
        selections.append(prefix[selection - 1])
        selection_id.append(selection)
        print("\nCurrent selections: ")
        print(selection_to_string(selections))

    selections_without_BD = selections
    try:
        selections_without_BD.remove("BFS")

    except:
        pass

    try:
        selections_without_BD.remove("DFS")
    except Exception as e:
        pass

    res = get_common_prefix(selections_without_BD)

    print("common prefix: ", res)

    for id in selection_id:
        remove_prefix = prefix[id-1].replace(res, "")
        process_selection(actual_names[id - 1], remove_prefix)


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
    for i in range(0, TOTAL_NODE_NUM + 5, 5):
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


def get_common_prefix(selections: list):
    print("selections", selections)
    res = ''.join(c[0] for c in takewhile(lambda x: all(x[0] == y for y in x), zip(*selections)))
    print("啊啊啊啊啊", res)
    common_attrs = ""
    if "-" in res:
        common_attrs = res[:res.rindex("-")]
    return common_attrs


TOTAL_NODE_NUM = int(input("Choose the graph you want to generate from: "))
list_options(TOTAL_NODE_NUM)

if(MIN_ANCHOR_NUM > MAX_ANCHOR_NUM):
    MAX_ANCHOR_NUM = TOTAL_NODE_NUM
    MIN_ANCHOR_NUM = 0

plt.axis([MIN_ANCHOR_NUM - 5, MAX_ANCHOR_NUM + 5, 0, MAX_TIME + MAX_TIME/10])
plt.grid(linestyle='--')


plt.title("Algorithm comparison for " + str(TOTAL_NODE_NUM) + " nodes" )
ax = plt.gca()
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
plt.xlabel('Anchor Num')
plt.ylabel('time(s)')
plt.show()
