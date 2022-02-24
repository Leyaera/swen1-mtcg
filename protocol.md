# SWEN1: MTCG Protcol

# Link to Git
https://github.com/Leyaera/swen1-mtcg

## Design Ideas

* Credits where credits are due: the base of the HTTP server structure and code has been taken from the HTTP server file provided in the SWEN1 Moodle Course. 

* The one "design" idea I think is worth writing about is my decision to implement the battle logic directly in the Card class. 
By using the "instantWinsAgainst()" method in the card class the game already determines instant wins or loses. 
Furthermore, I decided to also implement the second part of the battle logic concerning the effects of element types when spell cards are involved in the fight. The *damageAgainst()* method immediately calculates the damage of the card, depending on the element and card type.

### Unique Feature
I  implemented the *isImmune()* method (*Card.java*). This method, with a chance of 0,5% any card, grants complete immunity regardless of card type, element type or (calculated) damage and wins any fight in any constellation. 
In case BOTH are immune, it loses its effect and the normal battle logic is used.

## Lessons Learned
* being sick for 6 weeks and not feeling well in the following weeks is a killer.
* Huge DrawBack: DataBase. It just wouldn't work for days. 
  * Solution: if you have more than 1 hard drive, maybe just use the correct path to your database#
* Too many Interfaces spoil the project - well intended but often at the cost of the clarity of the project structure
* Abstract classes are fun bun not always useful. 
  * i.e. the Card.java class used to be an abstract class until it wasn't sensible anymore to work with an abstract class if you need to have the possibility to instantiate Objects which can only implement subclasses.
* Waiting for a much too expensive notebook which still hasn't arrived by now due to a component shortage is difficult when a course, even though said to not require a notebook, does HEAVILY rely on the possession of one.<br>ESPECIALLY when used programs and/or computers do not work in class.
* Docker is pain. Spent too much time trying to setup a database via docker until someone told me docker is not mandatory. 
* I would love to have implemented more unit tests, it is indeed a very fun, quick and creative way of testing code :)

## Unit Testing Decisions

Number of Unit tests: 21

### card/MonsterCardTest.java

* first baby steps in implementing unit tests
<br><br>

**testGetCardTypeMonster()** & **testGetCardTypeSpell()**<br>
checks if the cardType was assigned correctly when creating cards and is not null 

**testWinsAgainst...()**<br>
checks certain elements of the battle logic and if it has been correctly executed and special winning conditions are used

**testDamageAgainst...()**<br>
checks if special damage features (effectiveness of a card) and calculates the final damage against the opponent card

### card/UserTest.java

tests authorization both with correct AND incorrect passwords to make sure, the authorization for the *loginUser()* method works.

### card/UserServiceTest.java

* most imporatant tests beside authorization tests
* checks access to users:
  * *testGetUserByIdExistingId()*
  * *testGetUserByIdNonExistingId()*
  * *testGetUserByIExistingUsername()* 
  * *testGetUserByIdNonExistingUsername()*
* tests correct database entry/update:
  * *testAddUser()*
  * *testUpdateUser()*
  * *testDeleteUser()*

### NOT IMPLEMENTED

## - Methods partially written but never used and executed :|
* updates on user data
* specialities in battle-rounds between cards implemented in Card.java

## -- no code at all :(
* majority of battle logic due to an unlucky incident with a crashing computer and 2 weeks of spending waiting for the repair 
* trading
* score board
* profile page

## Time log

| Date | Tasks | Hours |
|------------|:--------------------|------:|
| 09/02/2022<br><br><br><br> | * README.md created<br>* protocol.md created<br>* feat: CardInterface, Card<br>* Git Repository created | 7,50<br><br><br><br> |
| 10/02/2022<br><br> | * feat: UserInterface, User<br>* UserServiceInterface, UserInterface | 7,00 <br><br> |
| 11/02/2022<br><br><br><br><br> | * db: created postgres DB with DataGrip<br>* server: integrated Server<br>* server: HTTP, Request, Response, RequestHandler<br>* curl test: user registration<br>* restructuring of project | 7,75<br><br><br><br><br> |
| 12/02/2022 | * solving database problems | 5,50 |
| 13/02/2022 | ! SICK |   |
| 14/02/2022 | ! SICK |   |
| 15/02/2022 | ! SICK |   |
| 16/02/2022 | ! SICK |   |
| 17/02/2022 | ! SICK |   |
| 18/02/2022<br><br> | * solving database problems<br>* working with static data (lists) | 7,00<br><br> |
| 19/02/2022 | * paid tutoring sessions (5 units, 180 €) | 5,25 |
| 20/02/2022<br><br> | * added password hashing<br>* added user status, user token | 4,25<br><br> |
| 21/02/2022 | * solved: DATABASE is working | 4,25 |
| 22/02/2022<br><br><br><br> |  * problem: git Repo (could not be solved)<br>* created new git repo<br>* finished: user registration<br>* finished: user login | 9,25 <br><br><br><br> |
| 23/02/2022<br><br> | * feat: cardpackage, carddeck, battle<br>* curl test: cardpackage, carddeck, battle | 9,25<br><br> |
| 24/02/2022<br><br> | * finishing protocoll<br>* finalized project, moodle upload  | 6,00<br><br> |
|   | **Gesamtzeit** | **73,00** |

