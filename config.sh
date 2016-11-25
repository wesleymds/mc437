heroku git:remote -a mc-437
heroku config:add java_opts='-Xmx128m -Xms128m -Xss256k -XX:+UseCompressedOops'
heroku config:add JAVA_OPTS='-Xmx128m -Xms128m -Xss256k -XX:+UseCompressedOops'