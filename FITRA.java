import java.io.*;
import java.net.*;

public class FileTransferServer {
    public static void main(String[] args) {
        // initialize host and port
        String host = "127.0.0.1";
        int port = 8000;

        try {
            // create server socket object
            ServerSocket serverSocket = new ServerSocket(port);

            // listen for incoming connections
            System.out.println("Listening on " + host + ":" + port);
            while (true) {
                System.out.println("Waiting for a connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected by " + socket.getRemoteSocketAddress());

                // receive the filename from the client
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String filename = in.readLine();
                System.out.println("Filename: " + filename);

                // open the file and read its contents
                FileInputStream fis = new FileInputStream(filename);
                byte[] filedata = new byte[fis.available()];
                fis.read(filedata);
                fis.close();

                // send the file data to the client
                OutputStream out = socket.getOutputStream();
                out.write(filedata);
                out.flush();
                System.out.println("File sent successfully");

                // close the connection
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
