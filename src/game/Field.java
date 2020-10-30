package game;

import game.cells.Block;
import game.cells.Cell;
import game.cells.Ship;
import game.cells.Shot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Field implements FieldInterface, Serializable {
    private Cell[][] playfield;
    private int height;
    private int length;

    public Cell[][] getPlayfield() {
        return playfield;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public Field(int height, int length) {
        assert 5 <= height && height <= 30 : "height not in range 5-30";
        assert 5 <= length && length <= 30 : "length not in range 5-30";

        this.playfield = new Cell[height][length];
        this.height = height;
        this.length = length;
        this.resetField();
    }

    private void resetField() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.length; j++) {
                this.playfield[i][j] = new Cell();
            }
        }
    }

    private ArrayList<Ship> extractShips(int newHeight, int newLength) {
        int height;
        int length;
        ArrayList<Ship> ships = new ArrayList<>();
        height = Math.min(this.height, newHeight);
        length = Math.min(this.length, newLength);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (this.playfield[i][j] instanceof Ship) {
                    Ship ship = (Ship) this.playfield[i][j];
                    boolean inBounds = true;
                    for (Position position: ship.getPositions()) {
                        if (position.getX() >= newLength || position.getY() >= newHeight) {
                            inBounds = false;
                            break;
                        }
                    }
                    if (inBounds && !ships.contains(ship))
                        ships.add(ship);
                }
            }
        }
        return ships;
    }

    public void resizeField(int newHeight, int newLength) {
        if (newHeight == this.height && newLength == this.length)
            return;

        Cell[][] tempField = new Cell[newHeight][newLength];
        ArrayList<Ship> ships = this.extractShips(newHeight, newLength);

        this.playfield = tempField;
        this.height = newHeight;
        this.length = newLength;
        this.resetField();

        for (Ship ship: ships) {
            this.addShip(ship);
        }
    }

    private void blockFields(Ship ship) {
        for(Position position: ship.getPositions()) {
            if (position.getX() > 0 && this.playfield[position.getY()][position.getX() - 1].getClass() == Cell.class)
                this.playfield[position.getY()][position.getX() - 1] = new Block();
            if (position.getX() < this.length - 1 &&
                    this.playfield[position.getY()][position.getX() + 1].getClass() == Cell.class)
                this.playfield[position.getY()][position.getX() + 1] = new Block();
            if (position.getY() > 0 &&
                    this.playfield[position.getY() - 1][position.getX()].getClass() == Cell.class)
                this.playfield[position.getY() - 1][position.getX()] = new Block();
            if (position.getY() < this.playfield.length - 1 &&
                    this.playfield[position.getY() + 1][position.getX()].getClass() == Cell.class)
                this.playfield[position.getY() + 1][position.getX()] = new Block();
            if (position.getX() > 0 && position.getY() > 0 &&
                    this.playfield[position.getY() - 1][position.getX() - 1].getClass() == Cell.class)
                this.playfield[position.getY() - 1][position.getX() - 1] = new Block();
            if (position.getX() < this.length - 1 && position.getY() < this.playfield.length - 1 &&
                    this.playfield[position.getY() + 1][position.getX() + 1].getClass() == Cell.class)
                this.playfield[position.getY() + 1][position.getX() + 1] = new Block();
            if (position.getX() > 0 && position.getY() < this.playfield.length - 1 &&
                    this.playfield[position.getY() + 1][position.getX() - 1].getClass() == Cell.class)
                this.playfield[position.getY() + 1][position.getX() - 1] = new Block();
            if (position.getX() < this.length - 1 && position.getY() > 0 &&
                    this.playfield[position.getY() - 1][position.getX() + 1].getClass() == Cell.class)
                this.playfield[position.getY() - 1][position.getX() + 1] = new Block();
        }
    }

    public boolean addShip(Ship ship){
        // check if any position of ship is already occupied
        for(Position position: ship.getPositions()){
            if (this.playfield[position.getY()][position.getX()].getClass() != Cell.class)
                return false;
        }

        // place ship on playfield
        for(Position position: ship.getPositions()){
            this.playfield[position.getY()][position.getX()] = ship;
        }

        // block fields arround ship
        this.blockFields(ship);
        return true;
    }

    public boolean addShipRandom(int length){
        Random r = new Random();
        boolean placed = false;
        char [] directions = {'n', 'e', 's', 'w'};
        int loopcount = 0;

        while (!placed){
            if (loopcount > 5000) {
                return false;
            }

            Position startPos = new Position(r.nextInt(this.length), r.nextInt(this.height));
            Position[] positions = new Position[length];
            char direction = directions[r.nextInt(4)];

            switch (direction) {
                case 'n' -> {
                    if (startPos.getY() - length < 0) {
                        continue;
                    }
                    positions[0] = startPos;
                    for (int i = 1; i < length; i++) {
                        positions[i] = new Position(startPos.getX(), startPos.getY() - i);
                    }
                    placed = this.addShip(new Ship(positions));
                }
                case 's' -> {
                    if (startPos.getY() + length > this.height - 1) {
                        continue;
                    }
                    positions[0] = startPos;
                    for (int i = 1; i < length; i++) {
                        positions[i] = new Position(startPos.getX(), startPos.getY() + i);
                    }
                    placed = this.addShip(new Ship(positions));
                }
                case 'w' -> {
                    if (startPos.getX() - length < 0) {
                        continue;
                    }
                    positions[0] = startPos;
                    for (int i = 1; i < length; i++) {
                        positions[i] = new Position(startPos.getX() - i, startPos.getY());
                    }
                    placed = this.addShip(new Ship(positions));
                }
                case 'e' -> {
                    if (startPos.getX() + length > this.length - 1) {
                        continue;
                    }
                    positions[0] = startPos;
                    for (int i = 1; i < length; i++) {
                        positions[i] = new Position(startPos.getX() + i, startPos.getY());
                    }
                    placed = this.addShip(new Ship(positions));
                }
            }
            loopcount++;
        }
        return true;
    }

    public boolean addShipRandom(int [] lengths){
        for (int i = 0; i < lengths.length; i++) {
            if (!addShipRandom(lengths[i])) {
                this.resetField();
                i = 0;
            }
        }
        return true;
    }

    public int getShipCount() {
        return this.extractShips(this.height, this.length).size();
    }

    public int registerShot(Position position){
        Cell cell = this.playfield[position.getY()][position.getX()];
        this.playfield[position.getY()][position.getX()] = new Shot();

        if (cell instanceof Ship) {
            Shot s = (Shot) this.playfield[position.getY()][position.getX()];
            s.setWasShip(true);
        }

        return cell.shot();
    }

    public void printField(){
        for (int i = 65; i < this.length + 65; i++) {
            System.out.print("\t" + (char)i);
        }
        System.out.println();
        for (int i = 0; i < this.height; i++) {
            System.out.print(i + 1);
            for (Cell cell: this.playfield[i]){
                System.out.print("\t" + cell.toString());
            }
            System.out.println();
        }
    }

    public void printFieldConcealed() {
        for (int i = 65; i < this.length + 65; i++) {
            System.out.print("\t" + (char)i);
        }
        System.out.println();
        for (int i = 0; i < this.height; i++) {
            System.out.print(i + 1);
            for (Cell cell: this.playfield[i]){
                if (cell instanceof Shot) {
                    System.out.print("\t" + cell.toString());
                }
                else {
                    System.out.print("\t 0");
                }
            }
            System.out.println();
        }
    }
}
