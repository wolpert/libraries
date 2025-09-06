# CodeHead Libraries

## Purpose

Top-level repository for the CodeHead open-source libraries.

## Reason

I switch to a monorepo repository simply to make it easier
on me to do updates. The independent services I'm writing will
still be in their own repos, but there are too many libraries for
me to keep track of them all without a proper CI/CD pipeline. 

Or to be more blunt, I'm lazy.

## Java support

Requires JDK 17 or higher.

## Installation

All libraries are available on maven central.

| Library              | Purpose                            | Version                                                                                          |
|----------------------|------------------------------------|--------------------------------------------------------------------------------------------------|
| codehead-test        | Testing libraries                  | ![CodeHead Test](https://img.shields.io/maven-central/v/com.codeheadsystems/codehead-test)       |
| database-test        | Database testing libraries         | ![Database Test](https://img.shields.io/maven-central/v/com.codeheadsystems/database-test)       |
| feature-flag         | Feature Flag                       | ![Feature Flag](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag)         |
| feature-flag-ddb     | Feature Flag DDB Support           | ![Feature Flag](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-ddb)     |
| feature-flag-etcd    | Feature Flag etcd Support          | ![Feature Flag](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-etcd)    |
| feature-flag-metrics | Feature Flag metrics support       | ![Feature Flag](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-metrics) |
| feature-flag-sql     | Feature Flag SQL support           | ![Feature Flag](https://img.shields.io/maven-central/v/com.codeheadsystems/feature-flag-sql)     |
| local-queue          | Local Queue                        | ![Local Queue](https://img.shields.io/maven-central/v/com.codeheadsystems/local-queue)           |
| metrics              | Metrics                            | ![Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics)                   |
| metrics-declarative  | Metrics declarative style          | ![Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics-declarative)       |
| metrics-micrometer   | Metrics with micrometer support    | ![Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics-micrometer)        |
| metrics-test         | Metrics test code                  | ![Metrics](https://img.shields.io/maven-central/v/com.codeheadsystems/metrics-test)              |
| oop-mock             | Out of process mocking             | ![Oop Mock](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock)                 |
| oop-mock-client      | Out of process mocking client      | ![Oop Mock](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock-client)          |
| oop-mock-dynamodb    | Out of process mocking ddb support | ![Oop Mock](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock-dynamodb)        |
| oop-mock-tests       | Out of process mocking tests       | ![Oop Mock](https://img.shields.io/maven-central/v/com.codeheadsystems/oop-mock-tests)           |
| smr                  | 5tate Machine Library              | ![State Machine Redux](https://img.shields.io/maven-central/v/com.codeheadsystems/smr)           |
| smr-metrics          | 5tate Machine Metrics addon        | ![State Machine Redux](https://img.shields.io/maven-central/v/com.codeheadsystems/smr-metrics)   |
| smr-yml              | 5tate Machine YAML support         | ![State Machine Redux](https://img.shields.io/maven-central/v/com.codeheadsystems/smr-yml)       |
