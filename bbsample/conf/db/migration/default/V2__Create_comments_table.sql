CREATE TABLE comments (
  id int not null primary key auto_increment,
  body varchar(255) not null,
  topic_id int not null
);
