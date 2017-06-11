import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;

class GameServerThread extends Thread {
    private Socket socket;
    private CommandHandler cmd;

    GameServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Accept: " + socket + "]");
            cmd = new CommandHandler(socket);
            handleCommand();
        } catch (IOException e) {
            System.err.println("クライアントとの接続が失われました。");
        } finally {
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Close socket:" + socket + "]");
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void handleCommand() {
        GameGroup group = null;
        Player player = null;
        boolean continueFlag = true;
        while (continueFlag) {
            try {
                switch (cmd.receiveInt()) {
                    case Server.REGISTER:
                        player = (Player) cmd.receiveObject();
                        group = Server.groupList.get(player.getGroupID());
                        if (group.isOpen()) {
                            cmd.send(true);
                        } else {
                            cmd.send(false);
                            break;
                        }

                        switch (player.getJobCode()) {
                            case Server.ATTACKER:
                                if (group.getAttackersCount() == 0) {
                                    cmd.send(true);
                                    player.setParent(true);
                                    group.setParentID(Server.allPlayerList.size());
                                } else {
                                    cmd.send(false);
                                }
                                break;
                            case Server.BYSTANDER:
                                cmd.send(false);
                                break;
                        }

                        player.setPlayerID(Server.allPlayerList.size());
                        group.addPlayer(player);
                        Server.allPlayerList.add(player);
                        cmd.send(Server.allPlayerList.size() - 1);
                        String output = "Register: " + player;
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [" + output + "]");
                        cmd.send(output);
                        group.addCommandHandler(cmd);
                        break;

                    case Server.NEW_GROUP:
                        Server.groupList.add(new GameGroup(Server.groupList.size()));
                        cmd.send(Server.groupList.size() - 1);
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Create group: " + (Server.groupList.size() - 1) + "]");
                        break;

                    case Server.DOES_EXIST_GROUP:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Does group exist: " + socket + "]");
                        cmd.send(Server.groupList.size());
                        break;


                    case Server.LIST_GROUP:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [List group: " + socket + "]");
                        cmd.send(Server.groupList.size()); //グループ数出力
                        for (int i = 0; i < Server.groupList.size(); i++) {
                            cmd.send(i + 1 + ": " + Server.groupList.get(i));
                        }
                        break;

                    case Server.CLOSE_APPLICATIONS:
                        if (group.getAttackersCount() > 1) {
                            group.closeGroup();
                            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Close application: " + "Success " + player.getGroupID() + " " + socket + "]");
                            cmd.send(true);
                        } else {
                            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Close application: " + "Failed " + player.getGroupID() + " " + socket + "]");
                            cmd.send(false);
                            cmd.send("More than 2 attackers are needed to start a game!");
                        }
                        break;
                    case Server.SET_SHIPS:
                        broadcast(group.getOutList(), cmd.receiveString());
                        break;
                    case Server.START:
                        broadcast(group.getOutList(), Server.START);
                        break;
                    default:
                        System.out.println("?");
                        break;
                }
            } catch (SocketException se) {
                continueFlag = false;
                System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Lost connection: player  " + player.getPlayerID() + "]");
                se.printStackTrace();
            }
        }
    }

    private void broadcast(ArrayList<CommandHandler> group, String command) {
        for (CommandHandler handler : group) {
            handler.send(command);
        }
    }

    private void broadcast(ArrayList<CommandHandler> group, int command) {
        for (CommandHandler handler : group) {
            handler.send(command);
        }
    }
}

