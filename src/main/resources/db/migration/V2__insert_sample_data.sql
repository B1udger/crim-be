-- Insert 5 sample users: 2 admins and 3 normal users
INSERT INTO users (username, password, email, role) VALUES ('admin1', 'password1', 'admin1@example.com', 'ADMIN');
INSERT INTO users (username, password, email, role) VALUES ('admin2', 'password2', 'admin2@example.com', 'ADMIN');
INSERT INTO users (username, password, email, role) VALUES ('user1', 'password3', 'user1@example.com', 'USER');
INSERT INTO users (username, password, email, role) VALUES ('user2', 'password4', 'user2@example.com', 'USER');
INSERT INTO users (username, password, email, role) VALUES ('user3', 'password5', 'user3@example.com', 'USER');

-- Create a channel by admin1 (assumed id = 1)
INSERT INTO channels (name, owner_id, deleted) VALUES ('General Chat', 1, false);

-- Add all users to the channel
INSERT INTO channel_members (channel_id, user_id) VALUES (1, 1);
INSERT INTO channel_members (channel_id, user_id) VALUES (1, 2);
INSERT INTO channel_members (channel_id, user_id) VALUES (1, 3);
INSERT INTO channel_members (channel_id, user_id) VALUES (1, 4);
INSERT INTO channel_members (channel_id, user_id) VALUES (1, 5);

-- Set one of the normal users (user2, assumed id = 4) as an admin in the channel
INSERT INTO channel_admins (channel_id, user_id) VALUES (1, 4);
