# OpenHouse.ai Assessment

Backend Assessment - retrieving logs

## How to build and run

1. The following are required to build and run this project:

   - JDK version 11.0.11 or above
   - Maven version 3.6.3 or above (if not using maven wrapper)
   - MySQL server version 8.0.29 or above

2. Connect to your MySQL server using an admin user like `root`, and run the script [`sql/init.sql`](sql/init.sql) to setup the database. This script will create a schema `ohai_db`, a user `ohai_app` with password `ohai_password`.

3. Run the command from project's root to launch the application. This will launch the application on `8080` port.

   ```bash
   $ mvn clean quarkus:dev
   ```

4. The API can be accessed using the URL `http://localhost:8080/api`.

## Storing Logs

- Make a post call to `/api/store` with one log entry as payload. For example:

  ```bash
  $ curl -X POST -H "Content-Type: application/json" -d  '{
  "userId": "ABC123XYZ",
  "sessionId": "XYZ456ABC",
  "actions": [
     {
        "time": "2018-10-18T21:37:28-06:00",
        "type": "CLICK",
        "properties": {
        "locationX": 52,
        "locationY": 11
        }
     },
     {
        "time": "2018-10-18T21:37:30-06:00",
        "type": "VIEW",
        "properties": {
        "viewedId": "FDJKLHSLD"
        }
     },
     {
        "time": "2018-10-18T21:37:30-06:00",
        "type": "NAVIGATE",
        "properties": {
        "pageFrom": "communities",
        "pageTo": "inventory"
        }
     }
  ]
  }' http://localhost:8080/api/store
  ```

- Make a post call to `/api/storeall` with array of log entries as payload. For example:

  ```bash
  $ curl -X POST -H "Content-Type: application/json" -d  '[
  {
     "userId": "USER123",
     "sessionId": "SESSION123",
     "actions": [
        {
        "time": "2022-06-01T21:37:28-06:00",
        "type": "CLICK",
        "properties": {
           "locationX": 52,
           "locationY": 11
        }
        },
        {
        "time": "2022-06-01T21:37:30-06:00",
        "type": "VIEW",
        "properties": {
           "viewedId": "QWERTY"
        }
        },
        {
        "time": "2022-06-01T21:37:30-06:00",
        "type": "NAVIGATE",
        "properties": {
           "pageFrom": "page1",
           "pageTo": "page2"
        }
        }
     ]
  },
  {
     "userId": "USER123",
     "sessionId": "SESSION456",
     "actions": [
        {
        "time": "2022-06-15T21:37:28-06:00",
        "type": "CLICK",
        "properties": {
           "locationX": 52,
           "locationY": 11
        }
        },
        {
        "time": "2022-06-15T21:37:30-06:00",
        "type": "VIEW",
        "properties": {
           "viewedId": "ASDFG"
        }
        },
        {
        "time": "2022-06-15T21:37:30-06:00",
        "type": "NAVIGATE",
        "properties": {
           "pageFrom": "page5",
           "pageTo": "page3"
        }
        }
     ]
  },
  {
     "userId": "USER456",
     "sessionId": "SESSION789",
     "actions": [
        {
        "time": "2022-06-25T21:37:28-06:00",
        "type": "CLICK",
        "properties": {
           "locationX": 52,
           "locationY": 11
        }
        },
        {
        "time": "2022-06-25T21:37:30-06:00",
        "type": "VIEW",
        "properties": {
           "viewedId": "ZXCVB"
        }
        },
        {
        "time": "2022-06-25T21:37:30-06:00",
        "type": "NAVIGATE",
        "properties": {
           "pageFrom": "page8",
           "pageTo": "page12"
        }
        }
     ]
  }
  ]' http://localhost:8080/api/storeall
  ```

### Retrieving Logs

- Make a get call to `/api/retrieve/all` to retrieve all logs. For example:

  ```bash
  $ curl -X GET http://localhost:8080/api/retrieve/all
  ```

- Make a get call to `/api/retrieve` with parameters to retrieve logs based on user, time range, or log type. Any combination of these parameters can be used. For example:

  ```bash
  $ curl -X GET http://localhost:8080/api/retrieve?user=USER123&type=NAVIGATE&startTime=2022-06-01T21:37:30-06:00&endTime=2022-06-25T21:37:28-06:00
  ```
