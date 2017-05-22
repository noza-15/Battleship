public class Attacker extends Player {
    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
    }

    @Override
    public String toString() {
        return "Attacker{" +
                "Name= " + getPlayerName() +
                ", Job= " + Server.job[getJobCode()] +
                "}";
    }
}
