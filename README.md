# pizzeria_palmetto

A small learning project for a pizzeria: a Spring MVC REST application packaged as a WAR and deployed to Apache Tomcat.
It is configured with Java config (`WebAppInitializer` + `AppConfig`, no `web.xml`) and uses an in-memory H2 database
through plain JDBC.

## Stack

- Java 21, Maven
- Spring Framework 7 (Spring MVC), Jackson 3 for JSON
- Apache Tomcat 11 (Jakarta Servlet 6.1)
- H2 (in-memory), SLF4J + Logback
- Lombok

## Environment setup

### Prerequisites

| Tool   | Version | Notes                                                                                                               |
|--------|---------|---------------------------------------------------------------------------------------------------------------------|
| JDK    | 21      | Used for both Maven and Tomcat. Tomcat must run on 21, otherwise the app fails with `UnsupportedClassVersionError`. |
| Maven  | 3.9+    | Must run on JDK 21 (`mvn -v` shows the Java version).                                                               |
| Tomcat | 11.x    | Tomcat 9/10 will not work (Spring 7 needs the `jakarta.*` namespace and Servlet 6.1).                               |

### Environment variables

macOS / Linux (add to `~/.zshrc` or `~/.bashrc`):

```bash
export JAVA_HOME=/path/to/jdk-21
export CATALINA_HOME=/path/to/apache-tomcat-11.x.x
export PATH="$JAVA_HOME/bin:$PATH"
```

Windows (PowerShell, persistent; reopen the terminal afterwards):

```powershell
setx JAVA_HOME "C:\Program Files\Java\jdk-21"
setx CATALINA_HOME "C:\tools\apache-tomcat-11.0.29"
```

Verify with `java -version` (must print 21) and `mvn -v`.

## Build

```bash
mvn clean package
```

This produces `target/pizzeria-palmetto.war`.

## Deploy to Tomcat

The deploy scripts stop Tomcat, remove the previous deployment, copy the new WAR into `webapps` and start Tomcat again.
Pass `--build` to run the Maven build first.

macOS / Linux:

```bash
./deploy.sh --build
```

Windows (cmd or PowerShell):

```bat
deploy.bat --build
```

`deploy.sh` defaults to JDK 21 and Tomcat paths from the author's machine; override them with `JAVA_HOME` /
`CATALINA_HOME`. `deploy.bat` requires both variables to be set.

## Endpoints

The context path is the WAR name, so the base URL is `http://localhost:8080/pizzeria-palmetto`.

| Method | Path                   | Description                                                     |
|--------|------------------------|-----------------------------------------------------------------|
| GET    | `/v1/orders`           | List all orders                                                 |
| GET    | `/v1/orders/{orderId}` | Get an order by id (400 for a non-numeric id, 404 if not found) |

Two demo customers (`sevak`, `Roland`) and their orders are created at startup.

## Logging

Logback is configured in `src/main/resources/logback.xml`. Logs go to the console (`$CATALINA_BASE/logs/catalina.out`)
and to `$CATALINA_BASE/logs/palmetto.log` (rolled daily, 14 days kept). Unhandled exceptions are logged with a stack
trace by `GlobalExceptionHandler`.

## Troubleshooting

- **Tomcat starts but endpoints return 404**: check the newest `logs/catalina.*.log` for a
  `SEVERE ... Context initialization failed` entry. The most common cause is running Tomcat on an older Java than the
  one used to build.
- **`UnsupportedClassVersionError ... class file version 65.0`**: Tomcat is running on Java older than 21. Set
  `JAVA_HOME` to JDK 21 and restart it.
- **`invalid target release: 21` when building**: Maven is using an older JDK; set `JAVA_HOME` to JDK 21.
- **Wrong Tomcat started**: confirm `CATALINA_HOME` points to Tomcat 11 (`echo $CATALINA_HOME` /
  `echo %CATALINA_HOME%`).
