# Baloot

## Baloot - University of Tehran Internet Engineering Course Project

## Commands:

<br/>

```
addUser {"username": string, "password": string, "email": string, "birthDate": string, "address": string, "credit": number}
```

#### example:

```
addUser {"username": "user1", "password": "1234", "email": "user@gmail.com", "birthDate": "1977-09-15", "address": "address1", "credit": 1500}
```

<hr/>
<br/>

```
addProvider {"id": number, "name": string, "registryDate": string}
```

#### example:

```
addProvider {"id": 1, "name": "provider1", "registryDate": "2023-09-15"}
```

<hr/>
<br/>

```
addCommadity {"id": number, "name": string, "providerId": int, "price": number, "categories": string[], "rating": number, "inStock": number}
```

#### example:

```
addCommadity {"id": 1, "name": "Headphone", "providerId": 3, "price": 3500, "categories": ["Technology", "Phone"], "rating": 8.8, "inStock": 50}
```

<hr/>
<br/>

```
getCommoditiesList
```

<hr/>
<br/>

```
rateCommodity {"username": string, "commodityId": number, "score": number}
```

#### example:

```
rateCommodity {"username": "user1", "commodityId": 3, "score": 7}
```

<hr/>
<br/>

```
addToBuyList {"username": string, "commodityId": number}
```

#### example:

```
addToBuyList {"username": "user1", "commodityId": 4}
```

<hr/>
<br/>

```
removeFromBuyList {"username": string, "commodityId": number}
```

#### example:

```
removeFromBuyList {"username": "user1", "commodityId": 7}
```

<hr/>
<br/>

```
getCommodityById {"id": number}
```

#### example:

```
getCommodityById {"id": 2}
```

<hr/>
<br/>

```
getCommoditiesByCategory {"category": string}
```

#### example:

```
getCommoditiesByCategory {"category": "Vegetables"}
```

<hr/>
<br/>

```
getBuyList {"username": string}
```

#### example:

```
getBuyList {"username": "user1"}
```
