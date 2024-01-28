# flights-company
Java application with a 4-layered architecture: data access layer with its validation ([domain package](https://github.com/Iri25/apm-project-Iri25/tree/main/src/main/java/domain)), persistence layer ([repository package](https://github.com/Iri25/apm-project-Iri25/tree/main/src/main/java/repository)), business layer ([service package](https://github.com/Iri25/apm-project-Iri25/tree/main/src/main/java/service)), presentation layer ([controller package](https://github.com/Iri25/apm-project-Iri25/tree/main/src/main/java/controller)). The data can be saved in memory and in txt files ([data package](https://github.com/Iri25/apm-project-Iri25/tree/main/data)). The type of repository must be changed depending on the choice of data storage. The interaction with the user is done through a graphical interface (GUI), developed in JavaFX (the fxml files are found in [views package](https://github.com/Iri25/apm-project-Iri25/tree/main/src/main/resources/views)).

Key concepts are abstraction, encapsulation, inheritance, polymorphism, validations, exceptions, alerts, reading from files and storing in files.

Desktop application for a flights company with an intuitive graphical interface. The application allows:
1. Login
2. View flights
3. Filter flights by location and date
4. Buy a ticket
5. Automatic update of the number of seats available for a flight
6. Paged display of flights


