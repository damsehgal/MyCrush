curl --data "isFbSignUp=0&name=a&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=b&emailId=b&contactNumber=b&password=b&birthdate=2&gender=m&interestedIn=F&linkOfProfilePicture=1&fbId=2&friendList=1,8" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1&fbId=6&friendList=8,5" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=0&name=a&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=1&friendList=2,8" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=8&friendList=2,1,6,5" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=0&name=a&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=c&emailId=a&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=5&friendList=6,8" http://127.0.0.1:3000/signUp --verbose 
