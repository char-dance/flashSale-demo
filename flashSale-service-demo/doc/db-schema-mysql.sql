CREATE DATABASE flash_sale;

CREATE TABLE  IF NOT EXISTS t_campaign(
	id INTEGER UNSIGNED AUTO_INCREMENT,
	name VARCHAR(64) NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    status INTEGER,
    lastUpdTime timestamp,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE  IF NOT EXISTS t_campaign_item(
	id INTEGER UNSIGNED AUTO_INCREMENT,
    item_id VARCHAR(64) NOT NULL,
    campaign_id INTEGER,
    stock INTEGER,
    status INTEGER,
    lastUpdTime timestamp,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into t_campaign(name, start_time, end_time, status) values('The 11.11 campaign', '2020-11-11 00:00:00', '2020-11-11 00:05:00', 0);

insert into t_campaign_item(item_id, campaign_id, stock, status) values('1A2B3C4D5E6F', 1, 100, 0);