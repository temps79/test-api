You are a member of a new startup that is developing a cutting edge software that will take out any competition in no time.
It is a new iteration of an exciting software engineering life. The product manager has assigned you a few new tasks. You cannot wait to start!

You are required to create a secured restful API to create and list articles
- To create an article the user should provide a title, author, the content and date for publishing.
- All of the fields are mandatory and the title should not exceed 100 characters. The publishing date should bind to ISO 8601 format.
- Article results should be paginated.

Additionally, you are required to develop an endpoint for statistics that would be accessible only by admins.
- The endpoint should return count of published articles on daily bases for the 7 days

Please also write tests to verify that your code works.


Swagger-ui: http://localhost:8080/swagger-ui/index.html

To run, you need to 
> `npm install`
>
> `build_DEV`
> 
> `mvn clean spring-boot:run`