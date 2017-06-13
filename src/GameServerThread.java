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
            System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [IOException: " + socket + "]");
        } finally {
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Close_socket:" + socket + "]");
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
                        group = Server.groupList.get(cmd.receiveInt());
                        if (group.isOpen()) {
                            cmd.send(true);
                        } else {
                            cmd.send(false);
                            break;
                        }
                        try {
                            player = (Player) cmd.receiveObject();
                        } catch (ClassCastException cce) {
                            cmd.receiveObject();
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
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [" + output + "]");
                        cmd.send(output);
                        group.addCommandHandler(cmd);
                        break;

                    case Server.NEW_GROUP:
                        Server.groupList.add(new GameGroup(Server.groupList.size()));
                        cmd.send(Server.groupList.size() - 1);
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Crate_grp: " + (Server.groupList.size() - 1) + "]");
                        break;

                    case Server.CHECK_GROUP_EXISTENCE:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Check_grp_existence: " + socket + "]");
                        cmd.send(Server.groupList.size());
                        break;

                    case Server.LIST_GROUP:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [List_grp: " + socket + "]");
                        cmd.send(Server.groupList.size()); //グループ数出力
                        for (int i = 0; i < Server.groupList.size(); i++) {
                            cmd.send(i + 1 + ": " + Server.groupList.get(i));
                        }
                        break;

                    case Server.CLOSE_APPLICATIONS:
                        if (group.getAttackersCount() > 1) {
                            group.closeGroup();
                            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                    + "] [Close_application: " + "success; GroupID= " + player.getGroupID() + "; PlayerID= " + player.getPlayerID() + "]");
                            cmd.send(true);
                        } else {
                            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                    + "] [Close_application: " + "failure; GroupID= " + player.getGroupID() + "; PlayerID= " + player.getPlayerID() + "]");
                            cmd.send(false);
                            cmd.send("Error: 2 attackers are needed to start a game at least.");
                        }
                        break;

                    case Server.START:
                        broadcast(group.getOutList(), Server.START);
                        broadcast(group.getOutList(), group.getPlayersList());
                        break;

                    case Server.SET_SHIPS:
                        group.setShipsMap(player, (ShipNew[][]) cmd.receiveObject());
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Receive_map: " + "PlayerID= " + player.getPlayerID() + "]");
                        while (!group.canStart()) {
                            Thread.sleep(200);
                        }
                        cmd.send(group.getAllShipsMap());
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Send_map: " + "PlayerID= " + player.getPlayerID() + "]");
                        break;

                    case Server.ATTACK:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Receive_command: " + "PlayerID= " + player.getPlayerID() + "]");
                        broadcast(group.getOutList(), cmd.receiveObject());
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Send_command: " + "PlayerID= " + player.getPlayerID() + "]");
                        break;

                    default:
                        System.out.println("?");
                        break;
                }
            } catch (SocketException se) {
                continueFlag = false;
                try {
                    System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                            + "] [Lost_connection: PlayerID= " + player.getPlayerID() + "]");
                } catch (NullPointerException npe) {
                    System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                            + "] [Lost_connection: " + socket + "]");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(ArrayList<CommandHandler> group, Object command) throws SocketException {
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

