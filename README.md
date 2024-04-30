## How to Build

### Build a Jar package

```console
mvn clean package
```

### Build with Docker

After you built the jar file, run this:

```console
docker build -t spring/bookshare-be .
```

To run all services:

```console
docker-compose up
```
