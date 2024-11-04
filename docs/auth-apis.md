# Auth API Design 

## Cases Auth APIs must satisfy:

- Allow the user to sign up
- Allow the user to log in 
- Provide session token for user/refresh token/invalidate token 

## Required APIs/details

Auth to be displayed on the front end: 
- Sign up
- Log in 
- Log out 

Fields required to be returned by the API:
- `firstName`
- `lastName`
- `email`
- `password`
- `token` - returned as a cookie in the `set-cookie` header
- `userId`

### Sign up/Log in/Log out 

As mentioned above, Auth APIs must return these fields: 

- `firstName`
- `lastName`
- `email`
- `password`
- `token`
- `userId`

## Sign up

- The HTTP verb used for this should be `POST`. 

- The REST endpoint for these will be `POST /api/auth/signup`

## Log in

- The HTTP verb used for this should be `POST`. 

- The REST endpoint for these will be `POST /api/auth/login`

## Refresh

- The HTTP verb used for this should be `GET`. 

- The REST endpoint for these will be `POST /api/auth/refresh`

## Log out 

- The HTTP verb used for this should be `POST`. 

- The REST endpoint for these will be `POST /api/auth/logout`

### API examples w/ JSON stubs

Request endpoint(s): `POST /api/auth/(signup,login,refresh,logout)`

Response:
```
{
    "Signup (request)": [
       {
            "firstName": "Joe",
            "lastName": "O'Brien",
            "email": "jlobrien@oakland.edu",
            "Password": "OUBookmarket123",
            "token": "abcd1234" 
       } 
    Signup (response)":
        {
            "message": "User registered successfully",
            "userId": "1234",
            "token": "abcd1234"
        }
    ]
    "Login (request)": [
       {
            "email": "jlobrien@oakland.edu",
            "Password": "OUBookmarket123"
       } 
    Login (response)":
        {
            "message": "Login Successful.",
            "userId": "abc123",
            "token": "abcde12345",
            "expiresAt": "1200"
        }
    ]
     "Refresh (request)": [
       {
            "userId": "abc123",
             "token": "abcde12345",
             "refreshToken": "1200"
       } 
    Refresh (response)":
        {
            "userId": "abc123",
            "token": "abcde12345",
            "newExpiresAt": "1200"
        }
    ]
    "Logout (request)": [
            
            NA
    "Logout (response)":
        {
            "message": "Logout Successful."
        }
    ]
}