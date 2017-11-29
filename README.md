# eCard Application

This application is built using Spring Boot framework to handle basic eCard Transactions like Load & Spend.

It has following package and classes:

    Interface to define different types of Cards
        _domain.base.ICard
        domain.PrePaidCard_
    
    Interface to define Commands
        _command.base.ICardCommand
        command.LoadCommand
        command.SpendCommand_
    
    Controller that will handle all the commands
        _command.CardController_
        
    Checked Exception for all Card related operations
        _exceptions.CarException_
        
    Main Startup Application
        _ECardApplication_
    
**Buiding the app**

    Normal Build and Install    
        ./mvnw clean install
    
    Build without Tests
        ./mvnw clean install -DskipTests=true
    
    Run the application (Though we don't have any specific features at runtime)
        ./mvnw clean install spring-boot:run -DskipTests=true