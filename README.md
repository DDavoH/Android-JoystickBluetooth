# Android-JoystickBluetooth
Joystick para controlar un: minisumo/microsumo/coche a control remoto por bluetooth

El programa envia una cadena de datos:
Ejemplo:
*0034800412#   

El valor[0]= * : El inicio de la cadena.
En donde el valor[1] de dicha cadena = 0 : Nos indica la dirección de la primera llanta izquierda. Varia entre 0 y 1.
valor[2]= valor variable entre 0 y 1 que indica si el vehiculo esta detenido(1) o en marcha(1).
valor[3],valor[4],valor[5] = Velocidad de la llanta izquierda, varia de 100 hasta 999.

En donde el valor[6] de dicha cadena = 0 : Nos indica la dirección de la primera llanta derecha. Varia entre 0 y 1.
valor[7]= valor variable entre 0 y 1 que indica si el vehiculo esta detenido(1) o en marcha(1).
valor[8],valor[9],valor[10] = Velocidad de la llanta derecha, varia de 100 hasta 999.


Los casos son los siguientes:
*0034800412#   : Vehiculo en marcha hacia adelante con velocidad de 348 en llanta izquierda y 412 en llanta derecha

*1110011100#   : Vehiculo detenido con velocidad de 100(el microcontrolador deberia estar programado para funcionar de 100 en adelante.)
