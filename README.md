# Real estate registry for buildings CRUD app

### Backend
- An in-memory H2 database for saving data

## Architecture
Project is based on layered architecture pattern.

* Controller - API endpoints
* Dao - Data access layer. Could be wrapped in to Repository layer in the future.
* Service - Bussines logic layer.
* Domain - DATA layer.

## Functionality
### API
* post `/api/buildings/{owner_id}/{address_id}` - creates a new building
* get `/api/buildings` - returns a list of all buildings
* get `/api/buildings/{building_id}` - returns a single building
* put `/api/buildings/{building_id}/{owner_id}/{address_id}` - updates a single building
* get  `/api/taxes/calculate/{owner_personal_code}` - calculates the total yearly real estate tax for all properties owned by particular owner

## Spring-boot
To start the app on default 8080 port
```mvn spring-boot:run```
### Swagger
```http://localhost:8080/swagger-ui/```
### H2
```http://localhost:8080/console```

## Usage guide
### Creation order
Address and owner must be created prior building creation.

### Tax calculation
Tax rates are persisted to database and must exist before calculation property tax.

## Future considerations
* Building entity should be renamed to "Property" e.g. 
Apartment is a real estate object but not a building.
* Application doesn't solve multi owner problem per property.
* Endpoints are not secured - Spring security could be used to provide such functionality.


