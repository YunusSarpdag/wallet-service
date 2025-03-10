# wallet-service
Digital wallet management system for corporate companies


- [About the Project](#about-project)
- [Beginning](#beginning)
- [Useage](#use)
- [Structures and Dependencies](#structures-dependencies)
- [Tests](#tests)

  ## About the Project
  Digital wallet management system for corporate companies

  ## Beginning
  Requires Java 17
  Maven

  ## Useage
  Clone the repository
  git clone https://github.com/YunusSarpdag/wallet-service
  cd wallet-service
  install maven
  mvn clean install
  mvn spring-boot:run

  if you have IDE you can run in main method

  ## Structures and Dependencies
  .
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── digital
│   │   │           └── wallet
│   │   │               └── wallet_service
│   │   │                   ├── DigitalWalletApiApplication.java
│   │   │                   ├── configuration
│   │   │                   │   ├── AppSecurtityConfig.java
│   │   │                   │   ├── DataInitializer.java
│   │   │                   │   └── aop
│   │   │                   │       └── RequestAuthAspect.java
│   │   │                   ├── controller
│   │   │                   │   ├── TransactionController.java
│   │   │                   │   └── WalletController.java
│   │   │                   ├── dto
│   │   │                   │   ├── ApproveOrRejectRequest.java
│   │   │                   │   ├── PaymentRequest.java
│   │   │                   │   ├── WalletDto.java
│   │   │                   │   └── WalletListRequest.java
│   │   │                   └── model
│   │   │                       ├── entity
│   │   │                       │   ├── BaseModel.java
│   │   │                       │   ├── Customer.java
│   │   │                       │   ├── Transaction.java
│   │   │                       │   ├── Wallet.java
│   │   │                       │   └── enums
│   │   │                       │       ├── Currency.java
│   │   │                       │       ├── OppositePartyType.java
│   │   │                       │       ├── TransactionStatus.java
│   │   │                       │       └── TransactionType.java
│   │   │                       ├── repository
│   │   │                       │   ├── CustomerRepository.java
│   │   │                       │   ├── TransactionRepository.java
│   │   │                       │   └── WalletRepository.java
│   │   │                       └── service
│   │   │                           ├── CustomerService.java
│   │   │                           ├── IbanGenerator.java
│   │   │                           ├── TransactionService.java
│   │   │                           └── WalletService.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── digital
│                   └── wallet
│                       └── wallet_service
│                           ├── DigitalWalletApiApplicationTests.java
│                           └── model
│                               └── service
│                                   ├── CustomerServiceTest.java
│                                   ├── IbanGeneratorTest.java
│                                   ├── TransactionServiceTest.java
│                                   └── WalletServiceTest.java
├── target
│   ├── classes
│   │   ├── application.properties
│   │   └── com
│   │       └── digital
│   │           └── wallet
│   │               └── wallet_service
│   │                   ├── DigitalWalletApiApplication.class
│   │                   ├── configuration
│   │                   │   ├── AppSecurtityConfig.class
│   │                   │   ├── DataInitializer.class
│   │                   │   └── aop
│   │                   │       └── RequestAuthAspect.class
│   │                   ├── controller
│   │                   │   ├── TransactionController.class
│   │                   │   └── WalletController.class
│   │                   ├── dto
│   │                   │   ├── ApproveOrRejectRequest.class
│   │                   │   ├── PaymentRequest.class
│   │                   │   ├── WalletDto.class
│   │                   │   └── WalletListRequest.class
│   │                   └── model
│   │                       ├── entity
│   │                       │   ├── BaseModel.class
│   │                       │   ├── Customer.class
│   │                       │   ├── Transaction.class
│   │                       │   ├── Wallet.class
│   │                       │   └── enums
│   │                       │       ├── Currency.class
│   │                       │       ├── OppositePartyType.class
│   │                       │       ├── TransactionStatus.class
│   │                       │       └── TransactionType.class
│   │                       ├── repository
│   │                       │   ├── CustomerRepository.class
│   │                       │   ├── TransactionRepository.class
│   │                       │   └── WalletRepository.class
│   │                       └── service
│   │                           ├── CustomerService.class
│   │                           ├── IbanGenerator.class
│   │                           ├── TransactionService.class
│   │                           └── WalletService.class
│   ├── generated-sources
│   │   └── annotations
│   ├── generated-test-sources
│   │   └── test-annotations
│   ├── maven-status
│   │   └── maven-compiler-plugin
│   │       ├── compile
│   │       │   └── default-compile
│   │       │       ├── createdFiles.lst
│   │       │       └── inputFiles.lst
│   │       └── testCompile
│   │           └── default-testCompile
│   │               ├── createdFiles.lst
│   │               └── inputFiles.lst
│   ├── surefire-reports
│   │   ├── TEST-com.digital.wallet.wallet_service.DigitalWalletApiApplicationTests.xml
│   │   └── com.digital.wallet.wallet_service.DigitalWalletApiApplicationTests.txt
│   └── test-classes
│       └── com
│           └── digital
│               └── wallet
│                   └── wallet_service
│                       ├── DigitalWalletApiApplicationTests.class
│                       └── model
│                           └── service
│                               ├── CustomerServiceTest.class
│                               ├── IbanGeneratorTest.class
│                               ├── TransactionServiceTest.class
│                               └── WalletServiceTest.class
└── testdb.mv.db

## Test
All unit test written
