# Java-DevOps-Portfolio

A clean, professional portfolio website for **Junaid Junaid** — Computer Science student & DevOps enthusiast. Built with **Java** (JDK built-in `HttpServer`, zero dependencies) and a modern responsive frontend.

## Features

- **Zero-dependency Java backend** — uses `com.sun.net.httpserver.HttpServer`, compiles with plain `javac`, no Maven/Gradle required.
- **Modern dark-theme UI** with DevOps/cloud aesthetic, smooth animations, and a typing effect.
- **Responsive design** — works on desktop, tablet, and mobile.
- **Sections**: Hero, About, Skills (technical + soft), Projects, Experience timeline, Education, Certifications, Contact.
- **Working contact form** — submits to a `/api/contact` endpoint with validation.
- **Dockerfile included** for easy containerization and CI/CD deployment.

## Project Structure

```
Java-DevOps-Portfolio/
├── src/main/java/com/junaid/portfolio/
│   ├── PortfolioServer.java          # Main entry point (HttpServer)
│   ├── handler/
│   │   └── PortfolioHandler.java     # Static file serving + /api/contact
│   └── model/
│       └── ContactMessage.java       # Contact form model + validation
├── src/main/resources/web/
│   ├── index.html                    # Portfolio page
│   ├── css/styles.css                # Styling
│   └── js/main.js                    # Interactivity
├── build.bat / run.bat               # Windows build & run scripts
├── Dockerfile                        # Container image
└── README.md
```

## Prerequisites

- Java 17+ (built and tested with JDK 25)

## Build & Run

### Windows

```bat
build.bat
run.bat
```

The server starts on **http://localhost:8080** by default. To use a different port:

```bat
run.bat 8090
```

### Manual (cross-platform)

```bash
# Build
mkdir -p out
javac -d out -encoding UTF-8 \
  src/main/java/com/junaid/portfolio/model/ContactMessage.java \
  src/main/java/com/junaid/portfolio/handler/PortfolioHandler.java \
  src/main/java/com/junaid/portfolio/PortfolioServer.java
cp -r src/main/resources/web out/

# Run
java -cp out com.junaid.portfolio.PortfolioServer 8080
```

## Docker

```bash
# Build image
docker build -t junaid-portfolio .

# Run container
docker run -p 8080:8080 junaid-portfolio
```

Visit **http://localhost:8080** in your browser.

## Contact

- **Email**: junaidj1266@gmail.com
- **GitHub**: [github.com/junaid2676](https://github.com/junaid2676)
- **Location**: Cambridge, UK
