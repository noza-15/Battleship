package BattleShip;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class CommandHandler {

    private Socket socket;
    // 先にOutputを生成。逆はデッドロック発生。
    // https://docs.oracle.com/javase/jp/1.4/guide/serialization/spec/input.doc1.html
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    public CommandHandler(Socket socket) throws IOException {
        this.socket = socket;
        try {
            InputStream inputStream = new BufferedInputStream(socket.getInputStream());
            OutputStream outputStream = socket.getOutputStream();
            objOut = new ObjectOutputStream(outputStream);
            objIn = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(String message) {
        try {
            objOut.writeObject(message);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(int command) {
        try {
            objOut.writeInt(command);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(boolean bool) {
        try {
            objOut.writeBoolean(bool);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object object) throws SocketException {
        try {
            objOut.writeObject(object);
            objOut.flush();
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int receiveInt() throws SocketException {
        try {
            return objIn.readInt();
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean receiveBoolean() throws SocketException {
        try {
            return objIn.readBoolean();
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String receiveString() throws SocketException {
        try {
            return (String) objIn.readObject();
        } catch (SocketException se) {
            throw new SocketException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "null";
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public Object receiveObject() throws SocketException {
        try {
            return objIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
