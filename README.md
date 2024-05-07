Two approaches: 

1. Apollo Gateway. 
- Apollo Gateway is a GraphQL server that enables you to compose multiple GraphQL services into a single data graph.
- It is a layer that sits between the client and the services that provide the data.
- It is a single endpoint that clients can query to get data from multiple services.

in our case: 
a. start accounts service (e.g. bootrun in the accounts service) and wait til its up
b. start transaction service (e.g. bootrun in the transaction service) and wait til its up (might take a bit)
c. start apollo gateway (node index.js in the apollo-gateway folder)
d. go to http://localhost:4000/ and run the following query: 
```
query accountsByBic($bic: String, $page: Int,$pageSize: Int){
   accountsByBic(bic: $bic, page: $page, pageSize: $pageSize) {
    iban
    transactions {
      bookingText
    }
  }
}
```
here we can see that the transactions field is being resolved by the transaction service whereas the iban field is being resolved by the accounts service.

The accounts service holds 8 million accounts and the transaction service holds 40 million transactions over 100k accounts. Those numbers can be adjusted in the properties of the respective services.

