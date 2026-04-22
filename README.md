# Restaurant Order Management API

REST API per la gestione delle ordinazioni in un ristorante. Sistema backend production-ready sviluppato con Spring Boot e deployato su Render.

## 🎯 Panoramica

Questa API consente la gestione completa del flusso ordinazioni:
- Creazione e gestione tavoli
- Creazione ordini associati a tavoli specifici
- Aggiunta articoli agli ordini con calcolo automatico totale
- Chiusura ordini con validazione business logic
- Documentazione API tramite Swagger OpenAPI

## 🛠️ Tecnologie

- **Java 17+**
- **Spring Boot 3.2.5**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Validation
- **H2 Database** (in-memory, auto-ricreato ad ogni avvio)
- **Lombok** (riduzione boilerplate)
- **Springdoc OpenAPI** (Swagger UI integration)
- **Maven** (build tool)

## 📁 Struttura del Progetto

```
restaurant-order-api/
├── src/main/
│   ├── java/com/restaurant/api/
│   │   ├── RestaurantOrderApiApplication.java    # Entry point
│   │   ├── config/
│   │   │   ├── OpenApiConfig.java               # Swagger configuration
│   │   │   └── CorsConfig.java                  # CORS per frontend
│   │   ├── entity/
│   │   │   ├── RestaurantTable.java             # Entità tavolo
│   │   │   ├── Order.java                       # Entità ordine
│   │   │   ├── OrderItem.java                   # Entità item ordine
│   │   │   └── OrderStatus.java                 # Enum (OPEN, CLOSED)
│   │   ├── repository/
│   │   │   ├── TableRepository.java
│   │   │   ├── OrderRepository.java
│   │   │   └── OrderItemRepository.java
│   │   ├── service/
│   │   │   ├── TableService.java
│   │   │   ├── OrderService.java
│   │   │   └── OrderItemService.java
│   │   ├── controller/
│   │   │   ├── TableController.java
│   │   │   ├── OrderController.java
│   │   │   └── OrderItemController.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       ├── ErrorResponse.java
│   │       ├── ResourceNotFoundException.java
│   │       └── BusinessException.java
│   └── resources/
│       └── application.properties               # Configurazione Spring
├── pom.xml
├── Dockerfile                                   # Multi-stage build
├── .gitignore
└── README.md
```

## 🚀 Avvio Locale

### Prerequisiti
- Java 17+
- Maven 3.8+

### Passaggi

```bash
# Clone repository
git clone https://github.com/lmarino773/restaurant-order-api.git
cd restaurant-order-api

# Build
mvn clean package

# Avvia applicazione
mvn spring-boot:run
```

L'API sarà disponibile su `http://localhost:8080`

**Swagger UI:** http://localhost:8080/swagger-ui.html  
**H2 Console:** http://localhost:8080/h2-console  
(Username: `sa`, Password: lasciare vuoto)

## 📊 API Endpoints

### Tavoli
```
GET  /tables              - Elenco di tutti i tavoli
POST /tables              - Crea un nuovo tavolo
```

**Richiesta POST /tables:**
```json
{
  "number": 5,
  "seats": 4
}
```

### Ordini
```
POST   /orders            - Crea ordine per una tavola
GET    /orders/{id}       - Recupera ordine con dettagli
PUT    /orders/{id}/close - Chiude ordine (mark as CLOSED)
```

**Richiesta POST /orders:**
```json
{
  "tableId": 1
}
```

### Items Ordine
```
POST /orders/{id}/items   - Aggiunge articolo a ordine
GET  /orders/{id}/items   - Lista articoli di un ordine
```

**Richiesta POST /orders/{id}/items:**
```json
{
  "name": "Carbonara",
  "quantity": 2,
  "price": 12.50
}
```

## ⚙️ Business Logic

- ✅ **Validazione tavola:** Impossibile creare ordine senza tavola valida (404)
- ✅ **Ordine non vuoto:** Non si può chiudere ordine senza almeno un articolo (400)
- ✅ **Ordine non chiuso:** Impossibile aggiungere articoli a ordine già chiuso (400)
- ✅ **Calcolo totale:** Automatico in base a (quantità × prezzo) per articolo

## 🐛 Gestione Errori

Tutte le eccezioni vengono gestite tramite `GlobalExceptionHandler`:

- **404 Not Found:** Risorsa inesistente
- **400 Bad Request:** Violazione business logic o validazione
- **500 Internal Server Error:** Errore imprevisto

Risposta errore:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Order not found with id: 5",
  "timestamp": "2026-04-22T12:07:21"
}
```

## 🌐 CORS

La configurazione CORS è abilitata per consentire richieste da frontend su domini diversi:
- Origini: `*` (tutti i domini)
- Metodi: GET, POST, PUT, DELETE, OPTIONS
- Headers: tutti ammessi

Configurato tramite `@CrossOrigin` sui controller per massima compatibilità.

## 📦 Deploy su Render

### Configurazione Render

1. Crea account su [render.com](https://render.com)
2. Connetti repository GitHub
3. Crea **Web Service** (New → Web Service)
4. Seleziona il repository
5. **Runtime:** Docker
6. Render auto-rileva Dockerfile e deploya

### Variabili d'Ambiente

```properties
PORT=<impostato automaticamente da Render>
```

L'applicazione legge la porta via `${PORT:8080}` in `application.properties`.

### URL di Produzione

```
https://restaurantorderapirepo.onrender.com
```

**Swagger UI:** https://restaurantorderapirepo.onrender.com/swagger-ui.html

**Nota:** Render free tier ha sleep dopo 15 minuti di inattività. La prima richiesta dopo il risveglio impiega 30-60 secondi.

## 🔗 Repository Git

**GitHub:** https://github.com/lmarino773/restaurant-order-api

```bash
# Clone
git clone https://github.com/lmarino773/restaurant-order-api.git

# Crea branch feature
git checkout -b feature/nome-feature

# Commit e push
git add .
git commit -m "Descrizione delle modifiche"
git push origin feature/nome-feature
```

## 📋 Stack Tecnico

| Componente | Versione | Ruolo |
|---|---|---|
| Java | 17 | Linguaggio principale |
| Spring Boot | 3.2.5 | Framework web |
| H2 Database | Latest | Database in-memory |
| Maven | 3.8+ | Build automation |
| Lombok | Latest | Code generation |
| Swagger/OpenAPI | 2.3.0 | Documentazione API |

## 🧪 Testing

Per testare l'API:

1. **Localmente:** Usa Swagger UI su http://localhost:8080/swagger-ui.html
2. **In produzione:** Usa Swagger UI su https://restaurantorderapirepo.onrender.com/swagger-ui.html
3. **cURL:**
   ```bash
   curl https://restaurantorderapirepo.onrender.com/tables
   ```

## 📝 Note di Sviluppo

- Database H2 è in-memory con `create-drop`: ricreato ad ogni riavvio
- Per persistenza a lungo termine, migrare a MySQL/PostgreSQL
- Lombok richiede abilitazione annotation processor nell'IDE
- CORS abilitato per supportare frontend React su dominio diverso

## 👥 Autore

Sviluppato come progetto portfolio per dimostrare competenze in:
- REST API design
- Spring Boot best practices
- JPA/Hibernate ORM
- Exception handling e validazione
- Docker deployment

---

**Ultimo aggiornamento:** Aprile 2026  
**Versione API:** 1.0.0
