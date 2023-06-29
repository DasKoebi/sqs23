# sqs23
SQS Vorlesung Sommersemester 2023

Bei diesem Projekt handelt es sich um ein Vorlesungsprojekt der Veranstaltung Software Qualitätssicherung der Technischen Hochschule Rosenheim.

# ISO-to-Name

Bei ISO-to-Name handelt es sich um Programm Mithilfe es möglich ist, den Buchnamen zu einer ISBN herauszufinden.

Mithilfe dieses Architekturüberblicks sollen die maßgeblichen Entwurfsentscheidungen nachvollziehbar werden.
Es werden die Strukturen der Lösungen sowie das Zusammenspiel zentraler Elemente aufgezeigt.
Die Gliederung der Inhalte erfolgt nach der ARC42-Vorlage.

# 1. Einführung und Ziele

Dieser Abschnitt soll in die Aufgabenstellung einführen. 
Zusätzlich werden die Ziele skizziert, welche ISO-to-Name verfolgt.

## 1.1 Aufgabenstellung

### Was ist ISO-to-Name

- Ein voll funktionsfähiges Programm
- Aufgabe ist das Herausfinden des Buchnamens zu der dazugehörigen ISBN
- Hierfür wird eine externe API (Google-API) befragt.
- Aus Performancegründen wird das Ergebnis in einer eigenen Datenbank zwischengespeichert.

### Wesentliche Features:

    - Handhabung von 10- und 13-stelligen ISBN-Nummern
    - Überprüfen, ob es sich um eine gültige ISBN-Nummer handelt.
    - Cachen der Antwort

## 1.2 Qualitätsziele
| Quality Category          | Quality             | Description                                                                                                                                                                   | Szenario |
|:--------------------------|:--------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------:|
| Usability                 | Leicht zu bedienen  | Der Endbenutzer soll mithilfe einer integrierten Webseite die Möglichkeit haben, seine ISBN nummer einzugeben, ohne dafür direkt mit der API zu kommunizieren.                                                                                                                         |          |  
|                           | Leicht zu verstehen | Die Anwendung sollte individuell verständlich sein, ohne dass sie vorher in einem langwierigen Prozess erlernt werden muss.                                                   |          |
| Performance               | Genauigkeit         | Der zurückgegebene Titel sollte auch zu der angegebenen ISBN gehören                                                                                                          |          |
|                           | Robustheit          | Das System muss unter allen angegebenen Umgebungs- und Betriebsbedingungen zuverlässig funktionieren.                                                                         |          |
| Maintainability & Support | Wartbarkeit         | Das System soll durch eindeutige Fehlermeldungen leicht verständlich und wartbar werden. So sollte ein Benutzer bei einer Fehleingabe selbst auf den Fehler schließen können. |          |
|                           | Überprüfung         | Durch die Überprüfung der empfangenen Anfrage soll sichergestellt werden, dass nur korrekte Daten empfangen werden.                                                           |  10.2.1  |
| Security                  | Integrität          | Für die Kommunikation der API-Schnitstelle von Google wird ein Token benötigt. Dieses wird mithilfe einer ENV-Variable eingelesen, damit dieses individuell und nicht statisch ist.                                                                              |          |



## 1.3 Stakeholder

| Role/Name | Contact | Expectations                                                                            |
|-----------|:--------|:----------------------------------------------------------------------------------------|
| Endnutzer | /       | Einfache Handhabung der REST-Schnittstelle zur Ermittlung des Titels anhand einer ISBN. |

# 2. Randbedingungen
Für den Lösungsentwurf mussten zu Beginn verschiedene Randbedingungen beachtet werden. 
Mithilfe dieses Abschnitts sollen diese erläutert werden und wenn nötig auch die dazugehörige Motivation.

## 2.1 Technische Randbedingungen

