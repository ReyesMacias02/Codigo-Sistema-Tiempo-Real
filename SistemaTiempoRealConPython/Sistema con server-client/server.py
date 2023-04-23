
# ---- El uso de Hilos para este sistema es para manejar multiples conexiones de clientes en el servidor ----

# Importamos socket  para la comunicación de red y threading para la creación de hilos.
import socket
import threading

# Definir el tamaño máximo de los datos que se pueden recibir en bytes
MAX_DATA_SIZE = 1024

# Crear un socket para el servidor
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Asignar un puerto y una dirección IP al servidor
HOST = '127.0.0.1'
PORT = 8000

# Vincular el socket del servidor a la dirección IP y al puerto
server_socket.bind((HOST, PORT))

# Escuchar conexiones entrantes
server_socket.listen()

# Imprimir la dirección IP y el puerto del servidor
print(f'Servidor escuchando en {HOST}:{PORT}')

# Función para manejar una conexión de cliente.  
# Esta función toma dos argumentos: el socket del cliente y la dirección del cliente.
def handle_client(client_socket, address):
    # Usamos un bucle while para recibir y enviar datos hasta que la conexión se cierre.
    while True:
        try:
            # Recibir los datos del cliente
            data = client_socket.recv(MAX_DATA_SIZE)

            # Si no hay datos, cerrar la conexión
            if not data:
                print(f'Conexión cerrada por {address}')
                break

            # Imprimir los datos recibidos y la dirección del cliente
            print(f'Datos recibidos del cliente {address}: {data}')

            # Enviar una respuesta al cliente
            response = f'¡Hola cliente {address}!'
            client_socket.sendall(response.encode())

        except Exception as e:
            print(f'Error al manejar la conexión con {address}: {e}')
            break

    # Cerrar la conexión con el cliente
    client_socket.close()
#  Usamos un bucle while para aceptar conexiones entrantes. Cada vez que se acepta una conexión, 
#  creamos un nuevo hilo utilizando la función Thread()  pasando como 
#  argumentos el socket del cliente y la dirección del cliente.
while True:
    # Aceptar conexiones entrantes
    client_socket, address = server_socket.accept()
    print(f'Cliente conectado desde {address}')

    # Iniciar un nuevo hilo para manejar la conexión
    client_thread = threading.Thread(target=handle_client, args=(client_socket, address))
    # Comenzamos el hilo utilizando el método start()
    client_thread.start()
