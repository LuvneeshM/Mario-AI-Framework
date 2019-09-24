from utils import read_file

class Parser:
    def __init__(self, filepath, verbose=0):
        self.contents = read_file(filepath)
        self.token = '<%^%>'
        self.verbose = verbose

    def separate_playthroughs(self):
        begin = 0
        playthroughs = []
        for end, line in enumerate(self.contents):
            if line == self.token:
                playthrough = ""
                for playthrough_part in self.contents[begin:end]:
                    playthrough += playthrough_part
                end = begin + 1
                playthrough = playthrough.replace('\n', '')
                playthroughs.append(playthrough)
                if self.verbose > 0:
                    print(playthrough + '\n*******************')
        return playthroughs
