import os
import json


def read_json(filepath):
    with open(filepath) as mech_file:
        data = json.load(mech_file)
        return data


def main():
    filename = 'event_log_20190514_201424_lvl-1.txt.json'
    filepath = '../logs/' + filename

    x_diff = 2
    time_diff = 10
    is_ai = False

    data = read_json(filepath)
    split_into_scenes(data, is_ai, x_diff, time_diff)


def split_into_scenes(data, is_ai, x, t):

    mech_list = []
    for mech in data.values():
        mech[0]['Delete'] = False
        mech_list.append(mech[0])

    # loop until no more mechanics are mergable
    # mergable defined as, adjacent, identical mechanics that are close enough to each other in time and space
    flag = True
    print(len(mech_list))
    while flag:
        flag = False
        for mech_1 in mech_list:
            for mech_2 in mech_list:
                if mech_1 != mech_2 and mech_1['Action'] == mech_2['Action']:
                    if check_proximity(mech_1, mech_2, is_ai, x, t):
                        print('{}: {}'.format(mech_1, mech_2))
                        mech_2['Delete'] = True
                        flag = True
        mech_list = reset_list(mech_list)
        print(len(mech_list))


def check_proximity(mech_1, mech_2, is_ai, x, t):
    flag_1 = False
    if not is_ai:
        flag_1 = abs(int(mech_1['Time']) - float(mech_2['Time'])) < t
    return abs(float(mech_1['X']) - float(mech_2['X'])) < x or flag_1


def reset_list(mech_list):
    temp_list = []
    for mech in mech_list:
        if mech['Delete'] is False:
            temp_list.append(mech)

    return list(temp_list)


main()
