CREATE TABLE "Admin" (
"id"  integer PRIMARY KEY AUTOINCREMENT,
"username"  text NOT NULL,
CONSTRAINT "fkey0" FOREIGN KEY ("username") REFERENCES "User" ("username")
);

CREATE TABLE "Attend" (
"id"  integer PRIMARY KEY AUTOINCREMENT,
"username"  text NOT NULL,
"eventid"  integer NOT NULL,
CONSTRAINT "fkey0" FOREIGN KEY ("username") REFERENCES "User" ("username"),
CONSTRAINT "fkey1" FOREIGN KEY ("eventid") REFERENCES "Event" ("id")
);

CREATE TRIGGER "addAttendLog" AFTER INSERT ON "Attend"
BEGIN
		INSERT INTO Log (content, date) VALUES (new.Username || " részt vesz a következő eseményen: " || (SELECT title FROM event WHERE (event.id == new.eventid)) || " (" || new.eventid || ")", strftime('%s','now')*1000);
END;

CREATE TRIGGER "delAttendLog" AFTER DELETE ON "Attend"
BEGIN
		INSERT INTO Log (content, date) VALUES (old.Username || " már nem vesz részt a következő eseményen: " || old.eventid, strftime('%s','now')*1000);
	--INSERT INTO Log (content, date) VALUES (old.Username || " már nem vesz részt a következő eseményen: " || (SELECT title FROM event WHERE (event.id == old.eventid)) || " (" || old.eventid || ")", strftime('%s','now')*1000);
END;

CREATE TABLE "Balance" (
"id"  integer PRIMARY KEY AUTOINCREMENT,
"username"  text NOT NULL,
"amount"  integer NOT NULL,
"description"  text NOT NULL,
"paid"  boolean NOT NULL,
"deadline"  date NOT NULL,
"payDate"  date,
CONSTRAINT "fkey0" FOREIGN KEY ("username") REFERENCES "User" ("username")
);

CREATE TRIGGER "addBalanceLog" AFTER INSERT ON "Balance"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Befizetés hozzáadva: " || new.Id || " (" || new.Username || ")", strftime('%s','now')*1000);
END;

CREATE TRIGGER "delBalanceLog" AFTER DELETE ON "Balance"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Befizetés törölve: " || old.id || " (" || old.username || ") ", strftime('%s','now')*1000);
END;

CREATE TRIGGER "modBalanceLog" AFTER UPDATE ON "Balance"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Befizetés módosítva: " || old.Id || " (" || old.Username || ")", strftime('%s','now')*1000);
END;

CREATE TABLE "Comment" (
"id"  integer PRIMARY KEY AUTOINCREMENT,
"username"  text NOT NULL,
"eventid"  integer NOT NULL,
"text"  text NOT NULL,
"date"  date NOT NULL,
CONSTRAINT "fkey0" FOREIGN KEY ("username") REFERENCES "User" ("username"),
CONSTRAINT "fkey1" FOREIGN KEY ("eventid") REFERENCES "Event" ("id")
);

CREATE TRIGGER "addCommentLog" AFTER INSERT ON "Comment"
BEGIN
		INSERT INTO Log (content, date) VALUES (new.Username || " hozzászólt a következő eseményhez: " || (SELECT title FROM event WHERE (event.id == new.eventid)) || " (" || new.eventid || ")", strftime('%s','now')*1000);
END;

CREATE TRIGGER "delCommentLog" AFTER DELETE ON "Comment"
BEGIN
		INSERT INTO Log (content, date) VALUES (old.username || " hozzászólása törölve: " || old.id, strftime('%s','now')*1000);
END;

CREATE TABLE "Event"(
  "id" integer PRIMARY KEY AUTOINCREMENT,
  "title" text NOT NULL,
  "content" text NOT NULL,
  "date" date NOT NULL,
  "active" boolean NOT NULL
);

CREATE TRIGGER "addEventLog" AFTER INSERT ON "Event"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Új esemény létrehozva: " || new.Title || " (" || new.ID || ")", strftime('%s','now')*1000);
END;

