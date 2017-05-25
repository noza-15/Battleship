public class Attacker extends Player {
    Attacker(String inputName) {
        super(inputName);
        setJobCode(Server.ATTACKER);
    }

    @Override
    public void nextTurn() {
        System.out.println("どこを攻撃しますか？");
        System.out.println("x");

        System.out.println("y");
    }

    @Override
    public String toString() {
        return "Attacker{" +
                "Name= " + getPlayerName() +
                ", Job= " + Server.job[getJobCode()] +
                "}";
    }
}
