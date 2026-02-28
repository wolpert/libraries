# CodeHead Libraries

## Purpose

Top-level repository for the CodeHead open-source libraries.

## Reason

I switched to a monorepo repository simply to make it easier
on me to do updates. The independent services I'm writing will
still be in their own repos, but there are too many libraries for
me to keep track of them all without a proper CI/CD pipeline. 

Or to be more blunt, I'm lazy.

## Java support

Requires JDK 21 or higher.

## Installation

All libraries are available on maven central.

| Library              | Purpose                            | Version                                                                                          |
|----------------------|------------------------------------|--------------------------------------------------------------------------------------------------|
| codehead-test        | Testing libraries                  | ![CodeHead Test](https://img.shields.io/maven-central/v/com.codeheadsystems/codehead-test?label=codehead-test)       |
| database-test        | Database testing libraries         | ![Database Test](https://img.shields.io/maven-central/v/com.codeheadsystems/database-test?label=database-test)       |
| feature-flag         | Feature Flag                       | ![Feature Flag](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag?label=feature-flag)         |
| feature-flag-ddb     | Feature Flag DDB Support           | ![Feature Flag DDB](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-ddb?label=feature-flag-ddb)     |
| feature-flag-etcd    | Feature Flag etcd Support          | ![Feature Flag Etcd](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-etcd?label=feature-flag-etcd)    |
| feature-flag-metrics | Feature Flag metrics support       | ![Feature Flag Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-metrics?label=feature-flag-metrics) |
| feature-flag-sql     | Feature Flag SQL support           | ![Feature Flag SQL](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-sql?label=feature-flag-sql)     |
| local-queue          | Local Queue                        | ![Local Queue](https://img.shields.io/maven-central/v/com.codeheadsystems/local-queue?label=local-queue)           |
| metrics              | Metrics                            | ![Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics?label=metrics)                   |
| metrics-declarative  | Metrics declarative style          | ![Metrics Declarative](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics-declarative?label=metrics-declarative)       |
| metrics-micrometer   | Metrics with micrometer support    | ![Metrics Micrometer](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics-micrometer?label=metrics-micrometer)        |
| metrics-test         | Metrics test code                  | ![Metrics Test](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics-test?label=metrics-test)              |
| oop-mock             | Out of process mocking             | ![Oop Mock](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock?label=oop-mock)                 |
| oop-mock-client      | Out of process mocking client      | ![Oop Mock Client](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock-client?label=oop-mock-client)          |
| oop-mock-dynamodb    | Out of process mocking ddb support | ![Oop Mock DynamoDB](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock-dynamodb?label=oop-mock-dynamodb)        |
| oop-mock-tests       | Out of process mocking tests       | ![Oop Mock Tests](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock-tests?label=oop-mock-tests)           |
| smr                  | State Machine Library              | ![State Machine Redux](https://img.shields.io/maven-central/v/com.codeheadsystems/smr?label=smr)           |
| smr-metrics          | State Machine Metrics addon        | ![State Machine Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/smr-metrics?label=smr-metrics)   |
| smr-yml              | State Machine YAML support         | ![State Machine YML](https://img.shields.io/maven-central/v/com.codeheadsystems/smr-yml?label=smr-yml)       |
