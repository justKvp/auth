-- auth.realmlist определение

CREATE TABLE `realmlist` (
                             `id` int unsigned NOT NULL AUTO_INCREMENT,
                             `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                             `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '127.0.0.1',
                             `localAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '127.0.0.1',
                             `localSubnetMask` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '255.255.255.0',
                             `port` smallint unsigned NOT NULL DEFAULT '8085',
                             `icon` tinyint unsigned NOT NULL DEFAULT '0',
                             `flag` tinyint unsigned NOT NULL DEFAULT '2',
                             `timezone` tinyint unsigned NOT NULL DEFAULT '0',
                             `allowedSecurityLevel` tinyint unsigned NOT NULL DEFAULT '0',
                             `population` float unsigned NOT NULL DEFAULT '0',
                             `gamebuild` int unsigned NOT NULL DEFAULT '12340',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Realm System';

-- auth.realmcharacters определение

CREATE TABLE `realmcharacters` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `realmid` int unsigned NOT NULL DEFAULT '0',
                                   `acctid` int unsigned NOT NULL,
                                   `numchars` tinyint unsigned NOT NULL DEFAULT '0',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Realm Character Tracker';

-- auth.ip_banned определение

CREATE TABLE `ip_banned` (
                             `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '127.0.0.1',
                             `bandate` int unsigned NOT NULL,
                             `unbandate` int unsigned NOT NULL,
                             `bannedby` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[Console]',
                             `banreason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'no reason',
                             PRIMARY KEY (`ip`,`bandate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Banned IPs';

-- auth.build_info определение

CREATE TABLE `build_info` (
                              `build` int NOT NULL,
                              `majorVersion` int DEFAULT NULL,
                              `minorVersion` int DEFAULT NULL,
                              `bugfixVersion` int DEFAULT NULL,
                              `hotfixVersion` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                              PRIMARY KEY (`build`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- auth.build_executable_hash определение

CREATE TABLE `build_executable_hash` (
                                         `build` int NOT NULL,
                                         `platform` char(4) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
                                         `executableHash` binary(20) NOT NULL,
                                         PRIMARY KEY (`build`,`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- auth.build_auth_key определение

CREATE TABLE `build_auth_key` (
                                  `build` int NOT NULL,
                                  `platform` char(4) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
                                  `arch` char(4) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
                                  `type` char(4) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
                                  `key` binary(16) NOT NULL,
                                  PRIMARY KEY (`build`,`platform`,`arch`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- auth.autobroadcast определение

CREATE TABLE `autobroadcast` (
                                 `realmid` int NOT NULL DEFAULT '-1',
                                 `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
                                 `weight` tinyint unsigned DEFAULT '1',
                                 `text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                 PRIMARY KEY (`id`,`realmid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- auth.account_muted определение

CREATE TABLE `account_muted` (
                                 `guid` int unsigned NOT NULL DEFAULT '0' COMMENT 'Global Unique Identifier',
                                 `mutedate` int unsigned NOT NULL DEFAULT '0',
                                 `mutetime` int unsigned NOT NULL DEFAULT '0',
                                 `mutedby` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `mutereason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                 PRIMARY KEY (`guid`,`mutedate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mute List';

-- auth.account_banned определение

CREATE TABLE `account_banned` (
                                  `id` int unsigned NOT NULL DEFAULT '0' COMMENT 'Account id',
                                  `bandate` int unsigned NOT NULL DEFAULT '0',
                                  `unbandate` int unsigned NOT NULL DEFAULT '0',
                                  `bannedby` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                  `banreason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                  `active` tinyint unsigned NOT NULL DEFAULT '1',
                                  PRIMARY KEY (`id`,`bandate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Ban List';

-- auth.account_access определение

CREATE TABLE `account_access` (
                                  `AccountID` int unsigned NOT NULL,
                                  `SecurityLevel` tinyint unsigned NOT NULL,
                                  `RealmID` int NOT NULL DEFAULT '-1',
                                  `Comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  PRIMARY KEY (`AccountID`,`RealmID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- auth.account определение

CREATE TABLE `account` (
                           `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identifier',
                           `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                           `salt` binary(32) NOT NULL,
                           `verifier` binary(32) NOT NULL,
                           `session_key_auth` binary(40) DEFAULT NULL,
                           `session_key_bnet` varbinary(64) DEFAULT NULL,
                           `totp_secret` varbinary(128) DEFAULT NULL,
                           `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                           `reg_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                           `joindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `last_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '127.0.0.1',
                           `last_attempt_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '127.0.0.1',
                           `failed_logins` int unsigned NOT NULL DEFAULT '0',
                           `locked` tinyint unsigned NOT NULL DEFAULT '0',
                           `lock_country` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '00',
                           `last_login` timestamp NULL DEFAULT NULL,
                           `online` tinyint unsigned NOT NULL DEFAULT '0',
                           `expansion` tinyint unsigned NOT NULL DEFAULT '2',
                           `mutetime` bigint NOT NULL DEFAULT '0',
                           `mutereason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                           `muteby` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                           `locale` tinyint unsigned NOT NULL DEFAULT '0',
                           `os` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                           `timezone_offset` smallint NOT NULL DEFAULT '0',
                           `recruiter` int unsigned NOT NULL DEFAULT '0',
                           `coins` bigint NOT NULL DEFAULT '0',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Account System';

INSERT INTO account
(username, salt, verifier, session_key_auth, session_key_bnet, totp_secret, email, reg_mail, joindate, last_ip, last_attempt_ip, failed_logins, locked, lock_country, last_login, online, expansion, mutetime, mutereason, muteby, locale, os, timezone_offset, recruiter, coins)
VALUES('TEST', 0x7C6FE47090E07EA641B3C44DD093AE9B68481269A53983F3F35B338F78A2580D, 0x8667081E591D2F69B201BAFA3C67C0EC3E981F805E0B8018411C11D3E276F42E, NULL, NULL, NULL, 'test@gmail.com', 'test@gmail.com', '2024-09-18 15:38:53', '127.0.0.1', '127.0.0.1', 0, 0, '00', NULL, 0, 2, 0, '', '', 0, '', 0, 0, 0);