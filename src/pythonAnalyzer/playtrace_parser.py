import pdb
import numpy as np
from utils import read_file

class Parser:
    def __init__(self, filepath, verbose=0):
        self.contents = read_file(filepath)
        self.token = '<%^%>'
        self.verbose = verbose

    def separate_playthroughs(self):
        begin = 0
        playthroughs = []
        logs = self.contents['Logs']
        my_array = logs.str.replace("\n", "").to_numpy()
        new_array = np.empty(0)

        for entry in my_array:
            new_entries = entry.split(self.token)[:-1]
            # for i, e in enumerate(new_entries):
            #     print('{}: {}\n'.format(i, e))
            new_array = np.append(new_array, new_entries)
        for i,entry in enumerate(new_array):
            print('{}: {}\n'.format(i, entry))

