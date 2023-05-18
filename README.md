# Recipe App
The Recipe App is designed to serve as a platform for users to access a wide range of recipes, cuisines, and cooking tips. It consists of two main projects that work together to provide a comprehensive user experience.

## 1. Recipe Admin
The Recipe Admin project is a REST API built using Spring Boot and PostgreSQL. Its user interface is developed using Angular and Angular Material, with the UI state managed by NgRx. The purpose of this project is to allow authorized admins to manage recipes. When a recipe's status is published, it is stored in MongoDB. This sets the stage for the second part of the application.

## 2. Recipe
The Recipe project is the end-user-facing application developed using Angular SSR (Server-Side Rendering) and Firebase. Firebase is utilized for authentication as well as features such as recipe bookmarking and favoriting. Additionally, two serverless APIs, namely getAllRecipe and reviews, are created using Spring and deployed to AWS. These APIs, which utilize MongoDB, are used during the deployment of the Recipe application. Notably, the serverless APIs are specifically utilized during deployment, and the recipes published by Recipe Admin are saved to MongoDB. The deployment of the Recipe app is scheduled to occur on a fixed interval, typically every 24 to 48 hours.
