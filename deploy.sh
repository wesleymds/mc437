./gradlew -Pprod bootRepackage -x test
heroku deploy:jar --jar build/libs/*war