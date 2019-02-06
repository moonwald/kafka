#
# Create the SBPC Database Schema
#

# Create the database
CREATE DATABASE sbpc DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

# Create the users and assign privileges
CREATE USER 'sbpc'@'localhost' IDENTIFIED BY 'sbpc';
CREATE USER 'sbpc'@'%' IDENTIFIED BY 'sbpc';
GRANT ALL PRIVILEGES ON sbpc.* TO 'sbpc'@'localhost';
GRANT ALL PRIVILEGES ON sbpc.* TO 'sbpc'@'%';
FLUSH PRIVILEGES;

USE sbpc;


#
# Create the SBPC tables
#

CREATE TABLE connectors (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  enabled tinyint(1) NOT NULL,
  event_contributor tinyint(1) NOT NULL,
  offer_management tinyint(1) NOT NULL,
  received_heartbeat datetime DEFAULT NULL,
  push_source_data tinyint(1) NOT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_name (name),
  INDEX idx_name USING HASH(name),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE betting_platforms (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  connector bigint(20) NOT NULL,
  name varchar(100) NOT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_connector_name (connector,name),
  CONSTRAINT fk_betting_platforms_connector FOREIGN KEY (connector) REFERENCES connectors (id),
  INDEX idx_connector_name USING HASH(connector,name),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE accounts (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  connector bigint(20) NOT NULL,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  currency varchar(3) NOT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  balance decimal(19,8) DEFAULT NULL,
  available_amount decimal(19,8) DEFAULT NULL,  
  PRIMARY KEY (id),
  UNIQUE KEY uq_connector_username (connector,username),
  CONSTRAINT fk_accounts_connectors FOREIGN KEY (connector) REFERENCES connectors (id),
  INDEX idx_connector_username USING HASH(connector,username),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE account_properties (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  account bigint(20) DEFAULT NULL,
  name varchar(50) NOT NULL,
  value varchar(50) NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_account_name (account,name),
  CONSTRAINT fk_account_properties_accounts FOREIGN KEY (account) REFERENCES accounts (id),
  INDEX idx_account_name USING HASH(account,name),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE meta_tags (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  type varchar(255) DEFAULT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name,type),
  INDEX idx_name USING HASH(name),
  INDEX idx_name_type USING HASH(name,type),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE participants (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  type varchar(10) NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_name USING HASH(name),
  INDEX idx_name_type USING HASH(name,type),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE name_variants (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  variant varchar(255) NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_variant USING HASH(variant),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE participants_meta_tags (
  participant bigint(20) NOT NULL,
  meta_tag bigint(20) NOT NULL,
  PRIMARY KEY (participant,meta_tag),
  UNIQUE KEY uq_participant_meta_tag (participant,meta_tag),
  CONSTRAINT fk_participants_meta_tags_meta_tag FOREIGN KEY (meta_tag) REFERENCES meta_tags (id),
  CONSTRAINT fk_participants_meta_tags_participant FOREIGN KEY (participant) REFERENCES participants (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE participants_name_variants (
  participant bigint(20) NOT NULL,
  name_variant bigint(20) NOT NULL,
  PRIMARY KEY (participant,name_variant),
  UNIQUE KEY uq_participant_name_variant (participant,name_variant),
  UNIQUE KEY uq_name_variant (name_variant),
  CONSTRAINT fk_participants_name_variants_participant FOREIGN KEY (participant) REFERENCES participants (id),
  CONSTRAINT fk_participants_name_variants_name_variant FOREIGN KEY (name_variant) REFERENCES name_variants (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE events (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  start_time datetime NOT NULL,
  status varchar(20) DEFAULT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_start_time USING BTREE(start_time ASC),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE events_meta_tags (
  event bigint(20) NOT NULL,
  meta_tag bigint(20) NOT NULL,
  PRIMARY KEY (event,meta_tag),
  UNIQUE KEY uq_event_meta_tag (event,meta_tag),
  CONSTRAINT fk_events_meta_tags_meta_tag FOREIGN KEY (meta_tag) REFERENCES meta_tags (id),
  CONSTRAINT fk_events_meta_tags_event FOREIGN KEY (event) REFERENCES events (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE events_name_variants (
  event bigint(20) NOT NULL,
  name_variant bigint(20) NOT NULL,
  PRIMARY KEY (event,name_variant),
  UNIQUE KEY uq_name_variant (name_variant),
  UNIQUE KEY uq_event_name_variant (event,name_variant),
  CONSTRAINT fk_events_name_variants_name_variant FOREIGN KEY (name_variant) REFERENCES name_variants (id),
  CONSTRAINT fk_events_name_variants_event FOREIGN KEY (event) REFERENCES events (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE source_events (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  event bigint(20) DEFAULT NULL,
  connector bigint(20) NOT NULL,
  source_id varchar(100) NOT NULL,
  source_name varchar(255) NOT NULL,
  creator tinyint(1) NOT NULL,
  status varchar(20) DEFAULT NULL,
  start_time datetime NOT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_connector_source_id_event (connector,source_id,event),
  CONSTRAINT fk_source_events_connector FOREIGN KEY (connector) REFERENCES connectors (id),
  CONSTRAINT fk_source_events_event FOREIGN KEY (event) REFERENCES events (id) ON DELETE CASCADE,
  INDEX idx_connector_source_id USING HASH(connector,source_id),
  INDEX idx_connector_last_fetch_time USING BTREE(connector,last_fetch_time),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE source_events_meta_tags (
  source_event bigint(20) NOT NULL,
  meta_tag bigint(20) NOT NULL,
  PRIMARY KEY (source_event,meta_tag),
  UNIQUE KEY uk_source_event_tag (source_event,meta_tag),
  CONSTRAINT fk_source_events_meta_tags_meta_tag FOREIGN KEY (meta_tag) REFERENCES meta_tags (id),
  CONSTRAINT fk_source_events_meta_tags_source_event FOREIGN KEY (source_event) REFERENCES source_events (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE markets (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  event bigint(20) NOT NULL,
  name varchar(255) NOT NULL,
  type varchar(25) NOT NULL,
  handicap double DEFAULT NULL,
  period varchar(30) NOT NULL,
  status varchar(20) DEFAULT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_markets_event FOREIGN KEY (event) REFERENCES events (id) ON DELETE CASCADE,
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_event_type_handicap_period USING HASH(event,type,handicap,period),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE interested_clients (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  version bigint(20) DEFAULT NULL,
  client_token varchar(255) NOT NULL,
  account bigint(20) DEFAULT NULL,
  market bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_account_market_client (account, market, client_token),
  CONSTRAINT fk_interested_clients_market FOREIGN KEY (market) REFERENCES markets (id) ON DELETE CASCADE,
  CONSTRAINT fk_interested_clients_account FOREIGN KEY (account) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE source_markets (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  market bigint(20) DEFAULT NULL,
  connector bigint(20) NOT NULL,
  source_event bigint(20) NOT NULL,
  source_id varchar(100) NOT NULL,
  source_name varchar(255) NOT NULL,
  creator tinyint(1) NOT NULL,
  type varchar(25) NOT NULL,
  period varchar(30) NOT NULL,
  handicap double DEFAULT NULL,
  status varchar(20) DEFAULT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_connector_source_id_market (connector,source_id,market),
  CONSTRAINT fk_source_markets_connector FOREIGN KEY (connector) REFERENCES connectors (id),
  CONSTRAINT fk_source_markets_source_event FOREIGN KEY (source_event) REFERENCES source_events (id) ON DELETE CASCADE,
  CONSTRAINT fk_source_markets_market FOREIGN KEY (market) REFERENCES markets (id) ON DELETE CASCADE,
  INDEX idx_connector_source_id USING HASH(connector,source_id),
  INDEX idx_connector_last_fetch_time USING BTREE(connector,last_fetch_time),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE runners (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  market bigint(20) NOT NULL,
  participant bigint(20) DEFAULT NULL,
  name varchar(255) NOT NULL,
  sequence int(11) NOT NULL,
  type varchar(11) NOT NULL,
  side varchar(7) NOT NULL,
  handicap double DEFAULT NULL,
  rotation_number int(11) DEFAULT NULL,
  runner_status varchar(20) DEFAULT NULL,
  result_status varchar(20) DEFAULT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_market_sequence_type_side(market,sequence,type,side),
  CONSTRAINT fk_runners_market FOREIGN KEY (market) REFERENCES markets (id) ON DELETE CASCADE,
  CONSTRAINT fk_runners_participant FOREIGN KEY (participant) REFERENCES participants (id) ON DELETE CASCADE,
  INDEX idx_market_sequence USING HASH(market,sequence),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE source_runners (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  runner bigint(20) DEFAULT NULL,
  connector bigint(20) NOT NULL,
  source_market bigint(20) NOT NULL,
  source_id varchar(100) NOT NULL,
  source_name varchar(255) NOT NULL,
  creator tinyint(1) NOT NULL,
  type varchar(11) NOT NULL,
  side varchar(7) NOT NULL,
  rotation_number int(11) DEFAULT NULL,
  handicap double DEFAULT NULL,
  sequence int(11) NOT NULL,
  runner_status varchar(20) DEFAULT NULL,
  result_status varchar(20) DEFAULT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_connector_source_id (connector,source_id),
  UNIQUE KEY uq_connector_runner (connector,runner),
  CONSTRAINT fk_source_runners_runner FOREIGN KEY (runner) REFERENCES runners (id) ON DELETE CASCADE,
  CONSTRAINT fk_source_runners_source_market FOREIGN KEY (source_market) REFERENCES source_markets (id) ON DELETE CASCADE,
  CONSTRAINT fk_source_runners_connector FOREIGN KEY (connector) REFERENCES connectors (id),
  INDEX idx_connector_source_id USING HASH(connector,source_id),
  INDEX idx_connector_runner USING HASH(connector,runner),
  INDEX idx_connector_last_fetch_time USING BTREE(connector,last_fetch_time),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE prices (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  runner bigint(20) NOT NULL,
  betting_platform bigint(20) NOT NULL,
  side varchar(4) NOT NULL,
  sequence int(11) NOT NULL,
  odds decimal(19,8) NOT NULL,
  odds_type varchar(11) NOT NULL,
  decimal_odds decimal(19,8) NOT NULL,
  available_amount decimal(19,8) DEFAULT NULL,
  currency varchar(3) DEFAULT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_runner_betting_platform_side_sequence (runner,betting_platform,side,sequence),
  CONSTRAINT fk_prices_betting_platform FOREIGN KEY (betting_platform) REFERENCES betting_platforms (id),
  CONSTRAINT fk_prices_runner FOREIGN KEY (runner) REFERENCES runners (id) ON DELETE CASCADE,
  INDEX idx_betting_platform_runner USING HASH(betting_platform,runner),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE positions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  runner bigint(20) NOT NULL,
  account bigint(20) NOT NULL,
  betting_platform bigint(20) NOT NULL,
  value decimal(19,8) NOT NULL,
  currency varchar(3) NOT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_runner_account_betting_platform (runner,account,betting_platform),
  CONSTRAINT fk_positions_account FOREIGN KEY (account) REFERENCES accounts (id),
  CONSTRAINT fk_positions_betting_platform FOREIGN KEY (betting_platform) REFERENCES betting_platforms (id),
  CONSTRAINT fk_positions_runner FOREIGN KEY (runner) REFERENCES runners (id) ON DELETE CASCADE,
  INDEX idx_betting_platform_runner_account USING HASH(betting_platform,runner,account),
  INDEX idx_betting_platform_runner_account_last_fetch_time USING BTREE(betting_platform,runner,account, last_fetch_time),
  INDEX idx_betting_platform_runner_account_last_change_time USING BTREE(betting_platform,runner,account, last_change_time DESC),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE scores (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  source_event bigint(20) NOT NULL,
  type varchar(25) NOT NULL,
  home int(5) NOT NULL,
  away int(5) NOT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_scores_source_event FOREIGN KEY fk_event (source_event) REFERENCES source_events (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE offers (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  runner bigint(20) NOT NULL,
  account bigint(20) NOT NULL,
  connector bigint(20) NOT NULL,
  source_id varchar(100) NOT NULL,
  odds decimal(19,8) NOT NULL,
  odds_type varchar(11) NOT NULL,
  decimal_odds decimal(19,8) NOT NULL,
  stake decimal(19,8) DEFAULT NULL,
  matched_amount decimal(19,8) DEFAULT NULL,
  available_amount decimal(19,8) DEFAULT NULL,
  currency varchar(3) DEFAULT NULL,
  side varchar(4) NOT NULL,
  offer_time datetime NOT NULL,
  status varchar(17) NOT NULL,
  score bigint(20) NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_connector_source_id (connector,source_id),
  CONSTRAINT fk_offers_account FOREIGN KEY (account) REFERENCES accounts (id),
  CONSTRAINT fk_offers_connector FOREIGN KEY (connector) REFERENCES connectors (id),
  CONSTRAINT fk_offers_runner FOREIGN KEY (runner) REFERENCES runners (id) ON DELETE CASCADE,
  CONSTRAINT fk_offers_score FOREIGN KEY (score) REFERENCES scores (id),
  INDEX idx_connector_source_id USING HASH(connector,source_id),
  INDEX idx_connector_runner_account_last_fetch_time USING BTREE(connector,runner,account,last_fetch_time),
  INDEX idx_connector_runner_account_last_change_time USING BTREE(connector,runner,account,last_change_time DESC),
  INDEX idx_offer_time USING BTREE(offer_time),
  INDEX idx_last_fetch_time USING BTREE(last_fetch_time),
  INDEX idx_last_change_time USING BTREE(last_change_time DESC),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE bets (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  offer bigint(20) NOT NULL,
  betting_platform bigint(20) DEFAULT NULL,
  source_id varchar(100) NOT NULL,
  odds decimal(19,8) NOT NULL,
  odds_type varchar(11) NOT NULL,
  decimal_odds decimal(19,8) NOT NULL,
  stake decimal(19,8) NOT NULL,
  currency varchar(3) NOT NULL,
  creation_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_offer_source_id (offer,source_id),
  CONSTRAINT fk_bets_offer FOREIGN KEY (offer) REFERENCES offers (id) ON DELETE CASCADE,
  CONSTRAINT fk_bets_betting_platform FOREIGN KEY (betting_platform) REFERENCES betting_platforms (id),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gfeed_mid_points (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  betting_platform bigint(20) NOT NULL,
  runner bigint(20) NOT NULL,
  odds decimal(19,8) NOT NULL,
  odds_type varchar(11) NOT NULL,
  enabled bit(1) NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_betting_platform_runner (betting_platform,runner),
  CONSTRAINT fk_gfeed_mid_points_betting_platform FOREIGN KEY (betting_platform) REFERENCES betting_platforms (id) ON DELETE CASCADE,
  CONSTRAINT fk_gfeed_mid_points_runner FOREIGN KEY (runner) REFERENCES runners (id) ON DELETE CASCADE,
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gfeed_settings (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  event bigint(20) NOT NULL,
  old_price_threshold double DEFAULT NULL,
  other_price_threshold double DEFAULT NULL,
  anchor_percentage double DEFAULT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_gfeed_settings_event FOREIGN KEY (event) REFERENCES events (id),
  INDEX idx_id_version USING HASH(id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE source_event_incident (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  source_event bigint(20) NOT NULL,
  score bigint(20) NOT NULL,
  event_phase varchar (50) DEFAULT NULL,
  sequence_number bigint(20) DEFAULT NULL,
  elapsed_time time DEFAULT NULL,
  incident_type varchar(25) DEFAULT NULL,
  dangerball_status varchar(25) NOT NULL,
  participant_id bigint(20) DEFAULT NULL,
  creation_time datetime NOT NULL,
  last_fetch_time datetime NOT NULL,
  last_change_time datetime NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_source_event_incident_source_event FOREIGN KEY (source_event) REFERENCES source_events (id),
  CONSTRAINT fk_source_event_incident_score FOREIGN KEY (score) REFERENCES scores (id),
  UNIQUE KEY uq_source_event_incident (source_event, incident_type, sequence_number),
  INDEX idx_source_event USING HASH(source_event),
  INDEX idx_id_version USING HASH(id, version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE settled_bets (
  id BIGINT NOT NULL AUTO_INCREMENT,
  bet BIGINT NOT NULL,
  profit_loss DECIMAL(19,8) NOT NULL,
  settled_time DATETIME NOT NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX fk_settled_bets_1_idx (bet ASC),
  CONSTRAINT fk_settled_bets_bet FOREIGN KEY (bet) REFERENCES bets (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE report_job_info (
  id BIGINT NOT NULL AUTO_INCREMENT,
  job_name VARCHAR(100) NOT NULL,
  account_id BIGINT NOT NULL,
  account_name VARCHAR(45) NOT NULL,
  connector_id BIGINT NOT NULL,
  connector_name VARCHAR(45) NOT NULL,
  last_update DATETIME NOT NULL,
  job_status VARCHAR(10) NULL,
  version bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

