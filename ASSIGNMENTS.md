# Assignments
Below are the assignments for this bootcamp, ordered in increasing difficulty and logical application development.

### 1. Alle boeken ophalen
Pas `BookController.java` aan zodat de methode `getAll` een lijst terug geeft met alle boeken.

### 2. Een enkel boek ophalen
Pas `BookController.java` aan zodat de methode `getById` een enkel boek kan ophalen op basis van het technische ID

### 3. Boek toevoegen
Pas `BookController.java` aan zodat de methode `add` een boek kan toevoegen aan het inventaris.

### 4. Boek aanpassen
Pas `BookController.java` aan zodat de methode `update` een boek kan updaten.

### 5. Boek verwijderen
Pas `BookController.java` aan zodat de methode `delete` een boek kan verwijderen.

### 6. Boek uitlenen en retourneren
Pas `BookController.java` aan zodat de methodes `borrow` en `return` de `borrowed` status aanpassen.

### 7. Meer informatie bij een boek
Pas `Book.java` aan zodat een boek voorzien kan worden van een samenvatting (summary).

Pas `BookController.java` aan zodat de `update` methode ook een samenvatting kan toevoegen

### 8. Blu-ray functionaliteit
Zorg dat naast Boeken ook de mogelijkheid komt om Blu-rays te huren met identieke mogelijkheden.

##### Voor alle volgende opdrachten geld: Ze zijn gericht op boeken, bonus is hetzelfde toepassen op Blu-rays 
### 9. Boek bij uitlenen koppelen aan lid
Pas `Book.java` en `Member.java` aan zodat een koppeling ontstaat tussen lid en boek bij het uitlenen.<br>
We gaan voor nu een 1:1 koppeling maken, een boek kent een lid zodra deze wordt uitgeleend

Pas `BookController.java` aan zodat `borrow` en `return` een relatie leggen tussen lid en boek en ook weer verwijderen

### 10. Limiteer het maximum aantal te lenen boeken
Pas `BookController.java` aan zodat `borrow` eerst controlleerd of een maximum aantal van 3 geleende boeken is bereikt. 

### 11. Boek niet uitlenen als deze al uitgeleend is
Pas `BookController.java` zodanig aan dat het niet langer mogelijk is een geleend boek weer opnieuw uit te lenen.

### 12. Vervang de H2 database met een Postgres database
Pas de properties in `application.properties` aan zodat H2 vervangen wordt door PostgresSQL

Gegevens van postgres kan je vinden: ``TODO``

## Optional assignments
### 13. Store a history of borrowing for a book

### 14. Show the list of previously borrowed books

### 15. Integration tests to prove the limit of borrowings

### 16. Add books in a batch via CSV input

### 17. Decouple Entities by using DTO's
