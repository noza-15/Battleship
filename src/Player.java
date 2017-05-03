public class Player {


    private String playerName;
    Player(String inputName){
        setPlayerName(inputName);
    }

    public void newGame(){

    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getPlayerName() {
        return playerName;
    }
}
