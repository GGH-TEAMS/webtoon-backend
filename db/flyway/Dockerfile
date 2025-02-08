FROM flyway/flyway:latest

# sql, conf 복사
COPY sql /flyway/sql
COPY conf /flyway/conf
COPY drivers /flyway/drivers

# Flyway 실행 명령어를 ENTRYPOINT로 설정
ENTRYPOINT ["flyway"]