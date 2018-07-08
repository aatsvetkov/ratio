Feel free to complete this test task, override or change everything

For start app: 
1. Upadte tmdb.apikey in src\main\resources\application.properties
2. Run mvn clean install
3. Run mvn spring-boot:run

### Register user
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/user/register/ -v

### Login
curl -X POST --data "username=test@test2.com&password=test" http://localhost:8080/login -v

### Movie Search
curl -X GET --data "year=2015&month=12" http://localhost:8080/movie/searchByFavoriteActorsUnwatchedMovies -v

### Mark movie watched
curl -X POST --data "" http://localhost:8080/movie/watched/{movieId} -v

### Favorite Actor 
curl -X POST --data "" http://localhost:8080/actor/favorite/{actorId} -v

curl -X DELETE --data "" http://localhost:8080/actor/favorite/{actorId} -v

### View popular movies
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/movie/popular/ -v
