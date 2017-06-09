import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CommandHandler {
    private BufferedReader in;
    private PrintWriter out;

    CommandHandler(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    void send(String command) {
        out.println(command);
    }

    void send(int command) {
        out.println(command);
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
}
