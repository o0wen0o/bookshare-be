## System Architecture Design
![System_Architecture_Design drawio](https://github.com/o0wen0o/bookshare-be/assets/107589285/8d86db6f-bd86-4630-9985-69df93fa9b39)

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
