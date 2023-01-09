# CPSC304 Project

Setup Guide:
The database initialization is under sql/scripts/databaseSetup.sql. Our database is using mySQL as the DBMS. For the purpose of running this application successfully, you need mySQL to be installed on your machine. You then import the database and run it on the local server using the CLI or mySQL Workbench.

Please make sure to change the settings in DatabaseConnectionHandler to match your server setup:
1. url: make sure that you use the correct port and database name
2. dbUser: make sure that it matches your server username settings
3. dbPassword: make sure that it matches your server password settings