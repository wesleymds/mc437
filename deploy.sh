heroku pg:psql -c "update databasechangeloglock set locked=false;"
./gradlew -Pprod bootRepackage -x test
heroku deploy:jar --jar build/libs/*war