package game.cells;

public class Shot extends Cell{

    private boolean wasShip = false;

    public void setWasShip(boolean wasShip) {
        this.wasShip = wasShip;
    }

    @Override
    public String toString() {
        if (this.wasShip)
            return "s";
        return "ㅁ";
    }
}
