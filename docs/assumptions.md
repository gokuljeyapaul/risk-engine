##### Semantics #####

- If the amount is less than 10, it should always be accepted
- If the amount is higher than 1000, it should always be rejected with the reason “amount”
- If the sum of purchases from a particular email is larger than 1000 (including current purchase), it should be rejected with reason “debt”

##### Assumptions & Limitations #####

- The semantic #1 and semantic#3 are contradicting. Hence, the assumption made here is to block any transaction beyond the max threshold(1000) however low the transaction amount is. This is a greedy way but avoids a lot of risk. Hence, a transaction with any amount below 1000 is set to pass through.
- Email is assumed to be the primary reference or the unique identifier for a customer.
- The validations on blacklisted email IDs and blacklisted email domains are not implemented.
- A minimal in-memory data store is being used to maintain the cumulative amount of transactions made and the customer information. Hence, restarts result in loss of data. This is something not really advisable in real time or production implementations.
- No [JSR-94](https://jcp.org/en/jsr/detail?id=94) standard or industry standard rule-engine has been used for this project as the rules were simple and a full fledged rule engine will be an overkill.
- All logs have been removed to provide clarity for review.
- The project is developed with multiple threads in mind.