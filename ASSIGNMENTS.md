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

### 9. Leden functionaliteit
Op dit moment zijn boeken en blu-rays voorzien van functionaliteit. Maar we weten nog niets over de leden. Zorg er voor dat `Leden` bestaan:
1. Administratie van NAW gegevens van leden
2. Administratie van maximum leenbare producten, met een standaard waarde voor alle leden
3. Standaard CRU (Crete, Read en Update) functionaliteit
4. Leden mogen nooit `verwijderd` worden - Zorg dat deze functionaliteit uitgeschakeld is
5. Leden kunnen wel `disabled` worden

### 10. Leden en producten koppelen
Op dit moment bestaat nog geen relatie tussen leden en geleende producten, maar dat is wel nodig.
Let op: producten moeten ook ingeleverd kunnen worden, bedenk een manier om dat netjes te registreren

### 11. Zorg voor een limiet op leenbare producten
Leden hebben een administratief limiet op eental leenbare producten, maar dat wordt nog niet gebruikt. Zorg er voor dat bij het uitlenen controles worden gedaan.

### 12. De bibliotheek gaat uitbreiden
De bibliotheek gaat meer soorten producten aanbieden. Voeg de volgende producten toe `Stripboek`, `Krant`, `Games` (Voor de Switch, Xbox en Playstation)

### 13. Meerdere bronnen voor films
De bibliotheek ziet in dat niet iedereen een Blu-ray speler heeft en er is veel vraag naar DVDs en digitale huur. 
