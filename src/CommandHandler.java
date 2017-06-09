import java.io.*;
import java.net.Socket;

public class CommandHandler {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // 先にOutputを生成。逆はデッドロック発生。
    // https://docs.oracle.com/javase/jp/1.4/guide/serialization/spec/input.doc1.html
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    CommandHandler(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    CommandHandler(Socket socket) {
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

    void send(String command) {
        out.println(command);
    }

    void send(int command) {
        out.println(command);
    }

    void send(Object object) {
        try {
            objOut.writeObject(object);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int receiveInt() {
        try {
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    boolean receiveBoolean() {
        try {
            return Boolean.parseBoolean(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    String receiveString() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    Object receiveObject() {
        try {
            return objIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
