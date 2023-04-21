import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

    private static final int SERVER_PORT = 12345;
    private static final int SYNC_INTERVAL = 10000; // Intervalo de sincronización en milisegundos

    public static void main(String[] args) {
        //permitir el ingreso del server
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la dirección IP del servidor: ");
        String serverAddress = scanner.nextLine();
       
       

      

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("El servidor está esperando conexiones de los clientes...");

           
            while (true) {
                Socket clientSocket = serverSocket.accept();
              
                ClientHandlers handler = new ClientHandlers(clientSocket);
               
                handler.start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static long getTimeFromHost(String host) throws IOException {
        // Código para obtener la hora actual de un host remoto
        return System.currentTimeMillis();
    }
}

class ClientHandlers extends Thread {
    private Socket clientSocket;
 
//contructor
    public ClientHandlers(Socket clientSocket) {
        this.clientSocket = clientSocket;
   
    }

    

    @Override
    public void run() {
              long threadId = Thread.currentThread().getId();
        System.out.println("Hilo " + threadId + " iniciado para manejar la conexión con el cliente " + clientSocket.getInetAddress().getHostAddress());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
            // Enviar la hora actual del servidor al cliente
            String clientAddresss = reader.readLine();
            System.out.printf("Cliente conectado: %s%n", clientAddresss);
            long serverTime = System.currentTimeMillis();
            writer.println(serverTime);

            // Recibir el reloj del cliente y actualizar el reloj global
            long clientClock = Long.parseLong(reader.readLine());
           
             } catch (NumberFormatException e) {
                // TODO Auto-generated catch block12
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }}
}