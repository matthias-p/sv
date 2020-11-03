import enums.KiStrength;
import game.Field;
import game.Game;
import game.LocalGame;
import game.Position;
import game.cells.Ship;
import ki.Ki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[] lengths = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
//        Game game = new Game(10, 10, lengths, KiStrength.BEGINNER);
//        game.getField().addShipRandom(lengths);
//
//        while (game.getEnemyField().getShipCount() > 0) {
//            try {
//                System.out.println("Enter Coordinates");
//                String in = reader.readLine();
//                String[] splitted = in.split(" ");
//                int hit = game.getEnemyField().registerShot(new Position(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
//                System.out.println(hit);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            game.getKi().shoot();
//            game.getEnemyField().printFieldConcealed();
//        }

        Field myField = new Field(10, 10);
        Position[] positions = {new Position(0, 0), new Position(0, 1), new Position(0,2)};
        Position[] positions2 = {new Position(5, 5), new Position(6, 5), new Position(7, 5), new Position(8, 5)};
//        myField.addShip(new Ship(positions));
//        myField.addShip(new Ship(positions2));
        myField.addShipRandom(lengths);
        myField.printField();

        Ki ki = new Ki(myField, new Field(10, 10), KiStrength.INTERMEDIATE);
        ki.addShipsToField(lengths);
        int counter = 0;
        while (true) {
            ki.shoot();
            myField.printField();
            counter++;
            assert counter < 100: "endlosschleife";
        }
    }
}