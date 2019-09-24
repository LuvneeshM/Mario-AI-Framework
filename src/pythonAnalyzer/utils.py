
def read_file(filepath):
    print('** Reading filepath: {} **'.format(filepath))
    f = open(filepath)
    return f.readlines()