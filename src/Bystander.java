import java.net.SocketException;

public class Bystander extends Player {

    Bystander(String inputName) {
        super(inputName);
        setJobCode(Server.BYSTANDER);
    }

    @Override
    public void newGame() throws SocketException {
        scanner.next();
        super.newGame();
    }

    @Override
    public void nextTurn() throws SocketException {
        super.nextTurn();
    }

}
