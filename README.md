# Decision Engine: recruitment task

A simple **Spring Boot decision engine** that evaluates credit requests and returns a calculated **credit score decision**.  

[Link to the frontend repo](https://github.com/Maxxxxxxxxxxxxxxxxxxxxx/decision-engine-fe)

## Overview

The Decision Engine processes incoming credit requests and evaluates them using a scoring mechanism.

The workflow is:

1. A client sends a **credit request** consisting of: ```userCode```, ```amount```, ```loanPeriod```
2. The **decision engine** evaluates the request. The algorithm:```credit score = (credit modifier / loan amount) * loan period``` has been simplified to: ```amount = credit modifier * loan period```
   
4. The service returns a **credit response** containing the decision, calculated amount and a message.

## Mocked data

The users' data is loaded from ```mock-user-data.json```

```json
[
  {
    "userCode": "1019",
    "creditModifier": 1200.50,
    "credit": {
      "duration": 60,
      "amount": 15000
    }
  },
  {
    "userCode": "1020",
    "creditModifier": 245.50
  },
  {
    "userCode": "1021",
    "creditModifier": 678.30
  },
  {
    "userCode": "1023",
    "creditModifier": 512.75
  },
  {
    "userCode": "1024",
    "creditModifier": 934.10
  },
  {
    "userCode": "1025",
    "creditModifier": 389.95
  }
]
```

User with userCode 1019 is a case of user already having debt.
