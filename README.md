# Interview-Task
Simple Rest application.

Category Entity end points:

Get all categories -  method: GET     url: /api/category  
Get category by id -  method: GET     url: /api/category/{id}     
Create category -     method: POST    url: /api/category    body: {"name":"name here"}
Update category -     method: PATCH   url: /api/category    body: {"name":"name here"}
Delete category -     method: DELETE  url: /api/category/{id}    

Product Entity end points:

Get all products -    method: GET     url: /api/product  
Get product by id -   method: GET     url: /api/product/{id}     
Create product  -     method: POST    url: /api/product    body: {"name":"name here","description":"description text here","price":"price here","categoryId":"category id" }
Update category -     method: PUT     url: /api/product    body: {"id":"id here","name":"name here","description":"description text here","price":"price here","categoryId":"category id"}
Delete category -     method: DELETE  url: /api/product/{id}
