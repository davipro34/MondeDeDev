![](https://github.com/davipro34/MondeDeDev/assets/images/logo_mdd.png)

# Monde De Dev (MDD)

## Description

MDD is a modern web application and social network designed to help developers find jobs through networking, encouraging connections and collaboration among peers with common interests. The application includes a secure back-end and an interactive front-end.

## Features

### Back-end
- **Comment Management**: Full CRUD for comments.
- **Security**: Integrates Spring Security for authentication and authorization.
- **CORS Configuration**: Supports cross-origin requests.
- **OAuth2 Integration**: Secure authentication via OAuth2.

### Front-end
- **Modern User Interface**: Uses Angular Material for a sleek interface.
- **Responsive Design**: Adapts to all screen sizes.
- **Internationalization**: Ready for internationalization with Angular i18n.
- **Unit Testing**: Integration with Karma and Jasmine for robust tests.

## Installation and Configuration

### Prerequisites
- **Back-end**:
  - Java 21
  - Maven
  - MySQL (or any database compatible with Spring Data JPA)
- **Front-end**:
  - Node.js (recommended version: latest LTS)
  - npm (included with Node.js)
  - Angular CLI: `npm install -g @angular/cli`

### Installation

#### Back-end
1. Clone the application repository:
   ```bash
   git clone https://github.com/davipro34/MondeDeDev
   cd MondeDeDev/back
   ```
2. Configure the database by editing the `src/main/resources/application.properties` file.
   - Some environment variables need to be set. Refer to the `application.properties` file for details.
3. Build the application's `.jar` file:
   ```bash
   ./mvnw clean install
   ```
4. Create the database and tables:
   - Run the SQL script located at `/assets/db/mdd_db_create_database_v1.7.sql` to create the database, tables, and insert sample data.
   - Optional: Use Docker to install the database with the `docker-compose.yaml` file located in `/assets/db/`.

#### Front-end
1. Clone the application repository:
   ```bash
   git clone https://github.com/davipro34/MondeDeDev
   cd MondeDeDev/front
   ```
2. Install dependencies:
   ```bash
   npm install
   ```

### Configuration

#### Back-end
- Edit `src/main/resources/application.properties` with your database details and configure the necessary environment variables.

#### Front-end
- **Development Environment**: Configure variables in `src/environments/environment.ts`.
- **Production Environment**: Modify `src/environments/environment.prod.ts`.

### Starting the Application

#### Back-end
- Start the application:
  ```bash
  java -jar target/mdd-api-0.0.1-SNAPSHOT.jar
  ```

#### Front-end
- To start in development mode:
  ```bash
  npm start
  ```
- To build for production:
  ```bash
  npm run build
  ```

## Technologies Used

### Back-end
- **Spring Boot**: Framework for microservices.
- **Spring Security**: Manages authentication and authorization.
- **Spring Data JPA**: Database integration and entity management.
- **OAuth2**: Token-based authentication.
- **Maven**: Dependency management and project building.

### Front-end
- **Angular**: Framework for dynamic web applications.
- **Angular Material**: UI component library.
- **TypeScript**: Typed programming language.
- **Karma and Jasmine**: Tools for unit testing.
- **SCSS**: CSS preprocessor.

For more information, refer to the official documentation of the mentioned technologies.
