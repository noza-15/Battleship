package BattleShip;

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
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Acp: " + socket + "]");
            cmd = new CommandHandler(socket);
            handleCommand();
        } catch (IOException e) {
            System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [IOException: " + socket + "]");
        } finally {
            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName() + "] [Cls_socket: " + socket + "]");
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
                        cmd.send(Server.allPlayerList.size());
                        String output = "Reg: " + player;
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [" + output + "]");
                        Server.allPlayerList.add(player);
                        group.addPlayer(player);
                        cmd.send(output);
                        group.addCommandHandler(cmd);
                        break;

                    case Server.NEW_GROUP:
                        Server.groupList.add(new GameGroup(Server.groupList.size()));
                        cmd.send(Server.groupList.size() - 1);
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Crt_grp: " + (Server.groupList.size() - 1) + "]");
                        break;

                    case Server.LIST_GROUP:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Lst_grp: " + socket + "]");
                        cmd.send(Server.groupList.size()); //グループ数出力
                        for (int i = 0; i < Server.groupList.size(); i++) {
                            cmd.send(i + 1 + ": " + Server.groupList.get(i));
                        }
                        break;

                    case Server.CHECK_GROUP_EXISTENCE:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Chk_grp_existence: " + socket + "]");
                        cmd.send(Server.groupList.size());
                        break;

                    case Server.LIST_MEMBERS:
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Lst_mem: " + "GroupID=" + player.getGroupID() + "; PlayerID=" + player.getPlayerID() + "]");
                        cmd.send(group.getAttackersCount());
                        cmd.send(group.getBystandersCount());
                        for (int i = 0; i < group.getPlayersList().size(); i++) {
                            cmd.send(i + 1 + ": " + group.getPlayersList().get(i));
                        }
                        break;

                    case Server.CLOSE_APPLICATIONS:
                        if (group.getAttackersCount() > 1) {
                            group.closeGroup();
                            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                    + "] [Cls_application: " + "success; GroupID=" + player.getGroupID() + "; PlayerID=" + player.getPlayerID() + "]");
                            cmd.send(true);
                        } else {
                            System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                    + "] [Cls_application: " + "failure; GroupID=" + player.getGroupID() + "; PlayerID=" + player.getPlayerID() + "]");
                            cmd.send(false);
                            cmd.send("Error: 2 attackers are needed to start a game at least.");
                        }
                        break;

                    case Server.START:
                        broadcast(group.getOutList(), Server.START);
                        broadcast(group.getOutList(), group.getPlayersList());
                        break;

                    case Server.SET_SHIPS:
                        group.setShipsMap(player, (Ship[][]) cmd.receiveObject());
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Recv_map: " + "PlayerID=" + player.getPlayerID() + "]");
                        break;

                    case Server.GET_SHIPS:
                        while (!group.canStart()) {
                            Thread.sleep(200);
                        }
                        cmd.send(group.getAllShipsMap());
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Send_map: " + "PlayerID=" + player.getPlayerID() + "]");
                        break;

                    case Server.ATTACK:
                        AttackCommand command = (AttackCommand) cmd.receiveObject();
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Recv_command: " + "GroupID=" + player.getGroupID() + " TurnNo=" + command.getTurnNo() + " PlayerID=" + player.getPlayerID() + "]");
                        broadcast(group.getOutList(), command);
                        System.out.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                                + "] [Send_command: " + "GroupID=" + player.getGroupID() + " TurnNo=" + command.getTurnNo() + " PlayerID=" + player.getPlayerID() + "]");
                        break;

                    default:
                        System.out.println("?");
                        break;
                }
            } catch (SocketException se) {
                continueFlag = false;
                try {
                    System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                            + "] [Disconnected: PlayerID=" + player.getPlayerID() + "]");
                } catch (NullPointerException npe) {
                    System.err.println(Calendar.getInstance().getTime() + " [" + Thread.currentThread().getName()
                            + "] [Disconnected: " + socket + "]");
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

