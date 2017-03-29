curl --data "isFbSignUp=0&name=a&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=b&emailId=b&contactNumber=b&password=b&birthdate=2&gender=m&interestedIn=F&linkOfProfilePicture=1&fbId=2&friendList=3,5" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1&fbId=5&friendList=2,8,3,6" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=0&name=a&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=8&friendList=5,6" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=3&friendList=2,5" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=0&name=a&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=6&friendList=5,8" http://127.0.0.1:3000/signUp --verbose 
