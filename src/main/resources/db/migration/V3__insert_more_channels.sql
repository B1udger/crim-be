-- Insert additional sample channels

-- Channel created by admin1 (assumed id = 1)
INSERT INTO channels (name, owner_id, deleted) VALUES ('Sports Talk', 1, false);

-- Channel created by admin2 (assumed id = 2)
INSERT INTO channels (name, owner_id, deleted) VALUES ('Music Lounge', 2, false);

-- Channel created by admin1 (assumed id = 1)
INSERT INTO channels (name, owner_id, deleted) VALUES ('Tech Hub', 1, false);

-- Optionally, add all sample users as members of these channels
-- For "Sports Talk" (channel id assumed to be 2 after insertion)
INSERT INTO channel_members (channel_id, user_id) VALUES (2, 1);
INSERT INTO channel_members (channel_id, user_id) VALUES (2, 2);
INSERT INTO channel_members (channel_id, user_id) VALUES (2, 3);
INSERT INTO channel_members (channel_id, user_id) VALUES (2, 4);
INSERT INTO channel_members (channel_id, user_id) VALUES (2, 5);

-- For "Music Lounge" (channel id assumed to be 3)
INSERT INTO channel_members (channel_id, user_id) VALUES (3, 1);
INSERT INTO channel_members (channel_id, user_id) VALUES (3, 2);
INSERT INTO channel_members (channel_id, user_id) VALUES (3, 3);
INSERT INTO channel_members (channel_id, user_id) VALUES (3, 4);
INSERT INTO channel_members (channel_id, user_id) VALUES (3, 5);

-- For "Tech Hub" (channel id assumed to be 4)
INSERT INTO channel_members (channel_id, user_id) VALUES (4, 1);
INSERT INTO channel_members (channel_id, user_id) VALUES (4, 2);
INSERT INTO channel_members (channel_id, user_id) VALUES (4, 3);
INSERT INTO channel_members (channel_id, user_id) VALUES (4, 4);
INSERT INTO channel_members (channel_id, user_id) VALUES (4, 5);
