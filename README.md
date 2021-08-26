### How to run application

Run script `docker/build_and_run.sh`.

It builds application, creates docker image locally under `pugs-api` name and uses `docker-compose.yml` to run the service.

### About application

#### API

API is available at `/api/balance?usernames=<list of usernames>`, e.g.: `/api/balance?usernames=user,user2`

#### Adding users, authentication

In order to define users' credentials and initial balance add them to `application.yml` under `users.users`. 

App uses stateless basic authentication. Auth mechanism uses plain string password - that would be unacceptable in production of course.

#### Architecture

I've decided to use Hexagonal Architecture. Both `adapters` and `domain` are separate gradle modules.
