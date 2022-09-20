# testCSTI
El siguiente proyecto es un test, funciona con una base de datos embebida H2. Para levantar el aplicativo debe realizar los siguientes pasos:

Descargar o clonar el proyecto
Importar como proyecto maven en un IDE de Java (IntelliJ IDEA, Eclipse, etc)
Anticlik en la ruta principal del proyecto y luego Run As>Spring Boot App

POSTMAN
-------
Listar
------
GET: http://localhost:8081/api/v1/nota/listar

Registrar
---------
POST: http://localhost:8081/api/v1/nota/add

Body:

{
    "idCurso":1,
    "idUsuario":1,
    "ciclo":1,
    "nota1": "E",
    "nota2":11,
    "nota3":15,
    "nota4":17
}

Auth
----
POST: http://localhost:8081/api/v1/security/authenticate

Body:

{
    "username": "admin",
    "password": "admin"
}
