CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE,
                       role VARCHAR(50) NOT NULL DEFAULT 'USER'
);

CREATE TABLE user_friends (
                              user_id BIGINT NOT NULL,
                              friend_id BIGINT NOT NULL,
                              PRIMARY KEY (user_id, friend_id),
                              CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                              CONSTRAINT fk_friend FOREIGN KEY (friend_id) REFERENCES users(id)
);

CREATE TABLE channels (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          owner_id BIGINT NOT NULL,
                          deleted BOOLEAN DEFAULT FALSE,
                          CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE channel_admins (
                                channel_id BIGINT NOT NULL,
                                user_id BIGINT NOT NULL,
                                PRIMARY KEY (channel_id, user_id),
                                CONSTRAINT fk_channel_admin FOREIGN KEY (channel_id) REFERENCES channels(id),
                                CONSTRAINT fk_admin FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE channel_members (
                                 channel_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 PRIMARY KEY (channel_id, user_id),
                                 CONSTRAINT fk_channel_member FOREIGN KEY (channel_id) REFERENCES channels(id),
                                 CONSTRAINT fk_member FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          content TEXT NOT NULL,
                          sender_id BIGINT NOT NULL,
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          recipient_user_id BIGINT,
                          channel_id BIGINT,
                          CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id),
                          CONSTRAINT fk_recipient_user FOREIGN KEY (recipient_user_id) REFERENCES users(id),
                          CONSTRAINT fk_channel_message FOREIGN KEY (channel_id) REFERENCES channels(id)
);
