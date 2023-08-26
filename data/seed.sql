DROP TABLE IF EXISTS `deployment`;
DROP TABLE IF EXISTS `environment_codebase_pair`;
DROP TABLE IF EXISTS `environment`;
DROP TABLE IF EXISTS `vpn`;
DROP TABLE IF EXISTS `release_bundle_part`;
DROP TABLE IF EXISTS `codebase`;
DROP TABLE IF EXISTS `release_bundle`;

-- A codebase is a compilable chunk of code that makes up one or more parts of a full app
CREATE TABLE `codebase` (
    CODEBASE_ID INT(11) NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(512) NOT NULL,
    URL VARCHAR(2000) NOT NULL,
    URL_SHA2 CHAR(64) NOT NULL,
    PROJECT_CODE VARCHAR(15) NOT NULL,
    PRIMARY KEY (`CODEBASE_ID`),
    UNIQUE(URL_SHA2)
);

-- It's a VPN...
CREATE TABLE `vpn` (
    VPN_ID INT(11) NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(512) NOT NULL,
    URL VARCHAR(2000) NOT NULL,
    URL_SHA2 CHAR(64) NOT NULL,
    ORGANIZATION VARCHAR(512) NOT NULL,
    PRIMARY KEY (`VPN_ID`),
    UNIQUE(URL_SHA2)
);

-- An environment is a server where an app is deployed and runs
CREATE TABLE `environment` (
    ENVIRONMENT_ID INT(11) NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(512) NOT NULL,
    DOMAIN VARCHAR(512) NOT NULL,
    VPN_ID INT(11) NOT NULL,
    DESCRIPTION VARCHAR(2000) NOT NULL DEFAULT '',
    EXTRA_FIELDS JSON NOT NULL,
    PRIMARY KEY (`ENVIRONMENT_ID`),
    FOREIGN KEY (`VPN_ID`) REFERENCES `vpn` (`VPN_ID`),
    UNIQUE(NAME)
);

CREATE TABLE `environment_codebase_pair` (
    ENVIRONMENT_CODEBASE_PAIR_ID INT(11) NOT NULL AUTO_INCREMENT,
    ENVIRONMENT_ID INT(11) NOT NULL,
    CODEBASE_ID INT(11) NOT NULL,
    PRIMARY KEY (`ENVIRONMENT_CODEBASE_PAIR_ID`),
    FOREIGN KEY (`ENVIRONMENT_ID`) REFERENCES `environment` (`ENVIRONMENT_ID`),
    FOREIGN KEY (`CODEBASE_ID`) REFERENCES `codebase` (`CODEBASE_ID`),
    UNIQUE(ENVIRONMENT_ID, CODEBASE_ID)
);

-- A release bundle is a bundle of codebase, version pairs that makes a fully fledged app
CREATE TABLE `release_bundle` (
    RELEASE_BUNDLE_ID INT(11) NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR(512) NOT NULL,
    CREATION_DATE DATETIME NOT NULL,
    UPDATE_DATE DATETIME NOT NULL,
    STATUS VARCHAR(11) NOT NULL DEFAULT 'DEVELOPMENT',
    PRIMARY KEY (`RELEASE_BUNDLE_ID`)
);

CREATE TABLE `release_bundle_part` (
    RELEASE_BUNDLE_PART_ID INT(11) NOT NULL AUTO_INCREMENT,
    GIT_IDENTIFIER VARCHAR(512) NOT NULL,
    CODEBASE_ID INT(11) NOT NULL,
    RELEASE_BUNDLE_ID INT(11) NOT NULL,
    PRIMARY KEY (`RELEASE_BUNDLE_PART_ID`),
    FOREIGN KEY (`CODEBASE_ID`) REFERENCES `codebase` (`CODEBASE_ID`),
    FOREIGN KEY (`RELEASE_BUNDLE_ID`) REFERENCES `release_bundle` (`RELEASE_BUNDLE_ID`)
);

