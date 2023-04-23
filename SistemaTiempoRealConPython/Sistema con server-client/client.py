import socket
import threading
# Definir el tamaño máximo de los datos que se pueden recibir en bytes
MAX_DATA_SIZE = 1024

# Crear un socket para el cliente
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Asignar una dirección IP y un puerto al cliente
HOST = '127.0.0.1'
PORT = 8000

# Conectar el cliente al servidor
client_socket.connect((HOST, PORT))

# Función para enviar mensajes al servidor
def send_message():
    while True:
        # Leer el mensaje del usuario desde la consola
        message = input('Mensaje: ')

        # Enviar el mensaje al servidor
        client_socket.sendall(message.encode())

        # Esperar la respuesta del servidor
        response = client_socket.recv(MAX_DATA_SIZE)

        # Imprimir la respuesta recibida
        print(f'Respuesta del servidor: {response.decode()}')

# Iniciar un hilo para enviar mensajes
send_thread = threading.Thread(target=send_message)
send_thread.start()

# Esperar a que el hilo de enviar mensajes termine
send_thread.join()

# Cerrar la conexión con el servidor
client_socket.close()
