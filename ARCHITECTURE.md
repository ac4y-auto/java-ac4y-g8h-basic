# Architektura - java-ac4y-g8h-basic

## Attekintes

A G8H (GUID-to-HumanId) alap modul biztositja a GUID es az ember altal olvashato azonositok (HumanId) kozotti lekepezest. Template-alapu kategorializalassal es adatbazis perzisztenciaval.

## Szerkezet

```
src/main/java/ac4y/guid8humanid/
  domain/object/
    Ac4yG8H.java                    - Fo domain objektum (GUID, HumanId, Template mappeles)
    Ac4yG8HList.java                - G8H lista wrapper
    Ac4yGUIDList.java               - GUID lista wrapper
  domain/persistence/
    Ac4yG8HDBAdapter.java           - JDBC adatbazis adapter (CRUD muveletek)
    Ac4yG8HTemplateDBAdapter.java   - Template-specifikus DB muveletek
    Ac4yIdentificationDBAdapter.java - Identification nezet adapter
  service/
    Ac4yGUID8HumanIdService.java    - Magas szintu szolgaltatas JNDI kapcsolatkezessel
```

## Osztalyok

### Ac4yG8H
- Extends `Ac4yNoId`
- Mezok: GUID, humanId, publicHumanId, simpledHumanId, templateGUID, persistentId, templatePersistentId, deleted
- Konvertalas: `getAc4yIdentification()`, `getAc4yClass()`

### Ac4yG8HDBAdapter
- Extends `Ac4yDBAdapter`
- Insert, delete, get, set muveletek
- Lista lekerdezesek (getList, getInstanceList, getGUIDList)
- Letezik-e ellenorzesek (isExistByGUID, isExistByHumanId, isExistByHumanIds)

### Ac4yGUID8HumanIdService
- Extends `Ac4yServiceOnDB`
- JNDI-alapu kapcsolatkezeles (`getDBConnection()`)
- Minden muvelet sajat kapcsolatot nyit es zar

## Fuggetlensegek

- ac4y-class, ac4y-base4jsonandxml (domain alap osztalyok)
- ac4y-database-basic (Ac4yDBAdapter)
- ac4y-connection-pool (DBConnection)
- ac4y-service-domain (Ac4yServiceOnDB)

## Eredet

Az `IJAc4yG8HModule/Basic` modulbol kinyerve.
