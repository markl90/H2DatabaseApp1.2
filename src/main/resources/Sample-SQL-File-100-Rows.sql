
CREATE TABLE IF NOT EXISTS user_details (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) AUTO_INCREMENT=10001 ;


INSERT INTO `user_details` (`user_id`, `username`, `first_name`, `last_name`) VALUES
(1, 'rogers63', 'david', 'john'),
(2, 'mike28', 'rogers', 'paul'),
(3, 'rivera92', 'david', 'john'),
(4, 'ross95', 'maria', 'sanders'),
(5, 'paul85', 'morris', 'miller'),;

