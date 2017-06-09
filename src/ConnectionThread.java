import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ConnectionThread extends Thread {
    private Socket socket;

    ConnectionThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Connection accepted: " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            handleCommand(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("closing...");
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("クライアントとの接続が失われました。");
            }
        }
    }

    private void handleCommand(BufferedReader in, PrintWriter out) throws IOException {
        int groupID, jobCode;
        String name, command;
        GameGroup group = null;
//        CommandHandler cmdHandler = new CommandHandler(in,out);
        while ((command = in.readLine()) != null) {
            switch (Integer.parseInt(command)) {
                case Server.REGISTER:
                    groupID = Integer.parseInt(in.readLine());
                    name = in.readLine();
                    jobCode = Integer.parseInt(in.readLine());
                    group = Server.groupList.get(groupID);
                    if (group.isOpen()) {
                        out.println(true);
                    } else {
                        out.println(false);
                    }
                    switch (jobCode) {
                        case Server.ATTACKER:
                            Attacker attacker = new Attacker(name);
                            group.addPlayer(attacker);
                            Server.allPlayerList.add(attacker);
                            if (group.getAttackersCount() == 1) {
                                out.println(true);
                                group.setParentID(Server.allPlayerList.size() - 1);
                            } else {
                                out.println(false);
                            }
                            break;
                        case Server.BYSTANDER:
                            Bystander bystander = new Bystander(name);
                            group.addPlayer(bystander);
                            Server.allPlayerList.add(bystander);
                            out.println(false);
                            break;
                    }
                    out.println(Server.allPlayerList.size() - 1);
                    String output = "Registered : " + name + ", " + Server.JOB[jobCode] + ", playerID: " + (Server.allPlayerList.size() - 1);
                    System.out.println(output);
                    out.println(output);
                    group.addPrintWriter(out);
                    break;

                case Server.NEW_GROUP:
                    System.out.println("New group generated.");
                    Server.groupList.add(new GameGroup(Server.groupList.size()));
                    out.println(Server.groupList.size() - 1);
                    break;

                case Server.DOES_EXIST_GROUP:
                    System.out.println("Does group exist?");
                    out.println(Server.groupList.size());
                    break;

                case Server.LIST_GROUP:
                    System.out.println("Group list:");
                    out.println(Server.groupList.size()); //グループ数出力
                    for (int i = 0; i < Server.groupList.size(); i++) {
                        out.println(i + 1 + ": " + Server.groupList.get(i));
                    }
                    break;

                case Server.CLOSE_APPLICATIONS:
                    System.out.println("Close group");
                    if (group.getAttackersCount() > 1) {
                        group.closeGroup();
                        out.println(true);
                    } else {
                        out.println(false);
                        out.println("More than 2 attackers are needed to start a game!");
                    }
                    break;
                case Server.SET_SHIPS:
                    broadcast(group.getOutList(), in.readLine());
                case Server.START:
                    broadcast(group.getOutList(), Server.START);
                    break;
                default:
                    System.out.println("?");
                    break;
            }
        }
    }

    void broadcast(ArrayList<PrintWriter> group, String command) {
        for (PrintWriter printWriter : group) {
            printWriter.println(command);
        }
    }

    void broadcast(ArrayList<PrintWriter> group, int command) {
        for (PrintWriter printWriter : group) {
            printWriter.println(command);
        }
    }
}

