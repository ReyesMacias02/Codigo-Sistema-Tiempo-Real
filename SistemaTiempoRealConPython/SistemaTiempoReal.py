import threading 
import time

def print_time(): # imprime la hora actual una vez por segundo
    while True:
        current_time = time.strftime("%H:%M:%S", time.localtime())
        print("La hora actual es:", current_time)
        time.sleep(1) # pausa la ejecución durante 1 sengundo

def print_numbers():
    for i in range(1, 11):
        print(i)
        time.sleep(0.5)

t1 = threading.Thread(target=print_time)
t2 = threading.Thread(target=print_numbers)

t1.start() # inicia la ejecución de cada hilo
t2.start()

t1.join() # utilizado para esperar que ambos hilos terminen de ejecutarse antes de finalizar el programa.
t2.join()