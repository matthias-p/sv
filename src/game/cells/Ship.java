package game.cells;

import game.Position;
import game.cells.Cell;

public class Ship extends Cell {
    private Position[] positions;
    private int length;
    private int id;

    private static int idCounter = 0;

    public Position[] getPositions() {
        return positions;
    }

    public int getId() { return this.id; }

    public Ship(Position[] positions){
        super();
        this.positions = positions;
        this.length = positions.length;

        this.id = idCounter;
        idCounter++;
    }

    public int shot(){
        this.length--;
        return this.length;
    }

    public boolean destroyed(){
        return this.length <= 0;
    }

    @Override
    public String toString() {
        return "s";
    }
}
