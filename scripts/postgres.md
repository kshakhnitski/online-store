```bash
docker exec -it onlineshop-postgres psql -U postgres -c "CREATE DATABASE users"
docker exec -it onlineshop-postgres psql -U postgres -c "CREATE DATABASE catalog"
```