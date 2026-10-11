FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY out/ /app/

EXPOSE 8080

# Install wget for healthcheck (Alpine)
RUN apk add --no-cache wget

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8080/ || exit 1

ENTRYPOINT ["java", "-cp", "/app", "com.junaid.portfolio.PortfolioServer", "8080"]
