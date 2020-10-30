import enums.KiStrength;
import game.Game;
import game.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[] lengths = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        Game game = new Game(10, 10, lengths, KiStrength.BEGINNER);
        game.getField().addShipRandom(lengths);

        while (game.getEnemyField().getShipCount() > 0) {
            try {
                System.out.println("Enter Coordinates");
                String in = reader.readLine();
                String[] splitted = in.split(" ");
                int hit = game.getEnemyField().registerShot(new Position(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
                System.out.println(hit);

            } catch (IOException e) {
                e.printStackTrace();
            }

            game.getKi().shoot();
            game.getEnemyField().printFieldConcealed();
        }
    }
}