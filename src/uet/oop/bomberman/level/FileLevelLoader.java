package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.GameSound;

import java.io.*;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
     * từ ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) {
        // TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
        // TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map

        try {
            String path = "res/levels/level" + level + ".txt";
            String abc = "/levels/level"+ level +".txt";
            InputStream inputStream = new FileInputStream(path);
            //InputStream inputStream = FileLevelLoader.class.getResourceAsStream(abc);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String data = bufferedReader.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            _level = Integer.parseInt(tokens.nextToken());
            _height = Integer.parseInt(tokens.nextToken());
            _width = Integer.parseInt(tokens.nextToken());
            _map = new char[_height][_width];
            for (int i = 0; i < _height; i++) {
                String dataLine = bufferedReader.readLine();
                char[] Line = dataLine.toCharArray();
                for (int j = 0; j < _width; j++) {
                    _map[i][j] = Line[j];
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createEntities() {
        // TODO: tạo các Entity của màn chơi
        // TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

        // TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
        // TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                int pos = x + y * _width;
                char c = _map[y][x];

                switch (c) {
                    // thêm Wall
                    case '#':
                        int xW = x, yW = y;
                        _board.addEntity(pos, new Wall(xW, yW, Sprite.wall));
                        break;

                    // thêm Bomber
                    case 'p':
                        int xBomber = x, yBomber = y;
                        _board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
                        Screen.setOffset(0, 0);
                        _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
                        break;

                    // thêm Enemy
                    case '1':
                        int xE1 = x,yE1 = y;
                        _board.addCharacter(new Balloon(Coordinates.tileToPixel(xE1), Coordinates.tileToPixel(yE1) + Game.TILES_SIZE, _board));
                        _board.addEntity(xE1 + yE1 * _width, new Grass(xE1, yE1, Sprite.grass));
                        break;

                    case '2':

                        int xE2 = x, yE2 = y;
                        _board.addCharacter(new Oneal(Coordinates.tileToPixel(xE2), Coordinates.tileToPixel(yE2) + Game.TILES_SIZE, _board));
                        _board.addEntity(xE2 + yE2 * _width, new Grass(xE2, yE2, Sprite.grass));
                        break;

                    case '3':
                        int xE3 = x,yE3 = y;
                        _board.addCharacter(new Ghost(Coordinates.tileToPixel(xE3), Coordinates.tileToPixel(yE3) + Game.TILES_SIZE, _board));
                        _board.addEntity(xE3 + yE3 * _width, new Grass(xE3, yE3, Sprite.grass));
                        break;

                    case '4':
                        int xE4 = x,yE4 = y;
                        _board.addCharacter(new Dahl(Coordinates.tileToPixel(xE4), Coordinates.tileToPixel(yE4) + Game.TILES_SIZE, _board));
                        _board.addEntity(xE4 + yE4 * _width, new Grass(xE4, yE4, Sprite.grass));
                        break;

                    case '5':
                        int xE5 = x,yE5 = y;
                        _board.addCharacter(new Minvo(Coordinates.tileToPixel(xE5), Coordinates.tileToPixel(yE5) + Game.TILES_SIZE, _board));
                        _board.addEntity(xE5 + yE5 * _width, new Grass(xE5, yE5, Sprite.grass));
                        break;
                    // thêm Brick
                    case '*':
                        int xB = x, yB = y;
                        _board.addEntity(xB + yB * _width,
                                new LayeredEntity(xB, yB,
                                        new Grass(xB, yB, Sprite.grass),
                                        new Brick(xB, yB, Sprite.brick)
                                )
                        );
                        break;

                    // thêm Item kèm Brick che phủ ở trên
                    case 'x': //Portal
                        int xIP = x, yIP = y;
                        _board.addEntity(xIP + yIP * _width,
                                new LayeredEntity(xIP, yIP,
                                        new Grass(xIP, yIP, Sprite.grass),
                                        new Portal(xIP, yIP, Sprite.portal),
                                        new Brick(xIP, yIP, Sprite.brick)
                                )
                        );
                        break;

                    case 'b': //Bomb Item
                        int xIB = x, yIB = y;
                        _board.addEntity(xIB + yIB * _width,
                                new LayeredEntity(xIB, yIB,
                                        new Grass(xIB, yIB, Sprite.grass),
                                        new BombItem(xIB, yIB, Sprite.powerup_bombs),
                                        new Brick(xIB, yIB, Sprite.brick)
                                )
                        );
                        break;

                    case 's': //Speech Item
                        int xIS = x, yIS = y;
                        _board.addEntity(xIS + yIS * _width,
                                new LayeredEntity(xIS, yIS,
                                        new Grass(xIS, yIS, Sprite.grass),
                                        new SpeedItem(xIS, yIS, Sprite.powerup_speed),
                                        new Brick(xIS, yIS, Sprite.brick)
                                )
                        );
                        break;

                    case 'f': //Flame Item
                        int xIF =x, yIF =y;
                        _board.addEntity(xIF + yIF * _width,
                                new LayeredEntity(xIF, yIF,
                                        new Grass(xIF, yIF, Sprite.grass),
                                        new FlameItem(xIF, yIF, Sprite.powerup_flames),
                                        new Brick(xIF, yIF, Sprite.brick)
                                )
                        );
                        break;
                    // thêm Grass
                    default:
                        int xG = x, yG = y;
                        _board.addEntity(pos, new Grass(xG, yG, Sprite.grass));
                        break;
                }
            }
        }
    }
}

