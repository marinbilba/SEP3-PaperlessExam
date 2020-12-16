-- Table: users
CREATE TABLE users (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstName     VARCHAR(30),
  lastName VARCHAR(30),
  username VARCHAR(255) NOT NULL,
  email    VARCHAR(30) NOT NULL,
  password VARCHAR(255) NOT NULL,
  fk_role_id INT NOT NULL ,
       foreign key (fk_role_id) references roles(id) on update cascade on delete set null
);


-- Table: roles
CREATE TABLE roles (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);


-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
);

-- Insert data
USE paperlessexam;

INSERT INTO roles VALUES (1, 'Student');
INSERT INTO roles VALUES (2, 'Teacher');
INSERT INTO roles VALUES (3, 'Admin');

INSERT INTO users(id,firstName,lastName,username,email,password,fk_role_id) VALUES (10,'Silvestru','Mandrila','silvmandrila','silvmandr@va.cs','111111',1);
INSERT INTO users(id,firstName,lastName,username,email,password,fk_role_id) VALUES (12,'Bilba','Marin','marinbilba','marinbilba@gmail.com','111111',1);

-- On update cascade
-- Multiple choice set
alter table multiple_choice_set drop foreign key FKlnw4kp4xd6wp8om4w9rnlpj1h;
alter table multiple_choice_set add foreign key (fk_user_id) references users(id) on DELETE CASCADE;