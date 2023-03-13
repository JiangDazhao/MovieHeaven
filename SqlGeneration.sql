drop schema if exists movie_db;
create schema movie_db;
use movie_db;

DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS review;



create table movie(
                      movie_id int auto_increment
                          primary key,
                      user_id int null,
                      review_count int null,
                      title    varchar(45) null,
                      genre    varchar(45) null,
                      year     year        null,
                      info     text        null,
                      rating   float       null,
                      post_time datetime    null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `movie` (`movie_id`,`user_id`,`review_count`,`title`, `genre`, `year`, `info`, `rating`,`post_time`)
VALUES
    (1,1,9,'The Shawshank Redemption','Drama','1994','Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',4.5,'2023-02-25 00:00:00'),
    (2,1,1,'The Godfather','Crime, Drama','1972','An organized crime dynasty\'s aging patriarch transfers control of his clandestine empire to his reluctant son.',NULL,'2023-02-25 00:00:00'),
    (3,1,0,'The Dark Knight','Action, Crime, Drama','2008','When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.',NULL,'2023-02-25 00:00:00'),
    (4,1,0,'The Godfather: Part II','Crime, Drama','1974','The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.',NULL,'2023-02-25 00:00:00'),
    (5,1,0,'12 Angry Men','Crime, Drama','1957','A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.',NULL,'2023-02-25 00:00:00'),
    (6,1,0,'The Lord of the Rings: The Return of the King','Action, Adventure, Drama','2003','Gandalf and Aragorn lead the World of Men against Sauron\'s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.',NULL,'2023-02-25 00:00:00'),
    (7,1,0,'Pulp Fiction','Crime, Drama','1994','The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.',NULL,'2023-02-25 00:00:00'),
    (8,1,0,'Schindler\'s List','Biography, Drama, History','1993','In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.',NULL,'2023-02-25 00:00:00'),
    (9,1,0,'Inception','Action, Adventure, Sci-Fi','2010','A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.',NULL,'2023-02-25 00:00:00'),
    (10,1,0,'Fight Club','Drama','1999','An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.',NULL,'2023-02-25 00:00:00'),
    (11,1,0,'Fight Club','Drama','1999','An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.',NULL,'2023-02-25 00:00:00'),
    (12,1,0,'Fight Club','Drama','1999','An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.',NULL,'2023-02-25 00:00:00');

create table review(
                       review_id      int auto_increment
                           primary key,
                       movie_id       int         null,
                       user_id        int         null,
                       stars          int         null,
                       review_content varchar(200) null,
                       review_time    datetime    null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `review` (`review_id`, `movie_id`, `user_id`, `stars`, `review_content`, `review_time`)
VALUES
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
    (null,2,1,4,'good, 4 star','2023-02-25 00:00:01'),
    (null,1,2,4,'asdf','2023-02-25 15:10:24');

create table user(
                     user_id    int auto_increment
                         primary key,
                     user_name  varchar(45)  null,
                     password   varchar(45)  null,
                     salt       varchar(50)  null,
                     header_url varchar(500) null
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user VALUES
                     (1,'jiangxuzhao','4ff5a4ebfbecf1e91acf2735f635b861','f30dd','https://avatars.githubusercontent.com/u/56500172?v=4'),
                     (2,'wuxiang','4ff5a4ebfbecf1e91acf2735f635b861','f30dd','http://images.nowcoder.com/head/674t.png'),
                     (3,'guoxiaoyan','4ff5a4ebfbecf1e91acf2735f635b861','f30dd','https://github.com/Tangent617/Tangent617.github.io/blob/main/images/avatar.png?raw=true');