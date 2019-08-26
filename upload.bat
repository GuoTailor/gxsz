call mvnw package
call apidoc -i ./ -o api/
call scp -r api root@zelfly.com:/data/www/
call scp target\gxsz-0.0.1-SNAPSHOT.jar root@zelfly.com:~
