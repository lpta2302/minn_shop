```mermaid

flowchart LR

home --> search
home --> categoryFilter[category filter]
home --> cart
home --> login
home --> register
register --> login

login --> manageAccount[manage account]
login --> orders
login --> voucher
login --> notification
login --> buy

buy --> takeOrder[take order]
takeOrder --prepay--> unpaid
unpaid --> prepareProduct
takeOrder --"pay later"--> prepareProduct
prepareProduct --> shipping
shipping --> recept
recept --> return
recept --> completed 
recept --> review
review --> completed

```