from playtrace_parser import Parser

if __name__ == '__main__':
    print('* Running playtrace analysis *')

    playtrace_parcer = Parser('test.txt', 1)
    playtrace_parcer.separate_playthroughs()