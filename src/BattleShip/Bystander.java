package BattleShip;

import java.net.SocketException;

public class Bystander extends Player {

    private static final long serialVersionUID = -1919533918019424969L;

    Bystander(String inputName) {
        super(inputName);
        setJobCode(Server.BYSTANDER);
    }

    @Override
    public void newGame() throws SocketException {
        manager = new ShipManager();
        super.newGame();
    }

    @Override
    public void nextTurn() throws SocketException {
        while (true) {
            super.nextTurn();
        }
    }

}
