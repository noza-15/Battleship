public class Attacker extends Player {
    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();//a
    }
}
