import java.io.*;
import java.net.Socket;

/*
Activity 3: Create a Client class to communicate with the Socket Server.
 */

public class Client {

    public static void main(String[] args) throws IOException {
        Integer num = 50, less = 100, plus = 0;
        String line, outline;
        line = num.toString();

        Socket socket = new Socket("0.0.0.0", 4444);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        out.write(line);
        out.newLine();
        out.flush();
        outline = in.readLine().toString();

        System.out.println(outline);

        while (!outline.contains("acertou")) {
            if (!outline.contains("acertou")) {
                num = plus;
                line = num.toString();
                out.write(line);
                out.newLine();
                out.flush();
                plus++;
                outline = in.readLine().toString();
                System.out.println(outline);
            }

            if (!outline.contains("acertou")) {
                num = less;
                line = num.toString();
                out.write(line);
                out.newLine();
                out.flush();
                less--;
                outline = in.readLine().toString();
                System.out.println(outline);
            }
        }

        out.write("tchau");
        out.newLine();
        out.flush();

        out.close();
        in.close();
        socket.close();
    }
}