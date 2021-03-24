Hogyan készül egy rádiós lejátszási toplista? Még szép, hogy adatbázis segítségével!
Lássuk, neked sikerül-e?

# Adatbázis

Az adatbázisban egyetlen tábla van `music_broadcast` néven, az alábbi oszlopokkal:

- `artist` varchar
- `song` varchar
- `times_aired` int

Az adatbázisba havonta kerül be, hogy egy-egy számot hányszor játszottak a rádiók. 
Az egyes hónapok adatai külön sorba kerülnek, így egy előadő egy száma többször is szerepelhet. Például:

| artist         | song             | times_aired |
|----------------|------------------|------------:|
| Chris Isaak    | Let Me Down Easy | 40          |
| Apey & the Pea | Nazareth         | 20          |
| Son Lux        | Ransom           | 20          |
| Radiohead      | You              | 5           |
| Son Lux        | Dream State      | 25          |
| Chris Isaak    | Let Me Down Easy | 20          |

*FONTOS:* Nem kell adatbázist elindítani vagy létrehozni. Az adatbázist a teszt esetek automatikusan létrehozzák ls feltöltik adatokkal, nektek csak kapcsolódni kell hozzá a megadott adatok alapján (DB_URL, USERNAME, PASSWORD) és lekérdezni belőle a szükséges adatokat

# Java alkalmazás

Implementáld a `RadioCharts` osztályt, ami konstruktor paraméterben megkapja az adatbázis eléréshez szükséges adatokat (url, user, password). Az osztályban hozd létre az alábbi metódusokat:

- `getMostPlayedSong()`: adja vissza annak a számnak a címét a nevét, amelyiket az évben a legtöbbször játszottak a rádiók.
- `getMostActiveArtist()`: adja vissza annak az előadónak a nevét, akinek a legtöbb száma szerepel az adatbázisban (ugyanaz a szám többször lejátszva egynek számít).

Ha nincsenek lejátszási adatok, vagy hiba történik, akkor adjon vissza mindkét metódus üres `String`-et.
Döntetlen esetén azt az előadót, vagy számot kell visszaadni, amelyik előbb került be az adatbázisba.

A számok és előadók ideiglenes tárolásához használhatod az előre készített `Song` és `Artist` osztályokat.
