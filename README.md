# The Exceptional Outliner

## Contents

-   [About](#about)
-   [Tools and Technologies used](#tools-and-technologies-used)
-   [Implemented features](#implemented-features)
-   [Planned features](#planned-features)

## About

This is the back-end of a web application that uses React, Spring Boot, and MongoDB. This application currently allows users to create accounts, universes, wikis, and todo list items for each. The wiki articles can be about anything within the User's universe, from characters to locations to events. The front end can be found [here](https://github.com/reishaleem/exceptional-wiki-front-end).

## Tools and Technologies used

In this back-end, the key technologies used are:

-   Spring Boot
-   MongoDB. This is implemented using Spring Data MongoDB.
-   JwT and Spring Security. This is used for User authentication and storing passwords.
-   Lombok. Lots of useful annotations to make life easier.

## Implemented features

So far, the features implemented in this back-end are:

-   CRUD operations for users. This includes validation on User members, such as passwords and ensuring no duplicate usernames and emails.
-   CRUD operations for Universes and Wikis. A Wiki will always belong to one and only one Universe, and a Universe will always belong to one and only one User.
-   CRUD operations for a todo list (implemented as TaskList), which will be tied to a Universe. The update operations include creating tasks for a certain list.

## Planned features

Some future features that will be implemented are:

-   CRUD operations on several models, such as Maps and Manuscripts, among many others.
-   Implement sending an password reset email to the user
-   The Generator
