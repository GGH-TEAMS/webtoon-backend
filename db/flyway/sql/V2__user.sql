CREATE TABLE IF NOT EXISTS USER
(
    ID       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    NICKNAME VARCHAR(255) NOT NULL COMMENT '이름',
    EMAIL    VARCHAR(255) NOT NULL COMMENT '이메일',
    CT_UTC   TIMESTAMP    NOT NULL COMMENT '생성 시간',
    UT_UTC   TIMESTAMP    NOT NULL COMMENT '업데이트 시간'
);

CREATE TABLE IF NOT EXISTS ACCOUNT
(
    ID         BIGINT                                             NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '계정 ID',
    USER_ID    BIGINT                                             NOT NULL COMMENT '사용자 ID',
    LOGIN_ID   VARCHAR(255)                                       NOT NULL COMMENT '로그인 ID',
    CREDENTIAL VARCHAR(255)                                       NOT NULL COMMENT '비밀번호 또는 자격증명',
    TYPE       ENUM ('GOOGLE')                                    NOT NULL COMMENT '계정 타입',
    STATUS     ENUM ('ONBOARDING_REQUIRED', 'ACTIVE', 'INACTIVE') NOT NULL COMMENT '계정 상태',
    CT_UTC     TIMESTAMP                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    UT_UTC     TIMESTAMP                                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간',
    UNIQUE (LOGIN_ID, TYPE)
);
