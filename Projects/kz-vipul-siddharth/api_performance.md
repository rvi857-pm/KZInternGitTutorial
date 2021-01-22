| api fetched                                                                                                   | intial time | time after caching |
|---------------------------------------------------------------------------------------------------------------|-------------|--------------------| 
| http://localhost:8080/accounts                                                                                | 1050ms      | 66ms               |
| http://localhost:8080/accounts?city=austin&state=texas&country=United%20states&domain=.com                    | 984ms       | 21ms               |
| http://localhost:8080/accounts?city=austin&state=texas&country=United%20states&domain=.com&page=1&page_size=2 | 1032ms      | 33ms               |
