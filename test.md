```mermaid

flowchart LR

Register --> Login
Login --> Admin((Admin))
Login --> Driver((Driver))
Login --> Customer((Customer))

Customer --> MakeRequest[make request]

Driver --> Search
Search --> NewRequest((New request))
CompletedOrder --> Review
Search --> CompletedOrder((Completed Delivery))

MakeRequest --> NewRequest

Driver --> TakeRequest
NewRequest --> TakeRequest[Take request]
TakeRequest --> Delivery((Delivery))
Delivery --> DoDelivery[Do Delivery]

DoDelivery --> CompletedOrder((Completed Order))

```