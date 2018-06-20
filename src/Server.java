import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(4444);

        while (true) {
            System.out.println("Server running and waiting connection..." + serverSocket.toString());
            Socket clientSocket = serverSocket.accept();

            System.out.println("Getting connection from " + clientSocket.getInetAddress() + ", port=" + clientSocket.getPort() + ", localPort=" + clientSocket.getLocalPort());
            int n = (int) (Math.random() * 100);
            (new Thread(new ServerClient(clientSocket, n))).start();
        }
    }

    private static class ServerClient implements Runnable {
        private Socket socket;
        private int num;

        public ServerClient(Socket clientSocket, int num) {
            this.num = num;
            this.socket = clientSocket;
        }

        public void run() {
            try {
                int min = 0;
                int max = 100;
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                int tentativas = 0;

                line = in.readLine();
                while (!line.equals("tchau")) {
                    System.out.println("Client:" + socket.getPort() + " enviou '" + line + "'");
                    int n = Integer.parseInt(line);
                    if (n >= min && n <= max) {
                        tentativas++;
                        if (n == num) {
                            out.write("Parabéns, você acertou o número: " + num + " escolhido em " + tentativas + " tentativas.");
                        } else {
                            if (num >= min && num <= n) {
                                max = n - 1;
                            } else {
                                min = n + 1;
                            }
                            out.write("O número está entre [" + min + "," + max + "]");
                        }
                    } else {
                        out.write("Número enviado está fora do intervalo [" + min + "," + max + "]!");
                    }
                    out.newLine();
                    out.flush();
                    line = in.readLine();
                }
                System.out.print("Finishing connection with client " +
                        socket.getInetAddress() + ", port=" + socket.getPort() +
                        ", localPort=" + socket.getLocalPort() + "...");
                out.close();
                in.close();
                socket.close();
                System.out.println("Finished!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
