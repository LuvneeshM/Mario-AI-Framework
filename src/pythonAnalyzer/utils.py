import pandas as pd
def read_file(filepath):
    print('** Reading filepath: {} **'.format(filepath))
    df = pd.read_csv(filepath)

    pd.options.display.max_columns = 1000
    pd.options.display.max_rows = 1000
    pd.options.display.max_colwidth = 199
    pd.options.display.width = None
    return df