CREATE TRIGGER "delEventLog" AFTER DELETE ON "Event"
BEGIN
		DELETE FROM Comment WHERE old.id == Comment.eventid;
		DELETE FROM Attend WHERE old.id == Attend.eventid;

		INSERT INTO Log (content, date) VALUES ("Event törölve: " || old.Title || " (" || old.id || ") ", strftime('%s','now')*1000);
END;

CREATE TRIGGER "modEventLog" AFTER UPDATE ON "Event"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Esemény módosítva: " || new.Title || " (" || old.id || ")", strftime('%s','now')*1000);
END;

CREATE TABLE "Log" (
"id"  INTEGER NOT NULL,
"content"  TEXT,
"date"  DATE,
PRIMARY KEY ("id" ASC)
);

CREATE TABLE "News"(
  "id" integer PRIMARY KEY AUTOINCREMENT,
  "title" text NOT NULL,
  "content" text NOT NULL,
  "date" date
);

CREATE TRIGGER "addNewsLog" AFTER INSERT ON "News"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Új hír létrehozva: " || new.Title || " (" || new.Id || ")", strftime('%s','now')*1000);
END;

CREATE TRIGGER "delNewsLog" AFTER DELETE ON "News"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Hír törölve: " || old.Title || " (" || old.Id || ") ", strftime('%s','now')*1000);
END;

CREATE TRIGGER "modNewsLog" AFTER UPDATE ON "News"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Hír szerkesztve: " || new.Title || " (" || old.Id || ")", strftime('%s','now')*1000);
END;

CREATE TABLE "RoomChange" (
"id"  integer PRIMARY KEY AUTOINCREMENT,
"username"  text NOT NULL,
"room"  integer NOT NULL,
CONSTRAINT "fkey0" FOREIGN KEY ("username") REFERENCES "User" ("username")
);

CREATE TABLE User(
  username text NOT NULL PRIMARY KEY,
  firstname text,
  lastname text,
  password text NOT NULL,
  email text,
  phone text,
  zip integer,
  city text,
  street text,
  room integer,
  active boolean NOT NULL
);

CREATE TRIGGER "addUserLog" AFTER INSERT ON "User"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Felhasználó regisztrálva: " || new.Username || " (aktiválásra vár)", strftime('%s','now')*1000);
		UPDATE Room SET curCapacity = curCapacity + 1 WHERE new.Room == Room.Number;
END;

CREATE TRIGGER "delUserLog" AFTER DELETE ON "User"
BEGIN
		DELETE FROM Admin WHERE old.Username == Admin.Username;
		DELETE FROM Attend WHERE old.Username == Attend.Username;
		DELETE FROM RoomChange WHERE old.Username == RoomChange.Username;
		UPDATE Comment SET Username = old.Username || " (törölt felhasználó)" WHERE old.Username == Comment.Username;
		UPDATE Balance SET Username = old.Username || " (törölt felhasználó)" WHERE old.Username == Balance.Username;
		UPDATE Room SET curCapacity = curCapacity - 1 WHERE old.Room == Room.Number;

		INSERT INTO Log (content, date) VALUES ("Felhasználó törölve: " || old.Username, strftime('%s','now')*1000);
END;

CREATE TRIGGER "modUserLog" AFTER UPDATE ON "User"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Felhasználói adatok módosítva: " || old.Username, strftime('%s','now')*1000);
END;

CREATE TRIGGER "modUserRoom" AFTER UPDATE ON "User"
WHEN old.Room <> new.Room
BEGIN
	UPDATE Room SET curCapacity = curCapacity + 1 WHERE new.Room == Room.Number;
	UPDATE Room SET curCapacity = curCapacity - 1 WHERE old.Room == Room.Number;
	INSERT INTO Log (content, date) VALUES ("Szoba adatok módosítva: " || old.Room ||"," || new.Room, strftime('%s','now')*1000);
END;

CREATE TABLE Room(
  number integer NOT NULL PRIMARY KEY,
  maxCapacity integer NOT NULL,
  curCapacity integer NOT NULL
);

CREATE TRIGGER "modRoomLog" AFTER UPDATE ON "Room"
BEGIN
		INSERT INTO Log (content, date) VALUES ("Szoba adatok módosítva: " || old.Number, strftime('%s','now')*1000);
END;