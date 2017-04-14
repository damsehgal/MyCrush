curl --data "isFbSignUp=1&name=chu&emailId=fasd&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=3&friendList=2,5&salt=salt&isNumberVisible=false" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=0&name=allu&emailId=gasd&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1&salt=salt&isNumberVisible=false" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=charu&emailId=herw&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=3&fbId=6&friendList=5,8&salt=salt&isNumberVisible=false" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=0&name=aanga&emailId=agfd&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1&salt=salt&isNumberVisible=false" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=banga&emailId=bvbn&contactNumber=b&password=b&birthdate=2&gender=m&interestedIn=F&linkOfProfilePicture=1&fbId=2&friendList=3,5&salt=salt&isNumberVisible=false" http://127.0.0.1:3000/signUp --verbose 
curl --data "isFbSignUp=1&name=canga&emailId=chft&contactNumber=a&password=a&birthdate=1&gender=m&interestedIn=F&linkOfProfilePicture=1&fbId=5&friendList=2,8,3,6&salt=salt&isNumberVisible=false" http://127.0.0.1:3000/signUp --verbose 

curl --data "name=a" http://127.0.0.1:3000/searchAll