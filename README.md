# market

### 개발 환경
* Java 11
* SpringBoot 2.5.12
* Gradle
* JPA
* H2 database(embedded)
* Swagger

### Setting
* Server port : 8081

### Swagger
http://localhost:8081/swagger-ui/index.html

### DB
* Address : http://localhost:8081/h2-console
* username : sa
* password : 

### DB 구조

* `Members` 1:N `Orders`
* `Orders` 1:N `OrderProduct` N:1 `Product`

![ERD](https://user-images.githubusercontent.com/60866755/166643595-594e5166-672f-45eb-9737-b9e3c8ee4cff.png)


### Exception Code
> Exceptoin Response
 ```
 {
  "code": Integer,
  "codeName": "String",
  "message": "String.",
  "timestamp": "Date"
}
 ```
> Auth
* `10001` : EMAIL_NOT_FOUND
* `10002` : SIGN_IN_FAIL
* `10003` : WRONG_PWD_FIVE
* `10004` : UNAUTHORIZED
* `10005` : INVALID_TOKEN
* `10006` : ACCOUNT_NOT_FOUND
* `10007` : ACCOUNT_SUSPENSION
* `10008` : ACCOUNT_LOCK
* `10009` : SIGN_UP_FAIL
* `10010` : DUPLICATED_MEMBER
> product
* `20001` : PRODUCT_NOT_FOUND
* `20002` : PRODUCT_DELETED
* `20003` : PRODUCT_CREATE_FAIL
* `20004` : PRODUCT_UPDATE_FAIL
* `20005` : PRODUCT_DELETE_FAIL
* `20006` : PRODUCT_DUPLICATE
> order
* `30001` : PAYMENT_NOT_FOUND
* `30002` : OUT_OF_STOCK
* `30003` : LACK_OF_QUANTITY
* `30004` : WRONG_ORDER_REQUEST
* `30005` : ORDER_NOT_FOUND
* `30006` : ORDER_CANCELED
* `30007` : ORDER_COMPLETED
* `30008` : ORDER_IN_PROGRESS
* `30009` : ORDER_CANCELED_FAIL
* `30010` : WRONG_CANCEL_REQUEST
> internal
* `500` : INTERNAL_ERROR
<br/><br/>
### 개발 컨셉
> - `Interceptor` 와 `JWT`를 기반으로 인증/인가 구현
> - 커스텀 `Annotation`인 `Authenticate`를 구현하여 인증/인가가 필요한 `API`에 추가 
> - Custon Exception Response를 구현하여 Exception 처리

> Product
1. 상품 등록 `Post` `/api/v1/product` 
* RequestBody
```
{
  "category": "string",
  "description": "string",
  "name": "string",
  "price": 0,
  "stock": 0
}
```
* Response(success)
```
{
  "message": "CREATE_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 상품 중복 등록 -> `PRODUCT_DUPLICATE`
  * 상품 등록 실패 -> `PRODUCT_CREATE_FAIL`
<br/><br/>

2. 상품 수정 `Post` `/api/v1/product/{id}` 
* PathVariable
```
Long id;
```
* RequestBody
```
{
  "category": "string",
  "description": "string",
  "name": "string",
  "price": 0,
  "stock": 0
}
```
* Response(success)
```
{
  "message": "UPDATE_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 상품 조회 실패 -> `PRODUCT_NOT_FOUND`
  * 상품 수정 실패 -> `PRODUCT_UPDATE_FAIL`

<br/><br/>
3. 상품 삭제 `Delete` `/api/v1/product/{id}` 
>> 삭제 요청이 발생하면 `deletedTime` 필드를 `null`에서 현재 시간으로 변경
* PathVariable
```
Long id;
```
* Response(success)
```
{
  "message": "DELETE_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 상품 삭제 실패 -> `PRODUCT_DELETE_FAIL`

<br/><br/>
4. 상품 단일 조회 `Get` `/api/v1/product/{id}` 
* PathVariable
```
Long id;
```
* Response(success)
```
{
  "category": "string",
  "description": "string",
  "id": 0,
  "name": "string",
  "price": 0
}
```
* Response(Exception)
  * 상품 조회 실패 -> `PRODUCT_NOT_FOUND`

<br/><br/>
5. 상품 리스트 조회 `Get` `/api/v1/product` 
>> `Paging` 처리
* RequestParam
```
int page; // 1부터 시작
```
* Response(success)
```
{
  "content": [
    {
      "category": "string",
      "id": 0,
      "name": "string"
    }
  ],
  "empty": true,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 0,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 0,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "unpaged": true
  },
  "size": 0,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "totalElements": 0,
  "totalPages": 0
}
```
<br/><br/>
> Member
1. 회원 조회 `Get` `/api/v1/member` 
* Response(success)
```
{
  "email": "string",
  "status": 0,
  "type": "string"
}
```
* Response(Exception)
  * 회원 조회 실패 -> `ACCOUNT_NOT_FOUND`
<br/><br/>

2. 회원 비밀번호 수정 `Post` `/api/v1/member` 
>> 비밀번호 5회 이상 틀려서 잠김 회원 or 비밀버호를 잊어버린 회원 재설정 기능
* RequestBody
```
{
  "email": "string",
  "password": "string"
}
```
* Response(success)
```
{
  "message": "PWD_UPDATE_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 회원 조회 실패 -> `EMAIL_NOT_FOUND`
<br/><br/>

> Auth
1. 회원 등록 `Post` `/api/v1/auth/signUp` 
* RequestBody
```
{
  "email": "string",
  "password": "string"
}
```
* Response(success)
```
{
  "message": "SIGN_UP_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 회원 중복 등록 -> `DUPLICATED_MEMBER`
  * 회원 등록 실패 -> `SIGN_UP_FAIL`
<br/><br/>

2. 로그인 `Post` `/api/v1/auth/signIn` 
* RequestBody
```
{
  "email": "string",
  "password": "string"
}
```
* Response(success)
```
{
  "message": "LOGIN_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 로그인 실패 -> `SIGN_IN_FAIL`
  * 비밀번호 오류 -> `SIGN_IN_FAIL`
  * 비밀번호 5회 이상 오류 -> `WRONG_PWD_FIVE`

<br/><br/>
3. 토큰 유효 확인 `Get` `/api/v1/auth/token/verify` 
>> 토큰 유효한지 확인
>> 자동 로그인을 위한 기능
>> 토큰이 유효하면 `refresh token` 발급
* Response(success)
```
{
  "message": "VERIFY_TOKEN",
  "result": true
}
```
* Response(Exception)
  * 로그인 실패 -> `SIGN_IN_FAIL`
  * 토큰 유효 x -> `INVALID_TOKEN`

<br/><br/>
> Order
1. 주문 등록 `Post` `/api/v1/order`
>> - 지불 수단 등록
>> - 구매할 `product`의 `id` 와 수량 리스트로 요청
>> - 주문하는 회원 조회
>> - 주문이 생성되면 `Event`에 등록해 놓은 알림 로그로 출력
* RequestBody
```
{
  "payment": 1,
  "productList": [
    {
      "count": 0,
      "id": 5
    }
  ]
}
```
* Response(success)
```
{
  "message": "ORDER_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 지불 수단 조회 실패 -> `PAYMENT_NOT_FOUND`
  * 회원 조회 실패 -> `EMAIL_NOT_FOUND`
  * 상품 등록 실패 -> `PRODUCT_CREATE_FAIL`
  * 잘못된 주문 요청 -> `WRONG_ORDER_REQUEST`
  * 재고 부족 -> `LACK_OF_QUANTITY`
  * 내부 오류 -> `INTERNAL_ERROR`
<br/><br/>

2. 주문 삭제 `Post` `/api/v1/product/{id}` 
>> - 부분 취소 가능하게 구현
>> - `주문접수` 상태가 아닐 경우 주문 취소 불가
* PathVariable
```
Long id;
```
* RequestBody
```
{
  "category": "string",
  "description": "string",
  "name": "string",
  "price": 0,
  "stock": 0
}
```
* Response(success)
```
{
  "message": "CANCEL_SUCCESS",
  "result": true
}
```
* Response(Exception)
  * 취소 내용 없는 경우 -> `WRONG_CANCEL_REQUEST`
  * 주문 조회 실패 -> `ORDER_NOT_FOUND`
  * 주문자와 취소자 다를 경우 -> `UNAUTHORIZED`
  * 주문이 진행 중일 경우 -> `ORDER_CANCELED_FAIL`
  * 내부 오류 -> `INTERNAL_ERROR`

<br/><br/>






