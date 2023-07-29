source .env
./gradlew :bootJar
rm -rf deploy
mkdir deploy
cp -r build/libs deploy
cp -r build/resources deploy

cd deploy/libs
for file in *.jar
do
  mv "$file" "app.jar"
done
cd ../..
ssh $SERVER "rm -rf app/hack/source/*"
scp -r deploy/* $SERVER:/root/app/hack/source/
ssh $SERVER "chmod +x app/hack/source/libs/app.jar"
ssh $SERVER "cd app/hack; docker-compose up -d --build"
