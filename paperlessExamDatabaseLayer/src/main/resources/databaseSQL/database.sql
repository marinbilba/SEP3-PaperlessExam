-- Table: users
CREATE TABLE users (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstName     VARCHAR(30),
  lastName VARCHAR(30),
  username VARCHAR(255) NOT NULL,
  email    VARCHAR(30) NOT NULL,
  password VARCHAR(255) NOT NULL,
  FK_RoleId INT NOT NULL ,
       foreign key (FK_RoleId) references roles(id) on update cascade on delete set null
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

INSERT INTO users VALUES (10,'Silvestru','Mandrila','silvmandrila','silvmandr@va.cs','111111',1);

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);