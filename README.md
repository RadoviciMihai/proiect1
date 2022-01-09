#Proiect Etapa 1 - Radovici Mihai

Structura claselor este o structura ierhica de continere asemanatoare cu cea din input

###Clasa FileIterator
    are doua campuri: 
        dir folderul testelor de intrare
        outDir folderul rezultatelor
    
    functia delete files sterge fisierele de iesire pentru a putea creea alte fisiere noi
    
    functia run itereaza prin fisierele de intrare creaza un fisier de iesire aferent si apeleaza functia runTest
    cu caiile absolute a acestor doua fisiere ca si parametrii
    
    functia runTest creaza o clasa de tip InputLoader
    apoi scrie rezultatul in fisierul de iesire aferent
    rezultatul este obtinut prin transformarea in string a unui obiec JSON returnat de functia run din database
    database este o proprietate a clasei InputLoader

###Clasa InputLoader

    Clasa InputLoader citeste textul din fisierul de input primit la constructor si il transforma intr-un obiect JSON
    foloseste acest obiect JSON pentru a construi baza de date

##Baza de date
toate clasele ce alcatuiesc baza de date pot fii create cu un parametru de tip JSONObject sau JSONArray
acesteea se creeaza unlele pe altele conform ierarhiei

### Ierarhia de creare a claselor

       +------------------------------------------+                                                                                        
       |DataBase                                  |                                                                                        
       |	(int) numberOfYears                      |                                                                                        
       |    (double) santaBudget                  |                                                                                        
       |    (InitialData) initialData             |                                                                                        
       |    (Lista de AnnualChange) annualChanges |                                                                                        
       +|-|---------------------------------------+                                                                                        
        | |+-------------------------------------+                                                                                         
        | ||InitialData                          |                                                                                         
        | -|	(Lista de Child) children        |                                                                                         
        |+--    (Lista de Gift) santaGiftsList   |                                                                                         
        || +-|-----------------------------------+                                                                                         
        ||   |                                                                                                                             
        ||   |+-------------------------------------------+                                                                                
        ||   || Child                                     |                                                                                
        ||    |     (int) id;                             |                                                                                
        ||    |     (String) lastName;                    |                                                                                
        ||    |     (String) firstName;                   |                                                                                
        ||    |     (int) age;                            |                                                                                
        ||    |     (Cities) city;                        |                                                                                
        ||    |     (double) niceScore;                   |                                                                                
        ||    |     (Lista de Category) giftsPreferences; |                                                                                
        ||    |     (Lista de Double) listaScoruri        ||                                                                               
        ||    +-------------------------------------------+|                                                                               
        ||     +-----------------------------+             |                                                                               
        ||     |Gift                         |             |                                                                               
        ||     |    (String) productName     |             |                                                                               
        ||     |    (double) price           |             |                                                                               
        |------|    (Category) category      ||            |                                                                               
        |      |                             ||            |                                                                               
        |      +-----------------------------+|            |                                                                               
        |                                     |            |                                                                               
        |                                     |            |                                                                               
        |                                     |            |                                                                               
        |                                     |            |                                                                               
        | +-----------------------------------|------+     |                                                                               
        | |AnnualChange                       |      |     |                                                                 
        | |	(double) newSantaBudget           |      |     |                                                                               
        ---    (Lista de Gift) newGifts ------+      |     |                                                                               
          |    (Lista de Child) newChildren ---------------+                                                                               
          |    (Lista de ChildUpdate) childrenUpdates|                                                                                     
          +------------------------------------------+        

##Executia
Clasa InitialData are:
- functia  ageAllChildren care incrementeaza carsta tuturor copiilor
- Functia getChildByIdcare primeste ca parametru un id si returneaza copilul cu idul aferent

Clasa Child are:
- functia update (care ajuta cu autoactualizare) primeste ca parametru un childUpdate
- functia getAverageScore care returneaza scorul mediu
- addToScoreList adauga un scor pe lista
- getNiceScoreHistory returneaza lista scorurileor sub format JSONArray
- getGiftsPreferencesJson returneaza lista categoriilor preferate sub format JSONArray

Clasa DataBase are:
- functia update care primeste ca parametru un an si updateaza baza de date cu anul respectiv
- functia getCheapestGift care primeste ca parametru o categorie si returneaza cel mai ieftin cadou din categoria respectiva
- getOutputChildren returneaza un obiect JSON ce reprezinta lista de copii eligibili din anul curent
- run itereaza anii, updateaza baza de date si construieste rezultatul final folosinduse de getOutputChildren

