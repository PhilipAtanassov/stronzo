#Stronzo

nothing to see here ... go on with your life

## What is this
An imaginary project where people can report issues e.g. to the municipality.

## What to do with it
1. Run it in intelliJ
2. Create some issues:
```shell
curl -X PUT \
     --header "Content-Type: application/json" \
     "http://localhost:8080/issues" \
    -d '{"description": "ala bala" }'
```
3. See them in a browser by visiting http://localhost:8080/issues
4. Visit the H2 console if you so insist. There is a line in the output similar to:
```
H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:fbb76bc0-0ae1-4c09-9df4-559677b79ebb'
```


## What's next
- input validation
- tests
- auth
- swagger?