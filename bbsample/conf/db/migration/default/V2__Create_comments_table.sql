CREATE TABLE comments (
  id int not null primary key,
  body varchar(255) not null,
  topic_id int not null
);
