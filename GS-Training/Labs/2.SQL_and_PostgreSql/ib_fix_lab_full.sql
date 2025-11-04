
-- =========================================================
-- Investment Banking FIX Lab — Full PostgreSQL Script
-- Creates schema + seeds sample data with explicit casts
-- Safe to run multiple times (uncomment DROP SCHEMA below to reset)
-- =========================================================

-- DROP SCHEMA IF EXISTS ib_fix_lab CASCADE;  -- <- uncomment to reset
CREATE SCHEMA IF NOT EXISTS ib_fix_lab;
SET search_path = ib_fix_lab, public;

BEGIN;

-- --- Reference tables -----------------------------------
CREATE TABLE IF NOT EXISTS participants (
  participant_id SERIAL PRIMARY KEY,
  participant_type TEXT NOT NULL CHECK (participant_type IN ('CLIENT','BROKER')),
  name            TEXT NOT NULL UNIQUE,
  country         TEXT
);

CREATE TABLE IF NOT EXISTS venues (
  venue_id  SERIAL PRIMARY KEY,
  mic       TEXT UNIQUE,
  name      TEXT NOT NULL,
  country   TEXT
);

CREATE TABLE IF NOT EXISTS instruments (
  instrument_id SERIAL PRIMARY KEY,
  symbol        TEXT NOT NULL UNIQUE,
  security_id   TEXT,
  asset_class   TEXT,
  currency      TEXT NOT NULL DEFAULT 'INR'
);

CREATE TABLE IF NOT EXISTS fix_sessions (
  session_id     SERIAL PRIMARY KEY,
  sender_comp_id TEXT NOT NULL,
  target_comp_id TEXT NOT NULL
);

-- --- Orders ---------------------------------------------
-- Side (54): '1'=Buy, '2'=Sell (FIX)
-- OrdStatus (39): '0' New, '1' Partially filled, '2' Filled, '4' Canceled, '5' Replaced, '8' Rejected, 'A' Pending New, etc.
CREATE TABLE IF NOT EXISTS orders (
  order_id        BIGSERIAL PRIMARY KEY,
  cl_ord_id       TEXT NOT NULL,
  orig_cl_ord_id  TEXT,
  client_id       INT  NOT NULL REFERENCES participants(participant_id),
  broker_id       INT  NOT NULL REFERENCES participants(participant_id),
  instrument_id   INT  NOT NULL REFERENCES instruments(instrument_id),
  venue_id        INT  NOT NULL REFERENCES venues(venue_id),
  session_id      INT  REFERENCES fix_sessions(session_id),
  side_code       CHAR(1) NOT NULL CHECK (side_code IN ('1','2')),
  order_qty       NUMERIC(18,4) NOT NULL,
  limit_price     NUMERIC(18,6),
  ord_type        TEXT CHECK (ord_type IN ('MARKET','LIMIT')) DEFAULT 'LIMIT',
  time_in_force   TEXT,
  order_ts        TIMESTAMPTZ NOT NULL,
  status_code     CHAR(1) NOT NULL 
                  CHECK (status_code IN ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E')),
  cum_qty         NUMERIC(18,4) NOT NULL DEFAULT 0,
  leaves_qty      NUMERIC(18,4) NOT NULL DEFAULT 0,
  UNIQUE (cl_ord_id, client_id)
);

CREATE INDEX IF NOT EXISTS idx_orders_client ON orders(client_id, order_ts);
CREATE INDEX IF NOT EXISTS idx_orders_instrument ON orders(instrument_id, order_ts);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status_code);