-- A deployment is a release bundle that has been deployed to an environment
CREATE TABLE `deployment` (
    DEPLOYMENT_ID INT(11) NOT NULL AUTO_INCREMENT,
    ENVIRONMENT_ID INT(11) NOT NULL,
    RELEASE_BUNDLE_ID INT(11) NOT NULL,
    DEPLOYMENT_DATE DATETIME NOT NULL,
    PREVIOUS_DEPLOYMENT_ID INT(11),
    PRIMARY KEY (`DEPLOYMENT_ID`),
    UNIQUE (DEPLOYMENT_DATE, ENVIRONMENT_ID),
    FOREIGN KEY (`ENVIRONMENT_ID`) REFERENCES `environment` (`ENVIRONMENT_ID`),
    FOREIGN KEY (`RELEASE_BUNDLE_ID`) REFERENCES `release_bundle` (`RELEASE_BUNDLE_ID`),
    FOREIGN KEY (`PREVIOUS_DEPLOYMENT_ID`) REFERENCES `deployment` (`DEPLOYMENT_ID`) ON DELETE SET NULL
);

INSERT INTO `release_bundle` (RELEASE_BUNDLE_ID, TITLE, CREATION_DATE, UPDATE_DATE) VALUES
    (1, 'My Cool Release', '2023-07-01', '2023-07-01'),
    (2, 'My Cool Release', '2023-07-02', '2023-07-02');

INSERT INTO `vpn` (VPN_ID, NAME, URL, URL_SHA2, ORGANIZATION) VALUES
    (1, 'MY cool VPN', 'foo.com', SHA2('foo.com', 256), 'Me'),
    (2, 'MY cool VPN 2', 'foo2.com', SHA2('foo2.com', 256), 'Me');

INSERT INTO `codebase` (CODEBASE_ID, NAME, URL, URL_SHA2, PROJECT_CODE) VALUES
    (1, 'PIC-SURE-API Build', 'https://github.com/hms-dbmi/pic-sure', SHA2('https://github.com/hms-dbmi/pic-sure', 256), 'PSA'),
    (2, 'PIC-SURE-HPDS Build', 'https://github.com/hms-dbmi/pic-sure-hpds', SHA2('https://github.com/hms-dbmi/pic-sure-hpds', 256), 'PSH'),
    (3, 'PIC-SURE Auth Micro-App Build', 'https://github.com/hms-dbmi/pic-sure-auth-microapp', SHA2('https://github.com/hms-dbmi/pic-sure-auth-microapp', 256), 'PSAMA'),
    (4, 'PIC-SURE Core Frontend', 'https://github.com/hms-dbmi/pic-sure-core-frontend', SHA2('https://github.com/hms-dbmi/pic-sure-core-frontend', 256), 'PSHU'),
    (5, 'Custom UI', 'https://github.com/hms-dbmi/pic-sure-gic-institution-frontend', SHA2('https://github.com/hms-dbmi/pic-sure-gic-institution-frontend', 256), 'PSU');

INSERT INTO `release_bundle_part` (RELEASE_BUNDLE_PART_ID, GIT_IDENTIFIER, CODEBASE_ID, RELEASE_BUNDLE_ID) VALUES
    (1, 'v2.0.0', 1, 1 ),
    (2, 'v2.0.0', 2, 1 ),
    (3, 'v2.0.0', 3, 1 ),
    (4, 'v2.0.0', 4, 1 ),
    (5, 'v2.0.0', 5, 1 ),
    (6, 'v2.0.0', 1, 2 ),
    (7, 'v2.0.0', 2, 2 ),
    (8, 'v2.0.0', 3, 2 ),
    (9, 'v2.0.0', 4, 2 ),
    (10, 'v2.0.0', 5, 2 );

INSERT INTO `environment` (ENVIRONMENT_ID, NAME, DOMAIN, VPN_ID, DESCRIPTION, EXTRA_FIELDS) VALUES
    (1, 'GIC BCH Dev', 'gic-bch-dev.pl.hms.harvard.edu', 1, 'Boston Children\'s GIC Institute node (dev)', '{}'),
    (2, 'GIC Common Area DEv', 'gic.hms.harvard.edu', 1, 'GIC Common Area', '{}');

INSERT INTO `environment_codebase_pair` (ENVIRONMENT_CODEBASE_PAIR_ID, ENVIRONMENT_ID, CODEBASE_ID) VALUES
    (1, 1, 1),
    (2, 1, 2);

INSERT INTO `deployment` (DEPLOYMENT_ID, ENVIRONMENT_ID, RELEASE_BUNDLE_ID, DEPLOYMENT_DATE, PREVIOUS_DEPLOYMENT_ID) VALUES
    (1, 1, 1, '2022-01-01 00:00:00', NULL),
    (2, 1, 1, '2022-01-02 00:00:00', 1);
