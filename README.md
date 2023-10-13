# StackTomeTask

A testing task for the StackTome company using Scala stack

## 🏁 Getting Started <a name = "getting_started"></a>

## Prerequisites

The system is running on Docker environment.

---

Clone project to your local machine.

```
git clone https://github.com/smagliy/StackTomeTask
```

## Setting up environment

In order to start docker environment, you need to build spark image:

```
docker build -f ./docker/Dockerfile -t scala-container:1.0.0 .
```

You're prepared to begin utilizing Docker. Afterwards, we execute this container using the command:

```
docker run --name scala-container -i -v $(pwd)/results:/opt/my-scala-app/results -d scala-container:1.0.0
```

## Project Directories

This project includes two main directories for data storage and processing:

### `./results/companies`

- This directory contains data for the top 10 companies from every category on trustpilot.com.

### `./results/sort`

- In this directory, you can find the sorted results based on traffic and the latest review count.