-- --- Executions -----------------------------------------
-- ExecType (150): '0' New, '1' Partial fill, '2' Fill, '4' Canceled, '5' Replaced, '8' Rejected, etc.
-- OrdStatus (39): current order state at the time of report
CREATE TABLE IF NOT EXISTS executions (
  exec_id          TEXT PRIMARY KEY,
  order_id         BIGINT NOT NULL REFERENCES orders(order_id),
  exec_type_code   CHAR(1) NOT NULL 
                   CHECK (exec_type_code IN ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I')),
  ord_status_code  CHAR(1) NOT NULL 
                   CHECK (ord_status_code IN ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E')),
  last_qty         NUMERIC(18,4) NOT NULL,
  last_px          NUMERIC(18,6) NOT NULL,
  exec_ts          TIMESTAMPTZ NOT NULL,
  liquidity_flag   TEXT,
  fee_amt          NUMERIC(18,6),
  currency         TEXT NOT NULL DEFAULT 'INR',
  reject_reason    TEXT
);

CREATE INDEX IF NOT EXISTS idx_exec_order ON executions(order_id, exec_ts);

-- --- Daily instrument benchmarks (for slippage exercises)
CREATE TABLE IF NOT EXISTS instrument_prices (
  instrument_id INT NOT NULL REFERENCES instruments(instrument_id),
  price_date    DATE NOT NULL,
  benchmark_px  NUMERIC(18,6) NOT NULL,
  PRIMARY KEY (instrument_id, price_date)
);

-- --- Simple per-client daily risk limits ----------------
CREATE TABLE IF NOT EXISTS risk_limits (
  client_id   INT NOT NULL REFERENCES participants(participant_id),
  limit_date  DATE NOT NULL,
  daily_notional_limit NUMERIC(18,2) NOT NULL,
  PRIMARY KEY (client_id, limit_date)
);

-- =========================================================
-- Seed Data
-- =========================================================

-- Participants
INSERT INTO participants (participant_type, name, country) VALUES
  ('CLIENT','Alpha AM','IN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO participants (participant_type, name, country) VALUES
  ('CLIENT','Beta Capital','IN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO participants (participant_type, name, country) VALUES
  ('BROKER','Gotham Securities','IN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO participants (participant_type, name, country) VALUES
  ('BROKER','Metro Securities','IN')
ON CONFLICT (name) DO NOTHING;

-- Venues
INSERT INTO venues (mic, name, country) VALUES
  ('XNSE','NSE India','IN')
ON CONFLICT (mic) DO NOTHING;

INSERT INTO venues (mic, name, country) VALUES
  ('XBOM','BSE India','IN')
ON CONFLICT (mic) DO NOTHING;

-- Instruments
INSERT INTO instruments (symbol, security_id, asset_class, currency) VALUES
  ('INFY',     'INE009A01021','EQ','INR')
ON CONFLICT (symbol) DO NOTHING;
INSERT INTO instruments (symbol, security_id, asset_class, currency) VALUES
  ('TCS',      'INE467B01029','EQ','INR')
ON CONFLICT (symbol) DO NOTHING;
INSERT INTO instruments (symbol, security_id, asset_class, currency) VALUES
  ('RELIANCE', 'INE002A01018','EQ','INR')
ON CONFLICT (symbol) DO NOTHING;
INSERT INTO instruments (symbol, security_id, asset_class, currency) VALUES
  ('HDFC',     'INE040A01034','EQ','INR')
ON CONFLICT (symbol) DO NOTHING;
INSERT INTO instruments (symbol, security_id, asset_class, currency) VALUES
  ('SBIN',     'INE062A01020','EQ','INR')
ON CONFLICT (symbol) DO NOTHING;
INSERT INTO instruments (symbol, security_id, asset_class, currency) VALUES
  ('AXISBANK', 'INE238A01034','EQ','INR')
ON CONFLICT (symbol) DO NOTHING;

-- Sessions
INSERT INTO fix_sessions (sender_comp_id, target_comp_id) VALUES
  ('ALPHA','GOTHAM')
ON CONFLICT DO NOTHING;
INSERT INTO fix_sessions (sender_comp_id, target_comp_id) VALUES
  ('BETA','METRO')
ON CONFLICT DO NOTHING;

-- Benchmarks for 2025-10-29
INSERT INTO instrument_prices (instrument_id, price_date, benchmark_px)
SELECT instrument_id, DATE '2025-10-29', x.bm
FROM instruments i
JOIN (VALUES
  ('INFY',1510.00::numeric),
  ('TCS', 3720.00::numeric),
  ('RELIANCE',2450.00::numeric),
  ('HDFC',1620.00::numeric),
  ('SBIN',571.00::numeric),
  ('AXISBANK',910.00::numeric)
) AS x(sym,bm) ON x.sym = i.symbol
ON CONFLICT (instrument_id, price_date) DO NOTHING;

-- Risk limits for 2025-10-29
INSERT INTO risk_limits (client_id, limit_date, daily_notional_limit)
SELECT p.participant_id, DATE '2025-10-29', lim
FROM participants p
JOIN (VALUES
  ('Alpha AM',  5000000.00::numeric),
  ('Beta Capital', 3000000.00::numeric)
) AS x(nm,lim) ON x.nm = p.name
WHERE p.participant_type='CLIENT'
ON CONFLICT (client_id, limit_date) DO NOTHING;

-- Orders (explicit casts)
WITH
c AS (SELECT name, participant_id FROM participants WHERE participant_type='CLIENT'),
b AS (SELECT name, participant_id FROM participants WHERE participant_type='BROKER'),
v AS (SELECT name, venue_id FROM venues),
i AS (SELECT symbol, instrument_id FROM instruments),
s AS (SELECT sender_comp_id, session_id FROM fix_sessions)
INSERT INTO orders
(cl_ord_id, orig_cl_ord_id, client_id, broker_id, instrument_id, venue_id, session_id,
 side_code, order_qty, limit_price, ord_type, time_in_force, order_ts, status_code, cum_qty, leaves_qty)
SELECT * FROM (
  -- A1: Alpha buys INFY 1000 @ 1520, fully filled on NSE
  SELECT
    'A1'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Alpha AM')::int,
    (SELECT participant_id FROM b WHERE name='Gotham Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='INFY')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='ALPHA')::int,
    '1'::char(1),
    1000::numeric,
    1520.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 09:30:00+05:30' AS timestamptz),
    '2'::char(1),
    1000::numeric,
    0::numeric

  UNION ALL
  -- A2: Alpha sells TCS 500 @ 3730, partial fill 200 on NSE
  SELECT
    'A2'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Alpha AM')::int,
    (SELECT participant_id FROM b WHERE name='Gotham Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='TCS')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='ALPHA')::int,
    '2'::char(1),
    500::numeric,
    3730.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 10:00:00+05:30' AS timestamptz),
    '1'::char(1),
    200::numeric,
    300::numeric

  UNION ALL
  -- A3: Alpha buys RELIANCE 1200 @ 2460, fully filled across 3 prints
  SELECT
    'A3'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Alpha AM')::int,
    (SELECT participant_id FROM b WHERE name='Gotham Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='RELIANCE')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='ALPHA')::int,
    '1'::char(1),
    1200::numeric,
    2460.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 10:15:00+05:30' AS timestamptz),
    '2'::char(1),
    1200::numeric,
    0::numeric

  UNION ALL
  -- B1: Beta buys SBIN 2000 @ MKT, single print
  SELECT
    'B1'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Beta Capital')::int,
    (SELECT participant_id FROM b WHERE name='Metro Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='SBIN')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='BETA')::int,
    '1'::char(1),
    2000::numeric,
    NULL::numeric,
    'MARKET'::text,
    'IOC'::text,
    CAST('2025-10-29 09:45:00+05:30' AS timestamptz),
    '2'::char(1),
    2000::numeric,
    0::numeric

  UNION ALL
  -- B2: Beta buys HDFC 1000 @ 1625, canceled (no fills)
  SELECT
    'B2'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Beta Capital')::int,
    (SELECT participant_id FROM b WHERE name='Metro Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='HDFC')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='BETA')::int,
    '1'::char(1),
    1000::numeric,
    1625.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 11:00:00+05:30' AS timestamptz),
    '4'::char(1),
    0::numeric,
    1000::numeric

  UNION ALL
  -- B3: Beta sells AXISBANK 800 @ 910, fully filled over 2 prints
  SELECT
    'B3'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Beta Capital')::int,
    (SELECT participant_id FROM b WHERE name='Metro Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='AXISBANK')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='BETA')::int,
    '2'::char(1),
    800::numeric,
    910.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 09:55:00+05:30' AS timestamptz),
    '2'::char(1),
    800::numeric,
    0::numeric

  UNION ALL
  -- A4: Alpha buys INFY 500 @ 1500, rejected
  SELECT
    'A4'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Alpha AM')::int,
    (SELECT participant_id FROM b WHERE name='Gotham Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='INFY')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='ALPHA')::int,
    '1'::char(1),
    500::numeric,
    1500.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 09:35:00+05:30' AS timestamptz),
    '8'::char(1),
    0::numeric,
    500::numeric

  UNION ALL
  -- B4: Beta sells RELIANCE 1000 @ MKT on BSE, full
  SELECT
    'B4'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Beta Capital')::int,
    (SELECT participant_id FROM b WHERE name='Metro Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='RELIANCE')::int,
    (SELECT venue_id        FROM v WHERE name='BSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='BETA')::int,
    '2'::char(1),
    1000::numeric,
    NULL::numeric,
    'MARKET'::text,
    'IOC'::text,
    CAST('2025-10-29 10:40:00+05:30' AS timestamptz),
    '2'::char(1),
    1000::numeric,
    0::numeric

  UNION ALL
  -- A5: Alpha buys TCS 700 @ 3728, replaced to A5R1
  SELECT
    'A5'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Alpha AM')::int,
    (SELECT participant_id FROM b WHERE name='Gotham Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='TCS')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='ALPHA')::int,
    '1'::char(1),
    700::numeric,
    3728.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 11:05:00+05:30' AS timestamptz),
    '5'::char(1),
    0::numeric,
    700::numeric

  UNION ALL
  -- A5R1: replacement active, partial 450 filled
  SELECT
    'A5R1'::text,
    'A5'::text,
    (SELECT participant_id FROM c WHERE name='Alpha AM')::int,
    (SELECT participant_id FROM b WHERE name='Gotham Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='TCS')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='ALPHA')::int,
    '1'::char(1),
    900::numeric,
    3726.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 11:07:00+05:30' AS timestamptz),
    '1'::char(1),
    450::numeric,
    450::numeric

  UNION ALL
  -- B5: Beta buys SBIN 1500 @ 572, pending new
  SELECT
    'B5'::text,
    NULL::text,
    (SELECT participant_id FROM c WHERE name='Beta Capital')::int,
    (SELECT participant_id FROM b WHERE name='Metro Securities')::int,
    (SELECT instrument_id   FROM i WHERE symbol='SBIN')::int,
    (SELECT venue_id        FROM v WHERE name='NSE India')::int,
    (SELECT session_id      FROM s WHERE sender_comp_id='BETA')::int,
    '1'::char(1),
    1500::numeric,
    572.00::numeric,
    'LIMIT'::text,
    'DAY'::text,
    CAST('2025-10-29 12:00:00+05:30' AS timestamptz),
    'A'::char(1),
    0::numeric,
    1500::numeric
) q;

-- Executions (cast exec_ts explicitly)
WITH o AS (SELECT cl_ord_id, order_id FROM orders)
INSERT INTO executions
(exec_id, order_id, exec_type_code, ord_status_code, last_qty, last_px, exec_ts, liquidity_flag, fee_amt, currency, reject_reason)
VALUES
  ('E1',  (SELECT order_id FROM o WHERE cl_ord_id='A1'),   '1','1', 400::numeric, 1518.00::numeric, CAST('2025-10-29 09:30:15+05:30' AS timestamptz), 'R', 12.00::numeric, 'INR', NULL),
  ('E2',  (SELECT order_id FROM o WHERE cl_ord_id='A1'),   '2','2', 600::numeric, 1519.00::numeric, CAST('2025-10-29 09:31:10+05:30' AS timestamptz), 'R', 18.00::numeric, 'INR', NULL),
  ('E3',  (SELECT order_id FROM o WHERE cl_ord_id='A2'),   '1','1', 200::numeric, 3725.00::numeric, CAST('2025-10-29 10:05:00+05:30' AS timestamptz), 'A', 10.00::numeric, 'INR', NULL),
  ('E4',  (SELECT order_id FROM o WHERE cl_ord_id='A3'),   '1','1', 300::numeric, 2452.00::numeric, CAST('2025-10-29 10:16:10+05:30' AS timestamptz), 'R', 15.00::numeric, 'INR', NULL),
  ('E5',  (SELECT order_id FROM o WHERE cl_ord_id='A3'),   '1','1', 500::numeric, 2451.00::numeric, CAST('2025-10-29 10:17:30+05:30' AS timestamptz), 'R', 25.00::numeric, 'INR', NULL),
  ('E6',  (SELECT order_id FROM o WHERE cl_ord_id='A3'),   '2','2', 400::numeric, 2449.50::numeric, CAST('2025-10-29 10:19:45+05:30' AS timestamptz), 'R', 20.00::numeric, 'INR', NULL),
  ('E7',  (SELECT order_id FROM o WHERE cl_ord_id='B1'),   '2','2', 2000::numeric, 571.40::numeric, CAST('2025-10-29 09:45:05+05:30' AS timestamptz), 'R', 30.00::numeric, 'INR', NULL),
  ('E8',  (SELECT order_id FROM o WHERE cl_ord_id='B2'),   '4','4', 0::numeric, 0.00::numeric,    CAST('2025-10-29 11:15:00+05:30' AS timestamptz), NULL, 0.00::numeric, 'INR', NULL),
  ('E9',  (SELECT order_id FROM o WHERE cl_ord_id='B3'),   '1','1', 300::numeric, 909.80::numeric, CAST('2025-10-29 09:56:00+05:30' AS timestamptz), 'A', 12.00::numeric, 'INR', NULL),
  ('E10', (SELECT order_id FROM o WHERE cl_ord_id='B3'),   '2','2', 500::numeric, 910.10::numeric, CAST('2025-10-29 09:58:20+05:30' AS timestamptz), 'R', 20.00::numeric, 'INR', NULL),
  ('E11', (SELECT order_id FROM o WHERE cl_ord_id='A4'),   '8','8', 0::numeric, 0.00::numeric,    CAST('2025-10-29 09:35:05+05:30' AS timestamptz), NULL, 0.00::numeric, 'INR', 'Credit check failed'),
  ('E12', (SELECT order_id FROM o WHERE cl_ord_id='B4'),   '2','2', 1000::numeric, 2448.00::numeric,CAST('2025-10-29 10:40:05+05:30' AS timestamptz), 'R', 28.00::numeric, 'INR', NULL),
  ('E13', (SELECT order_id FROM o WHERE cl_ord_id='A5'),   '5','5', 0::numeric, 0.00::numeric,    CAST('2025-10-29 11:06:00+05:30' AS timestamptz), NULL, 0.00::numeric, 'INR', NULL),
  ('E14', (SELECT order_id FROM o WHERE cl_ord_id='A5R1'), '1','1', 450::numeric, 3726.50::numeric,CAST('2025-10-29 11:08:40+05:30' AS timestamptz), 'R', 14.00::numeric, 'INR', NULL)
ON CONFLICT (exec_id) DO NOTHING;

COMMIT;

-- Optional smoke checks (uncomment to run):
-- SELECT COUNT(*) AS exec_rows FROM executions;
-- SELECT status_code, COUNT(*) FROM orders GROUP BY 1 ORDER BY 1;
-- SELECT p.name, SUM(e.last_qty*e.last_px) AS notional
-- FROM executions e
-- JOIN orders o ON o.order_id=e.order_id
-- JOIN participants p ON p.participant_id=o.client_id
-- WHERE e.exec_type_code IN ('1','2')
-- GROUP BY p.name;
