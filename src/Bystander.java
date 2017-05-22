public class Bystander extends Player {

    Bystander(String inputName) {
        super(inputName);
        setJobCode(Server.BYSTANDER);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
    }

    @Override
    public String toString() {
        return "Bystander{" +
                "Name= " + getPlayerName() +
                ", Job= " + Server.job[getJobCode()] +
                "}";
    }
}
