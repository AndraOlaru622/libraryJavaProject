CREATE TABLE `projectdb`.`books` (
        `idBook` INT NOT NULL AUTO_INCREMENT,
        `idAuthor` INT NOT NULL,
        `title` VARCHAR(45) NOT NULL,
        `nrCopies` INT NOT NULL,
        PRIMARY KEY (`idBook`));


        CREATE TABLE `projectdb`.`authors` (
        `idAuthor` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(45) NOT NULL,
        PRIMARY KEY (`idAuthor`));

        CREATE TABLE `projectdb`.`borrowings` (
        `idBorrowing` INT NOT NULL AUTO_INCREMENT,
        `idUser` INT NOT NULL,
        `idBook` INT NOT NULL,
        `borrowDate` VARCHAR(45) NOT NULL,
        PRIMARY KEY (`idBorrowing`));

        CREATE TABLE `projectdb`.`users` (
        `idUser` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(45) NOT NULL,
        `email` VARCHAR(45) NOT NULL,
        `address` VARCHAR(45) NOT NULL,
        PRIMARY KEY (`idUser`));

        CREATE TABLE `projectdb`.`audit` (
        `idAction` INT NOT NULL AUTO_INCREMENT,
        `table` VARCHAR(45) NOT NULL,
        `description` VARCHAR(45) NOT NULL,
        `actionDate` VARCHAR(45) NOT NULL,
        PRIMARY KEY (`idAction`));

