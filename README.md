# Servicio de Transacciones - Banco XYZ

Este repositorio contiene el servicio de microservicios para la gestión y registro del historial de transacciones bancarias de los clientes del banco XYZ. Las transacciones pueden incluir depósitos, retiros y transferencias entre cuentas, las cuales deben estar registradas y disponibles para su consulta.

---

## Descripción del Proyecto

El sistema de transacciones permite a los clientes realizar operaciones bancarias básicas, como depósitos, retiros y transferencias entre cuentas. El historial de todas las transacciones está disponible para consultas, y cada operación se guarda con detalles como el tipo de transacción, monto, fecha, y cuentas involucradas. Este microservicio es parte del ecosistema de servicios del banco XYZ, interactuando con otros microservicios para la gestión de clientes (8080) y cuentas (8081).

## Diagrama de Secuencia

![Diagrama de Secuencia](https://github.com/user-attachments/assets/791fd0c5-6ab6-450b-9038-2dd46bb0c8e8)

## Diagrama de Componentes

![Diagrama de Componentes](https://github.com/user-attachments/assets/69f05ef3-e389-492e-b082-2cf1399b8146)

## Detalles del Sistema

### Gestión de Transacciones:

El sistema registra las siguientes operaciones bancarias:

- **Depósito**: Se registra cuando un cliente deposita dinero en su cuenta.
- **Retiro**: Se registra cuando un cliente retira dinero de su cuenta.
- **Transferencia**: Se registra cuando un cliente transfiere dinero de una cuenta de origen a una cuenta de destino.

Cada transacción se almacena con los siguientes datos:
- **Tipo de Transacción**: Depósito, Retiro o Transferencia.
- **Monto**: Cantidad monetaria de la transacción.
- **Fecha**: Fecha y hora en que se realiza la transacción.
- **Cuentas**: Cuenta de origen y, en el caso de las transferencias, la cuenta de destino.

### Endpoints:

- **POST /transacciones/deposito**: Registra un depósito.
- **POST /transacciones/retiro**: Registra un retiro.
- **POST /transacciones/transferencia**: Registra una transferencia.
- **GET /transacciones/historial**: Consulta el historial de transacciones de un cliente.

---

## Requisitos del Sistema

Este servicio está configurado para correr en el puerto **8082** y se comunica con los otros dos microservicios:

- **Customer Service** (puerto **8080**): Gestiona la información de los clientes.
- **Account Service** (puerto **8081**): Gestiona las cuentas bancarias de los clientes.

A diferencia de los otros dos servicios, este sistema de transacciones es **no relacional**, lo que significa que utiliza una base de datos no relacional para almacenar el historial de transacciones.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **MongoDB** (Base de datos no relacional)
- **Rest API**
- **Maven** (para la gestión de dependencias)
- **Docker** (para contenedores, opcional)

---

## Instrucciones de Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/NttDataProyecto-NicollMalca/TransaccionMS.git

2. Asegurar de crear la base de datos : proyecto2, tal cual está en el archivo  application.properties
  

El servicio estará disponible en http://localhost:8082.


