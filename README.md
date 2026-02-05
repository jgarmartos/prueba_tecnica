# Instrucciones para ejecutar el proyecto

Necesario tener instalado Java 21 y Maven.

Desde una terminal, nos posicionamos en el directorio raíz del proyecto y ejecutamos spring-boot:run.
O, desde el propio IDE, ejecutamos la clase PruebaApplication.

## Arquitectura

El proyecto sigue una arquitectura por capas.

Controller → Service → Model

- LoanController: Maneja las peticiones HTTP y devuelve las respuestas. Valida los datos de entrada con Bean Validation.
- LoansService: Contiene la lógica. Se encarga de realizar las operaciones y cumple con las reglas d evalidación.
- Modelos: Representan las estructuras de datos de la aplicación. En este caso LoanRequest y LoanStatus.
- DTOs: Separan lo que recibe la API de lo que se guarda realmente. Es decir, el usuario rellena ciertos datos pero luego la aplicación genera todos los demás.
- Exception Handler: Se encarga de manejar toda la gestión de errores.

### Decisiones técnicas

Para el almacenamiento he utilizado un HashMap ya que nos permite guardar registros en formato clave-valor, lo cual nos garantiza poder acceder a cada registro según su identificador.

Para las validaciones usé Bean Validation en los DTOs porque es lo más rápido y Lombok para poder generar Getters y Setters rápidamente.

Además, he globalizado el sistema de excepciones en la clase GlobalExceptionHandler, la cual se encarga de devolver las excepciones de la palicación con sus respectivos mensajes.

Las posibles excepciones son:
- Préstamo no encontrado: LoanNotFoundException
- Cambio de status inválido: InvalidStatusTransitionException
- Dato incorrecto a la hora de crear o modificar un nuevo préstamo: MethodArgumentNotValidException

## Mejoras futuras

- Persistencia real: Usar una base de datos real para lograr persistencia en la información.
- Paginación: Si hay muchos préstamos el GET /loans puede llegar a ser muy largo.
- Tests: Agregar tests unitarios para mantener la integridad de la aplicación.
- Logging: Añadir logs estructurados para debugear mejor.
- Documentación de API: Swagger/OpenAPI para que sea más fácil probar los endpoints.
- Validaciones: Elaborar un sistema de validaciones más complejo, teniendo en cuentra otro tipo de divisas y demás contenidos.
- Manejo de simultaneidad: Si dos usuarios cambian el estado del mismo préstamo a la vez, uno podría sobrescribir al otro. Habría que controlarlo mejor.

## Endpoints disponibles

- `GET /api/v1/loans` - Listar todos los préstamos
- `POST /api/v1/loans` - Crear un nuevo préstamo
- `GET /api/v1/loans/{id}` - Obtener préstamo por ID
- `PATCH /api/v1/loans/{id}/status` - Actualizar el estado de un préstamo

### Ejemplo de uso

Crear préstamo:
```bash
curl -X POST http://localhost:8080/api/v1/loans \
  -H "Content-Type: application/json" \
  -d '{
    "requesterName": "Juan García",
    "requestedAmount": 15000,
    "currency": "EUR",
    "identification": "12345678A"
  }'
```

Actualizar estado:
```bash
curl -X PATCH http://localhost:8080/api/v1/loans/1/status \
  -H "Content-Type: application/json" \
  -d '{"status": "APROBADA"}'
```