# Music-Library
Problem Statement:
Music Library:
Music library is the application which will be used as a best entertainment tool.
There will be 2 Roles for this application:
1. Admin
2. User
## User Stories:
1. As a user, I should be able to log in, log out and register into the application with my personal details
like emailId, phone number.
2. As a user, I should be able to see the list of songs available in the library.
3. As a user, I should be able to see the details of the song like name of the song, singer, music director,
release date, album name if a song is selected.
4. As an user, I should be able search any song using music director, album, artist
5. As a User, I should be able to create one or multiple playlists
6. As a User, I should be able search any song using the name in Playlist
7. As a User, I should be able to do the CRUD on my playlist
8. As a User, I should be able to do the CRUD on my playlist songs
9. As a User, I should be able to see the play, stop, repeat and Shuffle button inside my playlist.
10. I should be able to see the multiple playlist created by me.
11. As a User, I should be able add one or more songs in the playlist.
## Admin Stories:
1. As an admin, I should be able to login and logout into the application
2. As an admin, I should be able to do CRUD on song list in the library and / or songs
3. As an admin, I should be able to restrict the visibility of the song from user
4. As an admin, I should be able to send notifications whenever new songs are added.
This file is meant for personal use by sumithramarakala@gmail.com only.
Sharing or publishing the contents in part or full is liable for legal action.
Sprint Plan:
Sprint I objective:
1. Create database schema (all tables along with their relationships)
2. Create entities in Spring
3. Create Microservice based structure
4. CRUD on User and Admin
5. Create template using HTML and CSS
Sprint II Objective:
1. Develop search functionality (songs / playlist)
2. The songs CRUD for Admin
3. Create visibility restriction for songs
4. The playlist CRUD for user
5. The songs CRUD in playlist for user
6. Implement Spring security, JWT
Sprint III Objective:
1. Create Data Transfer Objects
2. Create Repository
3. Create Service Layer logic
4. Create controller to direct rest API
5. Create notification microservice for new songs added
6. Integrate FrontEnd with BackEnd
7. Add extra features
This file is meant for personal use by sumithramarakala@gmail.com only.
Sharing or publishing the contents in part or full is liable for legal action.
## Instructions: -
1. Create a separate microservice for user and functionalities based on the user using Spring Boot and
register all microservices using Eureka Server
2. Create a separate microservice for the Admin and functionalities based on the admin using Spring Boot
and register all microservices using Eureka Server
3. Swagger implementation should be separate for all the microservices.
4. Establish the connection between the microservices.
5. Use spring cloud to share the configuration between the microservices.
6. Utilize H2 database for storage purposes, with each microservice having its own database instance.
7. Create custom exceptions in each microservice to handle invalid inputs.
8. Implement the relationships where applicable, ensuring data consistency across microservices..
9. Implement inter-microservice communication as needed, considering asynchronous messaging or RESTful APIs.
10. Develop an independent microservice dedicated to dispatching email notifications.
11. Use fetch API to integrate front END(HTML , CSS ) or JSP server side with back END.
12. Any extra functionality you may add as per the requirement.
This file is meant for personal use by sumithramarakala@gmail.com only.
Sharing or publishing the contents in part or full is liable for legal action.
