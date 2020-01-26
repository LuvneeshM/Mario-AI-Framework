from PIL import Image


def convert(image, level, mapsheet, enemymap):
    print('{},{}'.format(len(level), len(level[0])))
    for i in range(0, len(level)):
        for j in range(0, len(level[0])):
            c = level[i][j]
            print(c)
            if c == 'F':
                # draw flag
                continue
            elif c == 'y':
                # spiky
                image.paste(
                    get_tile(enemymap, (16, 32), 0, 0, 4), (16*i, 16*j))
                continue
            elif c == 'Y':
                # spiky_wing
                g = get_tile(enemymap, (16, 32), 0, 0, 3)
                image.paste(g, (16*j, 16*i), g)
                g = get_tile(enemymap, (16, 32), 0, 0, 4)
                image.paste(g, (16*j, 16*i), g)
                continue
            elif c == 'g':
                # goomba
                g = get_tile(enemymap, (16, 32), 0, 0, 2)
                image.paste(g, (16*j, 16*i), g)
                continue
            elif c == 'G':
                # goomba winged
                g = get_tile(enemymap, (16, 32), 0, 0, 2)
                image.paste(g, (16*j, 16*i), g)
                g = get_tile(enemymap, (16, 32), 0, 0, 4)
                image.paste(g, (16*j-8, 16*i-10), g)
                continue
            elif c == 'k':
                # green koopa
                g = get_tile(enemymap, (16, 32), 0, 0, 1)
                g.save('test.png', 'png')
                image.paste(g, (16*j, 16*i-16), g)
                continue
            elif c == 'K':
                # green koopa winged
                g = get_tile(enemymap, (16, 32), 0, 0, 1)
                image.paste(g, (16*j, 16*i-16), g)
                g = get_tile(enemymap, (16, 32), 0, 0, 4)
                image.paste(g, (16*j-8, 16*i-20), g)
                continue
            elif c == 'r':
                # red koopa
                g = get_tile(enemymap, (16, 32), 0, 0, 0)
                image.paste(g, (16*j, 16*i-16), g)
                continue
            elif c == 'R':
                # red koopa winged
                g = get_tile(enemymap, (16, 32), 0, 0, 0)
                image.paste(g, (16*j, 16*i-16), g)
                g = get_tile(enemymap, (16, 32), 0, 0, 4)
                image.paste(g, (16*j-8, 16*i-20), g)
                continue
            elif c == 'X':
                # //floor
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 1, 0), (16*j, 16*i))
                continue
            elif c == '#':
                # //pyramidBlock
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 2, 0), (16*j, 16*i))
                continue
            elif c == '@':
                # //mushroom question block
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 0, 1), (16*j, 16*i))
                continue
            elif c == '!':
                # //coin question block
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 0, 1), (16*j, 16*i))
                continue
            elif c == 'D':
                # //used
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 2, 2), (16*j, 16*i))
                continue
            elif c == 'S':
                # //normal block
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 2, 6), (16*j, 16*i))
                continue
            elif c == 'C':
                # //coin block

                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 2, 6), (16*j, 16*i))
                continue
            elif c == 'U':
                # //mushroom block
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 2, 6), (16*j, 16*i))
                continue
            elif c == 'L':
                # //1up block
                image.paste(
                    get_tile(mapsheet, (16, 16), 0, 2, 6), (16*j, 16*i))
                continue
            elif c == 'o':
                # //coin
                g = get_tile(mapsheet, (16, 16), 0, 7, 1)
                image.paste(g, (16*j, 16*i), g)
                continue
            elif c == 't':
                # empty Pipe
                if j > 0 and level[i][j-1] == 't':
                    if i > 0 and level[i-1][j] != 't':
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 3, 2), (16*j, 16*i))
                    else:
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 5, 2), (16*j, 16*i))
                else:
                    if j > 0 and level[i-1][j] != 't':
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 2, 2), (16*j, 16*i))
                    else:
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 4, 2), (16*j, 16*i))

                continue
            elif c == 'T':
                if j > 0 and level[i][j-1] == 't':
                    if i > 0 and level[i-1][j] != 't':
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 3, 2), (16*j, 16*i))
                    else:
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 5, 2), (16*j, 16*i))
                else:
                    if j > 0 and level[i-1][j] != 't':
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 2, 2), (16*j, 16*i))
                    else:
                        image.paste(
                            get_tile(mapsheet, (16, 16), 0, 4, 2), (16*j, 16*i))
                continue
    return image


def read_map(filepath):
    spritesheet = Image.open(filepath)
    return spritesheet


def read_ascii(filepath):
    with open(filepath) as level_file:
        level = level_file.readlines()
        level = [row.rstrip('\n') for row in level]
        return level


def get_tile(spritesheet, tileSize, margin, tileX, tileY):
    posX = (tileSize[0] * tileX) + (margin * (tileX + 1))
    posY = (tileSize[1] * tileY) + (margin * (tileY + 1))
    box = (posX, posY, posX + tileSize[0], posY + tileSize[1])
    return spritesheet.crop(box)


def main():
    map_path = '../../levels/original/lvl-1.txt'
    level = read_ascii(map_path)
    print('{}, {}'.format(len(level[0])*16, len(level)*16))

    # dimensions are found from the ascii map
    image = Image.new('RGB', (len(level[0]*16), len(level)*16), (0, 145, 255))

    # read in all the spritesheets

    levelmap = read_map('../../img/mapsheet.png')
    enemymap = read_map('../../img/enemysheet.png')
    convert(image, level, levelmap, enemymap)
    image.save('level.png', 'png')


main()
