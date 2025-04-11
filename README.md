# Test Social Network "Backend"

> [!CAUTION]
> This is a project created for me to learn to be a Web Developer, this project isn't intended for usual use.

## How to implement this project
### Dependencies:
| Dependency | Version Tested |
|------------|----------------|
| MySQL      | 9.2.0          |
| Tomcat     | 11.2.0         |

> [!WARNING]
> This project was created with **MySQL 9.2.0** In mind, I didn't test 8.x.x/5.x.x, Proceed with caution if you're using those versions. I probably check if it works with those versions

### Installation methods.
#### Manual Installation
Install a **Tomcat 11.2.0** Server.

Pull this repository and execute
```bash
mvn clean compile package
```

then you'll find the WAR file in `target/`, just move it to your webapps folder, Tomcat will automatly deploy it.

> [!NOTE]
> You'll need to run your Tomcat server with **Environment Variables**, Go to **Environment Variables** to see them.
> Otherwise, it'll crash

#### For Docker Users
You can download the latest image from Github Package Registry, Github Actions will update that image every time I push something to this repository.

This image contains **Tomcat 11.2.0** and the backend installed at ROOT.war
```bash
docker pull ghcr.io/dsaub/redsocial-backend:latest
```

> [!NOTE]
> You'll need to run the image with **Environment Variables**, Go to **Environment Variables** to see them.
> Otherwise, it'll crash

### Environment Variables
| Variable | Description                                                   | Example |
|----------|---------------------------------------------------------------|---------|
| JDBC_URL | JDBC Url for the database. Should already point to a database | jdbc:mysql://(host):(port)/(database) |
| JDBC_USER | MySQL Username, Should have permissions for the database     | root |
| JDBC_PASS | MySQL Password | toor |

## Warnings
> [!WARNING]
> MySQL Should be running when you initialize tomcat server, as it needs to deploy tables.