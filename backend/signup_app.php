<?php
	{
		$isFbSignUp = $_POST['isFbSignUp'];
		$name = $_POST['name'];
		$emailId = $_POST['emailId'];
		$contactNumber = $_POST['contactNumber'];
		$password = $_POST['password'];
		$birthdate = $_POST['birthdate'];
		$gender = $_POST['gender'];
		$interestedIn = $_POST['interestedIn'];
		$linkOfProfilePicture = $_POST['linkOfProfilePicture'];

		if ($isFbSignUp == "1") 
		{
			$friendList = $_POST['friendList'];
			$fbId = $_POST['fbId'];
			echo $fbId;
			echo $friendList;
			exec("./signUp.out", {"true", $emailId, $birthdate, $password, $linkOfProfilePicture, $contactNumber, $gender, $interestedIn, $fbId, $friendList }, $exitCode);
			echo $exitCode;
		}
		
		else
		{
			exec(".	/signUp.out", {"false", $emailId, $birthdate, $password, $linkOfProfilePicture, $contactNumber, $gender, $interestedIn }, $exitCode);
		}
	
	}
?>