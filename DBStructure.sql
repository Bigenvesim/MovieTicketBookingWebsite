


CREATE TABLE MOVIE(
     Id int NOT NULL,
     Name VARCHAR(255), 
     Genre VARCHAR(255), 
     Duration VARCHAR(25),
     Description VARCHAR(512),  
     ImageFile VARCHAR(255), 
     VideoFile VARCHAR(255),
     Price DECIMAL(15,2),
     PRIMARY KEY (Id)
);

CREATE TABLE USER(
     Id CHAR(5) NOT NULL,
     Name VARCHAR(25), 
     Phone CHAR(10), 
     Address VARCHAR(30), 
     Email VARCHAR(30), 
     Password CHAR(8), 
     AppGroup CHAR(12), 
     PRIMARY KEY (Id)
);

