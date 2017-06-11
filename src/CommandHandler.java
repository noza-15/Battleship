import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class CommandHandler {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // 先にOutputを生成。逆はデッドロック発生。
    // https://docs.oracle.com/javase/jp/1.4/guide/serialization/spec/input.doc1.html
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    CommandHandler(Socket socket) throws IOException {
        this.socket = socket;
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)), true);
            objOut = new ObjectOutputStream(outputStream);
            objIn = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void send(String message) {
        out.println(message);
    }

    void send(int command) {
        out.println(command);
    }

    void send(boolean bool) {
        out.println(bool);
    }

    void send(Object object) throws SocketException {
        try {
            objOut.writeObject(object);
            objOut.flush();
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int receiveInt() throws SocketException {
        try {
            return Integer.parseInt(in.readLine());
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    boolean receiveBoolean() throws SocketException {
        try {
            return Boolean.parseBoolean(in.readLine());
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    String receiveString() throws SocketException {
        try {
            return in.readLine();
        } catch (SocketException se) {
            throw new SocketException();
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    Object receiveObject() throws SocketException {
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
