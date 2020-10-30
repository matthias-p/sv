package ki;

import enums.KiStrength;
import game.Field;
import game.Position;
import game.cells.Shot;

import java.util.Random;

public class Ki {
    private Field enemyField;
    private Field kiField;
    private KiStrength kiStrength;

    public Ki(Field enemyField, Field kiField, int[] shiplengths, KiStrength kiStrength){
        this.enemyField = enemyField;
        this.kiField = kiField;
        this.kiField.addShipRandom(shiplengths);
        this.kiStrength = kiStrength;
    }

    public void shoot() {
        assert this.enemyField.getShipCount() > 0 : "Es gibt keine Schiffe mehr";

        switch (this.kiStrength) {
            case BEGINNER -> this.shootRandom();
            case INTERMEDIATE -> this.shootRows();
        }
    }

    private void shootRandom() {
        Random r = new Random();

        while (true) {
            int height = r.nextInt(this.enemyField.getHeight());
            int length = r.nextInt(this.enemyField.getLength());

            if (!(this.enemyField.getPlayfield()[height][length] instanceof Shot)) {
                this.enemyField.registerShot(new Position(length, height));
                break;
            }
        }
    }

    private int y = 0;
    private int x = 0;
    private void shootRows() {
        if (y % 2 == 0) {
            this.enemyField.registerShot(new Position(x, y));
        }
        else {
            this.enemyField.registerShot(new Position(x + 1, y));
        }
        x += 2;
        if (x >= this.enemyField.getLength()) {
            x = 0;
            y++;
        }
    }
}
