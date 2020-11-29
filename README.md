# atm-api




# Getting Started


### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)


##################### Instruction to build and execute the code  #########################  

 # JDK version is -> 1.8  
 # Gradle version is -> 5.2.1  

 # To build and execute the program you have to run the following command.  
 	$ gradle build  // to build the program  
 	$ gradle bootRun   // to execute the project   


####################### #######################################################################  
#DataBase Details
 I have used in-memmory H2 database ".sql" file placed in /resources folder. There is two files on the same location one file contains table create query and other one contains insert query.
 File names :
 1: schema.sql
 2: data.sql
 
#Denominations:
100,500,2000

#ATM API details:

#1.Swagger UI URL: 
[Click to show Swagger UI](http://localhost:8762/swagger-ui.html#)

You can refer all api's from swagger too.

We have Implement the API’s for the following functionalities - 

#1. Create accounts:
To Create new Account. 


# Request Method And URL:
    [POST] http://localhost:8762/atm/api/createAccount
  
#Request Payload:  
  {  
    "customerName":"Sajid Shaikh",  
    "email":"sajid.shaikh91@gmail.com",  
    "pin":"TEST",  
    "amount":47385.45,  
    "accountType":"Savings Account"  
 }  
#Response Payload:  
  {  
    "success": true,  
    "message": "Success",  
    "statusCode": 201,  
    "data": 3  
  }  
  
  	
#2. Cash deposits:  
To deposit money into account.  

# Request Method And URL:
    [POST] http://localhost:8762/atm/api/deposit
  
#Request Payload:  
{  
    "customerId":2,  
    "amount":12200,  
    "denomination":{  
        "100":2,  
        "500":4,  
        "2000":5  
    }  
}  
#Response Payload:  
 {  
    "success": true,  
    "message": "₹ 12200.0 Successfully deposited.!",  
    "statusCode": 201,  
    "data": null  
}  

	
#3. cash withdrawals:  
To cash withdrawls from ATM.  
#Amount will be accepted in multiplication of 100 only. And max cash transacation limit is >10000  

# Request Method And URL:
    [POST] http://localhost:8762/atm/api/withdrawals
  
#Request Payload:  
{  
    "customerId":2,  
    "pin":"test12",  
    "amount":12200  
}  
#Response Payload:  
{  
    "success": true,  
    "message": "₹ 12200 Successfully withdrawal.!",  
    "statusCode": 200,  
    "data": {  
        "availableBalance": "₹ 6085.45",  
        "currentTime": "2020-11-29T11:42:15.542",  
        "denominationMap": {  
            "₹ 500": 4,  
            "₹ 100": 2,  
            "₹ 2000": 5  
        }  
    }  
}  
#4. Balance enquiry:  
To check the current balance.  

# Request Method And URL:
    [GET] http://localhost:8762/atm/api/balanceEnquiry?customerId=2
  
#Request Payload:  
{  
   PARAM: customerId=2  
}  
#Response Payload:  
{  
    "success": true,  
    "message": "Success",  
    "statusCode": 200,  
    "data": {  
        "availableBalance": "₹ 18285.45",  
        "currentTime": "2020-11-29T11:41:32.697",  
        "denominationMap": null  
    }  
}  
