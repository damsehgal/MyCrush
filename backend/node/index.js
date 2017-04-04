// node wale folder se hi call krni h
var express = require('express');
var bodyParser = require('body-parser');
var app = express();
app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true }));

var exec = require('child_process').exec;

app.post('/signUp', (req, res)=>{
	console.log(req.body);
	var fbTrue = parseInt(req.body.isFbSignUp) === 1 ? true : false;
	if(fbTrue){
		var cmd = '../signUp.out true ' + req.body.emailId +' ' + req.body.name + ' ' + req.body.birthdate + ' ' + req.body.password + ' ' + req.body.linkOfProfilePicture + ' ' + req.body.contactNumber + ' ' + req.body.gender + ' ' + req.body.interestedIn + ' ' + req.body.salt + ' ' + req.body.isNumberVisible + ' ' + req.body.fbId + ' ' + req.body.friendList;

	} 
	else {

		var cmd = '../signUp.out false ' + req.body.emailId +' ' + req.body.name + ' ' + req.body.birthdate + ' ' + req.body.password + ' ' + req.body.linkOfProfilePicture + ' ' + req.body.contactNumber + ' ' + req.body.gender + ' ' + req.body.interestedIn + ' ' + req.body.salt + ' ' + req.body.isNumberVisible;
		
	}


	exec(cmd, function(error, stdout, stderr) {
	  // command output is in stdout
	  console.log("error: ", error);
	  console.log("Output: ", stdout);
	  console.log("STDERR: ", stderr);
	  if(error){
	  	res.sendStatus(500);
	  }
	});
	res.sendStatus(200);
});
app.post('/updatefuckers', function(req, res){

});

app.listen(3000);
