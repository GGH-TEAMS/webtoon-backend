CREATE TABLE IF NOT EXISTS FILE_METADATA
(
    ID                BIGINT                NOT NULL AUTO_INCREMENT COMMENT 'ID',
    ORIGINAL_FILENAME VARCHAR(255)          NOT NULL COMMENT '원본 파일 이름',
    FILENAME          VARCHAR(255)          NOT NULL COMMENT '파일 이름',
    DIRECTORY_PATH    VARCHAR(255)          NOT NULL COMMENT 'Directory path',
    FILE_URL          VARCHAR(255)         NOT NULL COMMENT '파일 URL',
    EXPIRATION_DATE   DATE COMMENT '파일 만료날짜',
    STORAGE_TYPE      ENUM ('LOCAL', 'AWS') NOT NULL COMMENT '저장소 타입',
    SERVICE_ID        VARCHAR(255) COMMENT '어떤 API 서버에서 사용하는지에 대한 구분 ID',
    USE_CASE          VARCHAR(255) COMMENT '어떤 용도로 파일을 사용하는지에 대한 구분',
    IS_TEMPORARY      BOOLEAN               NOT NULL COMMENT '임시 파일 여부',
    CT_UTC            DATETIME(6)           NOT NULL COMMENT '생성시간',
    UT_UTC            DATETIME(6) COMMENT '업데이트 시간',
    PRIMARY KEY (ID),
    UNIQUE (FILE_URL),
    UNIQUE (FILENAME, DIRECTORY_PATH, STORAGE_TYPE)
);
