# POS System API Documentation
## Base URL
`http://localhost:8080/posbackend`

## Overview
The POS System API allows clients to manage orders, customers, and items in a point-of-sale system. The API supports operations such as creating, retrieving, updating, and deleting orders, as well as managing items and customers.

---

## Authentication
This API does not require authentication. Ensure that appropriate security measures are implemented in a production environment.

---

## Endpoints

### Orders

#### 1. **Get All Orders**
   - **Method:** `GET`
   - **URL:** `/orders`
   - **Description:** Retrieves all orders from the database.
   - **Response:**
     ```json
     [
       {
         "orderId": "O001",
         "customerId": "C001",
         "customerName": "John Doe",
         "items": [
           {
             "itemId": "I001",
             "itemName": "Item 1",
             "quantity": 2,
             "price": 500.00
           }
         ],
         "total": 1000.00,
         "date": "2024-08-25"
       },
       ...
     ]
     ```

#### 2. **Get Order by ID**
   - **Method:** `GET`
   - **URL:** `/order/{orderId}`
   - **Description:** Retrieves a specific order by its ID.
   - **Path Parameter:**
     - `orderId` (string): The ID of the order to retrieve.
   - **Response:**
     ```json
     {
       "orderId": "O001",
       "customerId": "C001",
       "customerName": "John Doe",
       "items": [
         {
           "itemId": "I001",
           "itemName": "Item 1",
           "quantity": 2,
           "price": 500.00
         }
       ],
       "total": 1000.00,
       "date": "2024-08-25"
     }
     ```

#### 3. **Create New Order**
   - **Method:** `POST`
   - **URL:** `/order`
   - **Description:** Creates a new order in the system.
   - **Request Body:**
     ```json
     {
       "orderId": "O002",
       "customerId": "C002",
       "itemDtoList": [
         {
           "itemId": "I001",
           "quantity": 2,
           "price": 500.00
         }
       ],
       "total": 1000.00,
       "date": "2024-08-25"
     }
     ```
   - **Response:**
     ```json
     {
       "status": "Order created successfully"
     }
     ```

#### 4. **Update Order**
   - **Method:** `PUT`
   - **URL:** `/order/{orderId}`
   - **Description:** Updates an existing order.
   - **Path Parameter:**
     - `orderId` (string): The ID of the order to update.
   - **Request Body:**
     ```json
     {
       "customerId": "C002",
       "itemDtoList": [
         {
           "itemId": "I001",
           "quantity": 3,
           "price": 500.00
         }
       ],
       "total": 1500.00,
       "date": "2024-08-26"
     }
     ```
   - **Response:**
     ```json
     {
       "status": "Order updated successfully"
     }
     ```

#### 5. **Delete Order**
   - **Method:** `DELETE`
   - **URL:** `/order/{orderId}`
   - **Description:** Deletes an order by its ID.
   - **Path Parameter:**
     - `orderId` (string): The ID of the order to delete.
   - **Response:**
     ```json
     {
       "status": "Order deleted successfully"
     }
     ```

### Items

#### 6. **Get All Items**
   - **Method:** `GET`
   - **URL:** `/items`
   - **Description:** Retrieves all items available in the POS system.
   - **Response:**
     ```json
     [
       {
         "itemId": "I001",
         "itemName": "Item 1",
         "price": 500.00,
         "quantity": 100,
         "description": "Item 1 description"
       },
       ...
     ]
     ```

#### 7. **Get Item by ID**
   - **Method:** `GET`
   - **URL:** `/item/{itemId}`
   - **Description:** Retrieves a specific item by its ID.
   - **Path Parameter:**
     - `itemId` (string): The ID of the item to retrieve.
   - **Response:**
     ```json
     {
       "itemId": "I001",
       "itemName": "Item 1",
       "price": 500.00,
       "quantity": 100,
       "description": "Item 1 description"
     }
     ```

#### 8. **Create New Item**
   - **Method:** `POST`
   - **URL:** `/item`
   - **Description:** Adds a new item to the POS system.
   - **Request Body:**
     ```json
     {
       "itemId": "I002",
       "itemName": "Item 2",
       "price": 750.00,
       "quantity": 50,
       "description": "Item 2 description"
     }
     ```
   - **Response:**
     ```json
     {
       "status": "Item added successfully"
     }
     ```

#### 9. **Update Item**
   - **Method:** `PUT`
   - **URL:** `/item/{itemId}`
   - **Description:** Updates the details of an existing item.
   - **Path Parameter:**
     - `itemId` (string): The ID of the item to update.
   - **Request Body:**
     ```json
     {
       "itemName": "Updated Item Name",
       "price": 800.00,
       "quantity": 75,
       "description": "Updated description"
     }
     ```
   - **Response:**
     ```json
     {
       "status": "Item updated successfully"
     }
     ```

#### 10. **Delete Item**
   - **Method:** `DELETE`
   - **URL:** `/item/{itemId}`
   - **Description:** Deletes an item by its ID.
   - **Path Parameter:**
     - `itemId` (string): The ID of the item to delete.
   - **Response:**
     ```json
     {
       "status": "Item deleted successfully"
     }
     ```

### Customers

#### 11. **Get All Customers**
   - **Method:** `GET`
   - **URL:** `/customers`
   - **Description:** Retrieves all customers in the POS system.
   - **Response:**
     ```json
     [
       {
         "customerId": "C001",
         "customerName": "pavithrani",
         "email": "pavithrani@example.com",
         "phone": "1234567890"
       },
       ...
     ]
     ```

#### 12. **Get Customer by ID**
   - **Method:** `GET`
   - **URL:** `/customer/{customerId}`
   - **Description:** Retrieves a specific customer by their ID.
   - **Path Parameter:**
     - `customerId` (string): The ID of the customer to retrieve.
   - **Response:**
     ```json
     {
       "customerId": "C001",
       "customerName": "pavithrani",
       "email": "pavithrani@example.com",
       "phone": "1234567890"
     }
     ```

#### 13. **Create New Customer**
   - **Method:** `POST`
   - **URL:** `/customer`
   - **Description:** Adds a new customer to the POS system.
   - **Request Body:**
     ```json
     {
       "customerId": "C002",
       "customerName": "pavithrani",
       "email": "pavithrani@example.com",
       "phone": "0987654321"
     }
     ```
   - **Response:**
     ```json
     {
       "status": "Customer added successfully"
     }
     ```

#### 14. **Update Customer**
   - **Method:** `PUT`
   - **URL:** `/customer/{customerId}`
   - **Description:** Updates the details of an existing customer.
   - **Path Parameter:**
     - `customerId` (string): The ID of the customer to update.
   - **Request Body:**
     ```json
     {
       "customerName": "pavithrani",
       "email": "pavithrani@example.com",
       "phone": "1122334455"
     }
     ```
   - **Response:**
     ```json
     {
       "status": "Customer updated successfully"
     }
     ```

#### 15. **Delete Customer**
   - **Method:** `DELETE`
   - **URL:** `/customer/{customerId}`
   - **Description:** Deletes a customer by their ID.
   - **Path Parameter:**
     - `customerId` (string): The ID of the customer to delete.
   - **Response:**


     ```json
     {
       "status": "Customer deleted successfully"
     }
     ```

---

## Error Handling
All endpoints return appropriate HTTP status codes and error messages in case of failures:

- **400 Bad Request:** The request could not be understood or was missing required parameters.
- **404 Not Found:** The specified resource could not be found.
- **500 Internal Server Error:** An error occurred on the server.

Example error response:

```json
{
  "error": "Resource not found"
}
```

