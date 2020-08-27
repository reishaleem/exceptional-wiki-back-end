# The Exceptional Outliner
## Contents
- [About](#about)
- [Tools and Technologies used](#tools-and-technologies-used)
- [Implemented features](#implemented-features)
- [Planned features](#planned-features)

## About
This is the back-end of a web application that uses React, Spring Boot, and MongoDB. This application currently allows users to create accounts in order to create wiki-styled articles for aspects of their stories. The articles can be about anything, from characters to locations to events. The front end can be found [here](https://github.com/reishaleem/exceptional-wiki-front-end).

## Tools and Technologies used
In this back-end, the key technologies used are:
- Spring Boot
- MongoDB. This is implemented using Spring Data MongoDB.
- JwT and Spring Security. This is used for User authentication and storing passwords.
- Lombok. Lots of useful annotations to make life easier.

## Implemented features
So far, the features implemented in this back-end are:
- CRUD operations for users. This includes validation on User members, such as passwords and ensuring no duplicate usernames and emails.

## Planned features
Some future features that will be implemented are:
- CRUD operations on several models, including Universes, Wikis, Maps, and much more. 
- Implement sending an password reset email to the user