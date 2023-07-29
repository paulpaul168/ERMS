source .env

npm run build
ssh $SERVER "rm -rf app/hack/dist/*"
scp -r dist/* $SERVER:/root/app/hack/dist/