| Randbedingung           | Erläuterung, Hintergrund                                                                                                                                                                                                                           |
|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Virtualisierung         | Mithilfe von Docker soll es möglich sein, die Software auf verschiedenen Subsystemen auszuführen. Docker ist hierbei für die Verwendung kostenlos, wodurch auch Endverbraucher ohne Kosten die Software verwenden können.                          |
| Implementierung in Java | In der Hochschule wurde hauptsächlich Java gelehrt und auch bei Projekten verwendet. Daher ist hier eine Grunderfahrung vorhanden.                                                                                                                 |
| Postgresql Datenbank    | Da die Daten in einer Datenbank zwischengespeichert werden, wurde hierfür Postgres verwendet. Hierbei handelt es sich um ein freies, objektrelationales Datenbankmanagementsystem. Auch hier sind durch vorherige Projekte Erfahrungen vorhanden. |

## 2.2 Organisatorische Randbedingungen

| Randbedingung                          | Erläuterung, Hintergrund                                                                                                                                                                                      |
|----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Team                                   | Lukas Kolb, unterstützt durch die Vorlesung.                                                                                                                                                                  |
| Zeitplan                               | Beginn der Entwicklung war April 2023, erster lauffähiger Prototyp war Mai 2023. Fertistellung mit sämtlichen Tests war im Juni 2023.                                                                           |
| Vorgehensmodell                        | Entwicklung risikogetrieben, iterativ und inkrementell. Zur Dokumentation der Architektur kommt arc42 zum Einsatz. Eine Architekturdokumentation, gegliedert nach dieser Vorlage ist zentrales,Projektergebnis |
| Entwicklungswerkzeuge                  | Erstellung der Java-Quelltexte in IntelliJ. Die Software muss allerdings auch alleine mit Gradle, ohne IDE baubar sein, damit diese später in einem Dockercontainer laufen kann.                              |
| Konfigurations- und Versionsverwaltung | Github                                                                                                                                                                                                        |
| Testwerkzeuge und -prozesse            | JUnit im Annotationsstil sowohl für inhaltliche Richtigkeit als auch, für Integrationstests und die Einhaltung von Effizienzvorgaben. K6 für Lasttest. Endtests werden mithilfe von Cypress durchgeführt.      |
| Veröffentlichung als Open Source       | Die Quelltexte der Lösung oder zumindest Teile werden als Open Source,verfügbar gemacht. Lizenz: GNU General Public License version 3.0,(GPLv3). Gehostet bei GitHub: https://github.com/DasKoebi/sqs23       |

## 2.3 Konventionen

