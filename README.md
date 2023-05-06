# sqs23
SQS lecture summer term 2023

# 1. Einführung und Ziele

ISBN-to-Title sucht für die empfangene ISBN den entsprechenden Titel heraus. 
Es wird entwickelt damit Nutzer leichter den Titel eines Buches finden können.

![Sequencediagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=C4S2BsFMAIBUAsQGdrOgQ2qCkBQv0BjYAewCdoBVJSM3AB3TNEJEYDthoApdAN3QBaAIL16uACbpg6AEboa0ACLS5CvAyYs26TtADiJEgHMo+arUEA+XgJFiAXPoCisQQCVIARwCukJMC47CTAMCR8tFQ0ZAA0tkKi9A4AkgDKAEIActAA7iBkEhg+KJTuADLQspAAXiaQ7LgqMvI0ADyC8fZJhPCQhADWDnBgkOCo7MqqLXjo4FywI2PIXOwgPVwgE0318uz9jVPqgtadiQ64p2LWhiZQQy5unr7+gTemkMc2-AmOC6HguFGij+o1QAXGk1C7F2+yaahon0uSRBAPqEgu3y6nwsZCGKKAA)
## 1.1 Aufgabenstellung

Das Programm verfügt über eine REST-Schnitstelle. An diese kann der Endnutzer eine 10- / 13-Stellige ISBN-Nummer abfragen.
Hierbei handelt es sich um eine GET-Schnitstelle. Dabei wird die ISBN der Aufgerufenen URL entnommen.
Anschließend wird zu der ISBN der dazugehörige Titel gesucht, entweder in der Datenbank oder bei Google.
Dabei wird zuerst überprüft ob die ISBN in der eigenen Datenbank gespeichert ist. 
Daher leiten sich folgende Fälle ab: 

    - 1. Fall: ISBN nicht gefunden
        - ISBN wird an externen REST-Service von google geschickt
        - Titel wird mit der 10- und 13-Stelligen ISBN in der Datenbank abgespeichert.
        - Titel wird an Endnutzer zurückgeschickt
    - 2. Fall: ISBN wird gefunden
        - Titel wird an Endnutzer zurückgeschickt

## 1.2 Qualitätsziele
| Quality Category | Quality | Description | Szenario |
| :-- | :-- | :-- | :-: |
| Usability | Leicht zu bedienen | Einfache Handhabung durch den Endverbraucher. |  |  
|  | Leicht zu verstehen | Die Anwendung sollte individuell verständlich sein, ohne dass sie vorher in einem langwierigen Prozess erlernt werden muss. |  |
| Performance | Genauigkeit | Der zurückgegebene Titel sollte auch zu der angegebenen ISBN gehören |  |
|  | Robustheit | Das System muss unter allen angegebenen Umgebungs- und Betriebsbedingungen zuverlässig funktionieren. |  |
| Maintainability & Support | Wartbarkeit | Das System soll durch eindeutige Fehlermeldungen leicht verständlich und wartbar werden. So sollte ein Benutzer bei einer Fehleingabe selbst auf den Fehler schließen können. |  |
|  | Überprüfung | Durch die Überprüfung der empfangenen Anfrage soll sichergestellt werden, dass nur korrekte Daten empfangen werden. | 10.2.1 |
| Security | Integrität | Durch das Einsetzen von HTTPS soll sichergestellt werden, dass die Antwort von dem System kommt. |  |



## 1.3 Stakeholder

| Role/Name | Contact | Expectations |
| --- | :-- | :-- |
| Endnutzer | / | Einfache Handhabung der REST-Schnittstelle zur Ermittlung des Titels anhand einer ISBN. |

# 9. Entwurfsentscheidungen

## 9.1 Übermittlung der ISBN
Die ISBN wird aus der Aufgerufenen URL entnommen.

## 9.2 Überprüfung der ISBN
Da es sich bei einer ISBN nur um 10 oder 13 Stellige Zahlenfolgen handelt. Soll überprüft werden ob die ISBN die korrekte länge hat.
Da eine ISBN nur aus Zahlen besteht, soll hier gleich überprüft werden ob es sich auch nur um Zahlen handelt.
## 9.3 Projektsprache
Das Projet wird in Java geschriegen, da hier das vorhandene Know-How verwendet werden kann. 
Außerdem wurden schon vorher REST-Schnitstellen in dieser Sprache umgesetzt.

## 9.4 Datenbank
Bei der Datenbank handelt es sich um eine Postgress Datenbank, da auch hier das Know-How vorhanden ist und sich diese leicht auf jedem System umsretzen lässt.

## 9.5 Virtualisierung
Die Anwendungen werden mithilfe von Docker virtualisiert. Dies ist auf jedem gängigen System vorhanden und durch das bereitstellen der Container online, können diese leicht auf neue Systeme verbreitet werden.


10. Qualitätsanforderungen

## 10.2 Quality Scenarios
| ID | Description |
| 10.2.1 | Ungültige ISBN-Nummern sollen erkannt und dem Nutzer aufezeigt werden, welcher Fehler vorliegt. |
