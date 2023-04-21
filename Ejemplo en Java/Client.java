import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private static final int SERVER_PORT = 12345;
    private static final String SERVER_ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            // Recibir la hora actual del servidor
            writer.println("127.0.0.3");
            long serverTime = Long.parseLong(reader.readLine());
            Date date = new Date(serverTime);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate = formatter.format(date);
            System.out.printf("Hora actual del servidor :  %s%n", formattedDate);

            // Obtener la hora actual del cliente
            long clientTime = System.currentTimeMillis();
            Date date1 = new Date(clientTime);
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate1 = formatter1.format(date1);
            System.out.printf("Hora actual del cliente: %s%n", formattedDate1);

            // Calcular la diferencia de tiempo entre el reloj del servidor y el reloj del cliente
            long timeDiff = serverTime - clientTime;
            System.out.printf("Diferencia de tiempo entre el servidor y el cliente: %d%n", timeDiff);

            // Sincronizar el reloj del cliente con los demás clientes
            long clientClock = clientTime + timeDiff;
            Date date3 = new Date(clientClock);
                SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate3 = formatter3.format(date3);
                
            System.out.printf("Reloj del cliente sincronizado : %s%n", formattedDate3);

            // Enviar el reloj del cliente al servidor
            writer.println(clientClock);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}