| Randbedingung                | Erläuterung, Hintergrund                                                                    |
|------------------------------|---------------------------------------------------------------------------------------------|
| Architekturdokumentation     | Terminologie und Gliederung nach dem deutschen arc42-Template in der Version 6.0            |
| Kodierrichtlinien für Java   | Java Coding Conventions von IntelliJ geprüft.                                               |
| Kodierrichtlinien für Docker | Docker empfehlungen mithilfe von Hadolint (https://hadolint.github.io/hadolint/) überprüft. |

# 3. Kontextabgrenzung

Mithilfe dieses Abschnittes wird das Umfeld beschrieben. Hier wird beschrieben, für welchen Nutzer das System ist und mit welchen Fremdsystemen es interagiert.

## 3.1 Fachlicher Kontext

### Benutzer
Der Benutzer kann mit dem System interagieren.

### Datenbank

Bei der Abfrage von Daten wird zuerst in einer Datenbank überprüft, ob die Daten vorhanden sind.

### Google API

Sollten die Daten nicht vorhanden sein, werden diese bei der Google API abgefragt.

## 3.2 Technischer- oder Verteilungskontext

### Frontend

Mithilfe des Frontends kann der Benutzer über eine grafische Oberfläche mit dem System interagieren. 

# 4. Lösungsstrategie
Hier werden die wichtigsten Ziele und Lösungsansätze Gegenübergestellt.

## 4.1 Einstieg in die Lösungsstrategie

| Analysierbarkeit  | - Architekturüberblick gegliedert nach arc42 - Explizites, objektorientiertes Domänenmodell |
|-------------------|---------------------------------------------------------------------------------------------|
| Änderbarkeit      | - verbreitete Programmiersprache Java, - Hohe Testabdeckung als Sicherheitsnetz             |
| Interoperabilität | - Verwendung des verbreiteten Kommunikationsprotokolls http,  Einsatz des portablen Java    |
| Portabilität      | - Einsatz durch Docker (b)                                                                  |

## 4.2 Der Aufbau von ISBN-to-Name

ISBN-to-Name ist als Java-Programm mit Springboot realisiert. 
Grob lässt es sich in folgende Teile aufteilen:

- eine Implementierung der HTTP-GET-Schnittstelle
- einen Kommunikationsservice mit der Datenbank
- einen Kommunikationsservice mit der Google API
- einen Service zur überprüfung der ISBN

Mithilfe dieser Zerlegung ist es möglich, dinge wie die Kommunikation zu Google API auszutauschen. 
Ale Teile sind durch Schnittstellen abstrahiert. Die Zerlegung ermöglicht es weiterhin, die Software leicht automatisch zu testen.

## 4.3 Anbindung

ISBN-to-Name besitzt ein integriertes Frontend. Welches es den Benutzer ermöglicht, Anfragen nicht nur über eine HTTP-Get anfrage zu stellen, sondern diese bequem im Browser durchzuführen. 

# 5. Bausteinsicht

Dieser Abschnitt beschreibt die Zerlegung von ISBN-to-Name in Module, wie sie sich auch in der Paketstruktur des Java-Quelltextes widerspiegelt.

# 6. Laufzeitsicht

Diese Sicht visualisiert im Gegensatz zur statischen Bausteinsicht dynamische Aspekte.

## 6.1 Walkthrough
![Sequenzdiagramm](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=C4S2BsFMAIBUAsQGdrOgQ2qCkBQv0BjYAewCdoBVJSM3AB3TNEJEYDthoApdAN3QBaAIL16uACbpg6AEboa0ACLS5CvAyYs26TtADiJEgHMo+arUEA+XgJFiAXPoCisQQCVIARwCukJMC47CTAMCR8tFQ0ZAA0tkKi9A4AkgDKAEIActAA7iBkEhg+KJTuADLQspAAXiaQ7LgqMvI0ADyC8fZJhPCQhADWDnBgkOCo7MqqLXjo4FywI2PIXOwgPVwgE0318uz9jVPqgtadiQ64p2LWhiZQQy5unr7+gTemkMc2-AmOC6HguFGij+o1QAXGk1C7F2+yaahon0uSRBAPqEgu3y6nwsZCGKKAA)

Zunächst übermittelt der User mithilfe der HTTP-GET-Request eine ISBN an das System. 
Diese ISBN wird aus der URL abgeleitet.
Anschließend wird innerhalb des Systems überprüft, ob die ISBN in der Datenbank hinterlegt ist, sollte dies der Fall sein, werden die Informationen zurückgegeben.
Ansonst wird die Google API befragt, die erhaltenen Daten (ISBN-10, ISBN-13, Name) in der Datenbank gespeichert und der Name zurückgegeben.

# 7. Verteilungsschicht

Diese Schicht beschreibt den Betrieb von ISBN-to-Name.
Dadurch das dieses Programm als Dockercontainer zur verfügung steht, ist dies recht anspruchslos.

## 7.1 Infrastruktur

Software-Voraussetzungen auf dem PC:
 - Docker

Zusätzliche Voraussetzungen:
 - Google-API key


Damit die Anwendung gestartet werden kann, ist eine Docker-Compose Datei beigelegt.
Diese dient als Vorlage, wobei hier noch die Variable `API_KEY` angepasst werden muss.
Zusätzlich sollte für das Datenbankpasswort ein anderer Wert vergeben werden. 

# 8. Querschnittliche Konzepte

Dieser Abschnitt beschreibt allgemeine Strukturen und Aspekte, die systemweit gelten.

## 8.1 ISBN-Domänenmodell

Die ISBN welche von der Google API zurückgegen wird wird als JSON ausgegeben.
Zur Modelierung im System wird ein Klassenmodell wie folgt verwendet:

# 9. Entscheidungen

Hier sollen gewisse Entscheidungen nachfollzogen werden können.

## 9.1 Übermittlung der ISBN
Die ISBN wird anhand der aufgerufenen URL abgeleitet.
Der Aufbau ist exemplarisch: `https://www.example.com/v1/books/${ISBN}`

## 9.2 Projektsprache
Das Projekt wird in Java geschrieben, da hier das vorhandene Know-How verwendet werden kann. 
Außerdem wurden schon vor diesem Projekt, REST-Schnittstellen in dieser Sprache intern verwirklicht.

## 9.3 Datenbank
Bei der Datenbank handelt es sich um eine Postgres Datenbank, da auch hier das Know-How vorhanden ist und sich diese leicht auf jedem System realisieren lässt.

## 9.4 Docker

Docker wurde unteranderm deswegen verwendet, da es kostenlos verwendet werden kann.
Durch diese Virtualisierung kann auf allen gängigen Systemen wie Windows, Linux und Mac das Programm verwendet werden.

Außerdem ist es mithilfe der CI-Pipline möglich, das Programm automatisch neu zu bauen und als Container online zur verfügung zu stellen. 
Somit kann sehr schnell und einfach immer die neuste Version verwendet werden.

# 10. Qualitätsanforderungen

Dieser Abschnitt beinhaltet konkrete Qualitätsszenarien.

## 10.1

## 10.2 Quality Scenarios
| ID | Description |
| 10.2.1 | Ungültige ISBN-Nummern sollen erkannt und dem Nutzer aufzeigt werden, welcher Fehler vorliegt. |

# 11 Risiken

## 11.1 Flasche Interpretation der ISBN-Nummer

Sollte die ISBN-Nummer nicht korrekt aus der URL des GET-Request interpretiert werden, so schlägt die ganze Anwendung fehl.

## Eventualfallplanung

Es wäre möglich von einer GET-Request auf eine POST-Request umzubauen, bei dieser wird die ISBN in einem JSON-Boddy mitgeschickt.
Dieser kann wiederumm vallidiert werden, was allerdings einen großen mehraufwand bedeuten würde.

## Risikominderung

Durch frühe und vermehrte Test kann hier Sicherheit geboten werden. 


# 13 

## Env-Variablen
| Name       | Beschreibung                                                                                     | Beispiel                                              | Standardwert                                          |
|------------|--------------------------------------------------------------------------------------------------|-------------------------------------------------------|-------------------------------------------------------|
| `API_KEY`  | Der API-Key für die Google-Datenbank. Dieser wird benötigt, um Abfragen bei Google zu ermöglichen | `A791023AKC843123`                                    | Keinen                                                |
| `BASE_URI` | Die Base URI der Google-API, an diese werden die ISBN-Nummern gesendet.                          | `https://www.googleapis.com/books/v1/volumes?q=isbn:` | `https://www.googleapis.com/books/v1/volumes?q=isbn:` |
| `SPRING_DATASOURCE_URL` | Hierbei handelt es sich um die URL, welche die Anwendung benötigt, um sich mit der Datenbank zu verbinden. | `jdbc:postgresql://db:5432/postgres` | `jdbc:postgresql://host.docker.internal:5432/postgres` | 
| `SPRING_DATASOURCE_USERNAME` | Hierbei handelt es sich um den Benutzernamen des Datenbankbenutzers | `postgres` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Hierbei handelt es sich um das Passwort des Datenbankbenutzers | `changeMe!` | Keinen |