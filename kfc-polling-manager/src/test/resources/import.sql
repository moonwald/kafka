INSERT INTO connectors (id, name) VALUES(1, 'Matchbook')
INSERT INTO connectors (id, name) VALUES(2, 'ThreeEt')

--EVENTS
INSERT INTO source_events (id, connector, source_id, source_name, status) VALUES(1, 1, '234234', 'Man United vs Chelsea','OPEN')
INSERT INTO source_events (id, connector, source_id, source_name, status) VALUES(2, 2, '223424', 'Irish Open','OPEN')

INSERT INTO meta_tags (id, name, type) VALUES(1,'Soccer','SPORT')
INSERT INTO meta_tags (id, name, type) VALUES(2,'Golf','SPORT')

INSERT INTO source_events_meta_tags (source_events, meta_tags) VALUES(1, 1)
INSERT INTO source_events_meta_tags (source_events, meta_tags) VALUES(2, 2)

--Accounts
INSERT INTO accounts (id, connector, username, password, currency) VALUES(1, 1, 'testgbp05', 'Password1', 'GBP')
INSERT INTO accounts (id, connector, username, password, currency) VALUES(2, 2, 'decare_trade', 'Password1', 'GBP')

--Connections
INSERT INTO connections (id, connector, base_url, session_token) VALUES(1, 1, 'https://beta01.xcl.ie/bpapi/rest', '51270_d6cd73d9c96581fee3ccc1cd7c3a8f1')
INSERT INTO connections (id, connector, base_url, session_token) VALUES(2, 2, 'http://rose.xcl.ie/mercury/api', '37947de818918242cb6d2b7bc495ce9c')

--MARKETS
INSERT INTO source_markets (id, connector, source_event, source_id, status, type) VALUES(1, 1, 1, '456', 'OPEN', 'ASIAN_HANDICAP')
INSERT INTO source_markets (id, connector, source_event, source_id, status, type) VALUES(2, 1, 1, '789', 'OPEN', 'ASIAN_HANDICAP_TOTAL')
INSERT INTO source_markets (id, connector, source_event, source_id, status, type) VALUES(3, 2, 2, '000', 'CLOSED', 'ASIAN_HANDICAP')

--RUNNERS
INSERT INTO source_runners (id, connector, source_market, source_id, runner_status, type, side, handicap) VALUES(1, 1, 1, '1020', 'OPEN', 'PARTICIPANT', 'HOME', 0.5)
INSERT INTO source_runners (id, connector, source_market, source_id, runner_status, type, side, handicap) VALUES(2, 1, 2, '1025', 'OPEN', 'PARTICIPANT', 'AWAY', 4.5)
INSERT INTO source_runners (id, connector, source_market, source_id, runner_status, type, side, handicap) VALUES(3, 1, 3, '1030', 'CLOSED', 'PARTICIPANT', 'AWAY', -4.5)
