public class Bystander extends Player{

    Bystander(String inputName) {
        super(inputName);
        setJobCode(Server.BYSTANDER);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
    }
}
